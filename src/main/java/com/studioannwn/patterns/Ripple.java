package com.studioannwn.patterns;

import com.studioannwn.util.TimeConstants;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.modulator.SinLFO;
import heronarts.lx.parameter.BooleanParameter;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.parameter.DiscreteParameter;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.pattern.LXPattern;
import heronarts.lx.transform.LXVector;

import static com.studioannwn.util.MathConstants.TAU;
import static com.studioannwn.util.MathUtils.*;

@LXCategory(LXCategory.FORM)
public class Ripple extends LXPattern {
  CompoundParameter hueParameter = new CompoundParameter("Hue", 0.5).setDescription("Sets hue of wave");
  CompoundParameter speedParameter = new CompoundParameter("Speed", 1, -5, 5).setDescription("Sets speed of wave movement").setPolarity(LXParameter.Polarity.BIPOLAR);
  CompoundParameter wavelengthParameter = new CompoundParameter("Wavelength", 0.5, 0.005, 0.25).setDescription("Sets wavelength between peaks");
//  CompoundParameter wavelengthParameter = new CompoundParameter("Wavelength", 0.01, 0.0001, 0.01).setDescription("Sets wavelength between peaks");
  BooleanParameter tinyWavelengthParameter = new BooleanParameter("TinyWavelength", false).setDescription("Makes the wavelength tiny");
  CompoundParameter sizeParameter = new CompoundParameter("Size", -0.1, -3, 1).setDescription("Changes size of wave by applying exponential scaling").setPolarity(LXParameter.Polarity.BIPOLAR);
  CompoundParameter shapeParameter = new CompoundParameter("Shape", -0.5, -2, 1).setDescription("Applies shaping to the waveshape").setPolarity(LXParameter.Polarity.BIPOLAR);
  BooleanParameter grayscaleParameter = new BooleanParameter("Gray", false).setDescription("Sets output to grayscale");
  CompoundParameter centerXParameter = new CompoundParameter("CenterX", 0.5, 0, 1).setDescription("Sets x position of ripple center");
  CompoundParameter centerYParameter = new CompoundParameter("CenterY", 0.5, 0, 1).setDescription("Sets y position of ripple center");

  LXVector center = new LXVector(0, 0, 0);
  float t = 0;

  float maxModelRange = Math.max(Math.max(model.xRange, model.yRange), model.zRange);

  public Ripple(LX lx) {
    super(lx);

    addParameter(speedParameter.getLabel().toLowerCase(), speedParameter);
    addParameter(wavelengthParameter.getLabel().toLowerCase(), wavelengthParameter);
    addParameter(tinyWavelengthParameter.getLabel().toLowerCase(), tinyWavelengthParameter);
    addParameter(sizeParameter.getLabel().toLowerCase(), sizeParameter);
    addParameter(shapeParameter.getLabel().toLowerCase(), shapeParameter);
    addParameter(hueParameter.getLabel().toLowerCase(), hueParameter);
    addParameter(grayscaleParameter.getLabel().toLowerCase(), grayscaleParameter);
    addParameter(centerXParameter.getLabel().toLowerCase(), centerXParameter);
    addParameter(centerYParameter.getLabel().toLowerCase(), centerYParameter);
  }

  private void updateCenter() {
    float xn = centerXParameter.getValuef();
    float yn = centerYParameter.getValuef();
    float x = map(xn, 0, 1, model.xMin, model.xMax);
    float y = map(yn, 0, 1, model.yMin, model.yMax);
    center.set(x, y);
  }

  @Override
  protected void run(double deltaMs) {
    t += (float) deltaMs / TimeConstants.SEC * speedParameter.getValuef() * -1;
    t = t % TAU;
    updateCenter();

    boolean tiny = tinyWavelengthParameter.getValueb();
    float wavelength = wavelengthParameter.getValuef();
    if (tiny) {
      wavelength = wavelength / 50;
    }
    float freq = 1.f / (wavelength * maxModelRange);
    float offset;

    float exp = sizeParameter.getValuef() * -1; // flip sign to make knob more intuitive: higher value = larger peak
    float expPower = (exp >= 0) ? (1 + 3*exp) : (1 / (1 - 3*exp));
    float shape = shapeParameter.getValuef();
    float shapePower = (shape <= 0) ? (1 - 3*shape) : (1 / (1+3*shape));

    float h = hueParameter.getValuef() * 360;
    float s = grayscaleParameter.isOn() ? 0 : 100;
    float b;

    float val;
    LXVector pv = new LXVector(0, 0, 0);

    for (LXPoint p : model.points) {
      pv.set(p.x, p.y);
      offset = center.dist(pv);
      val = sin(freq * offset + t); // calculate sin value
      val = norm(val, -1, 1); // normalize value

      if (shapePower != 1) {
        if (val >= 0.5) {
          val = 0.5f + 0.5f * pow(2f*(val-0.5f), shapePower);
        } else {
          val = 0.5f - 0.5f * pow(2f*(0.5f - val), shapePower);
        }
      }
      if (expPower != 1) {
        val = pow(val, expPower); // apply size bias
      }
      b = val * 100;
      colors[p.index] = LXColor.hsb(h, s, b);
    }
  }
}
