package com.studioannwn.patterns;

import com.studioannwn.model.StripsModel;
import heronarts.lx.LX;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.modulator.SinLFO;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.pattern.LXModelPattern;

import static heronarts.lx.utils.LXUtils.distance;
import static heronarts.lx.utils.LXUtils.random;

public class StripBounce extends LXModelPattern<StripsModel> {
  private final int numOsc = 30;
  SinLFO[] fX = new SinLFO[numOsc];
  SinLFO[] fY = new SinLFO[numOsc];
  SinLFO[] fZ = new SinLFO[numOsc];
  SinLFO[] sat = new SinLFO[numOsc];
  float[] colorOffset = new float[numOsc];

  public final CompoundParameter size = new CompoundParameter("size", 30, 10, 100);
  public final CompoundParameter hueVariance = new CompoundParameter("hueVar");

  public StripBounce(LX lx) {
    super(lx);
    for (int i = 0; i < numOsc; i++) {
      fX[i] = new SinLFO(model.xMin, model.xMax, random(2000, 20000));
      fY[i] = new SinLFO(model.yMin, model.yMax, random(2000, 20000));
      fZ[i] = new SinLFO(model.zMin, model.zMax, random(2000, 20000));
      sat[i] = new SinLFO(60, 100, random(2000, 50000));
      addModulator(fX[i]).trigger();
      addModulator(fY[i]).trigger();
      addModulator(fZ[i]).trigger();
      colorOffset[i] = (float)random(0, 256);
    }

    addParameter(size.getLabel().toLowerCase(), size);
    addParameter(hueVariance.getLabel().toLowerCase(), hueVariance);
  }

  public void run(double deltaMs) {
    float[] bright = new float[model.points.length];

    for (LXModel strip : model.getStrips()) {
      for (int i = 0; i < numOsc; i++) {
        float avgdist = (float)distance(strip.cx, strip.cy, fX[i].getValuef(), fY[i].getValuef());
        float hv = palette.getHuef() + colorOffset[i]*hueVariance.getValuef();
        float br = Math.max(0, 100 - avgdist*4);

        boolean on = avgdist < size.getValuef();
        for (LXPoint p : strip.points) {
          if (on && br > bright[p.index]) {
            colors[p.index] = lx.hsb(hv, sat[i].getValuef(), br);
            bright[p.index] = br;
          }
        }
      }
    }
  }
}
