package com.studioannwn.patterns;

import com.studioannwn.util.TimeConstants;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.BooleanParameter;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.pattern.LXPattern;
import heronarts.lx.transform.LXVector;

import static com.studioannwn.util.MathConstants.TAU;
import static com.studioannwn.util.MathUtils.*;

@LXCategory(LXCategory.FORM)
public class Spiral extends LXPattern {
  CompoundParameter sizeParameter = new CompoundParameter("Size", 5, 1, 10).setDescription("Sets spiral width");
  CompoundParameter spacingParameter = new CompoundParameter("Spacing", 0.5, 0.1, 1).setDescription("Sets spiral spacing");
  CompoundParameter lengthParameter = new CompoundParameter("Length", 2, 1, 5).setDescription("Sets spiral length");
  CompoundParameter speedParameter = new CompoundParameter("Speed", 10, -25, 25).setDescription("Sets rotation speed").setPolarity(LXParameter.Polarity.BIPOLAR);
  CompoundParameter thetaIncParameter = new CompoundParameter("Curve", .1, -.1, .1).setDescription("Alters the spiral curvature").setPolarity(LXParameter.Polarity.BIPOLAR);

  CompoundParameter hueParameter = new CompoundParameter("Hue", 0.5).setDescription("Sets hue of spiral");
  BooleanParameter grayscaleParameter = new BooleanParameter("Gray", false).setDescription("Sets output to grayscale");

  CompoundParameter centerXParameter = new CompoundParameter("CenterX", 0.5, 0, 1).setDescription("Sets x position of ripple center");
  CompoundParameter centerYParameter = new CompoundParameter("CenterY", 0.5, 0, 1).setDescription("Sets y position of ripple center");

  LXVector center = new LXVector(0, 0, 0);
  float t = 0;

  public Spiral(LX lx) {
    super(lx);

    addParameter(sizeParameter.getLabel().toLowerCase(), sizeParameter);
    addParameter(spacingParameter.getLabel().toLowerCase(), spacingParameter);
    addParameter(lengthParameter.getLabel().toLowerCase(), lengthParameter);
    addParameter(speedParameter.getLabel().toLowerCase(), speedParameter);
    addParameter(thetaIncParameter.getLabel().toLowerCase(), thetaIncParameter);
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
    setColors(LXColor.BLACK);
    updateCenter();

    float width = sizeParameter.getValuef();
    float spacing = spacingParameter.getValuef();
    float iterations = lengthParameter.getValuef() * 72;
    float thetaIncrement = thetaIncParameter.getValuef();

    float h = hueParameter.getValuef() * 360;
    float s = grayscaleParameter.isOn() ? 0 : 100;
    float b = 100;

    float r = 0;
    float theta = 0;
    for (int i = 0; i < iterations; i++) {
      float x = center.x + r * cos(theta + t);
      float y = center.y + r * sin(theta + t);

      for (LXPoint p : model.points) {
        if (dist(x, y, p.x, p.y) < width) {
          colors[p.index] = lx.hsb(h, s, b);
        }

      }
      r += spacing;
      theta += thetaIncrement;
    }
  }
}
