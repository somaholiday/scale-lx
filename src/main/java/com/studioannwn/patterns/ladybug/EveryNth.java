package com.studioannwn.patterns.ladybug;

import com.studioannwn.util.TimeConstants;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.parameter.DiscreteParameter;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.pattern.LXPattern;

import static com.studioannwn.util.MathConstants.TAU;

@LXCategory(LXCategory.FORM)
public class EveryNth extends LXPattern {

  CompoundParameter nParameter = new CompoundParameter("n", 3, 1, 50).setDescription("Light every nth pixel");
  CompoundParameter hueParameter = new CompoundParameter("Hue", 0.5).setDescription("Sets base hue");
  CompoundParameter spreadParameter = new CompoundParameter("Spread", 0.5).setDescription("Sets spread of rainbow");
  CompoundParameter speedParameter = new CompoundParameter("Speed", 10, -30, 30).setDescription("Sets speed of rotation").setPolarity(LXParameter.Polarity.BIPOLAR);
  CompoundParameter fadeParameter = new CompoundParameter("Fade", 0.1, 0.001, 0.2).setDescription("Sets speed of fade");

  float t = 0;

  public EveryNth(LX lx) {
    super(lx);

    addParameter(nParameter.getLabel().toLowerCase(), nParameter);
    addParameter(hueParameter.getLabel().toLowerCase(), hueParameter);
    addParameter(spreadParameter.getLabel().toLowerCase(), spreadParameter);
    addParameter(speedParameter.getLabel().toLowerCase(), speedParameter);
    addParameter(fadeParameter.getLabel().toLowerCase(), fadeParameter);
  }

  public void run(double deltaMs) {
    int n = (int)Math.floor(nParameter.getValuef());
    float hue = hueParameter.getValuef() * 360;
    float fade = fadeParameter.getValuef();
    float spread = spreadParameter.getValuef();

    t += (float) deltaMs / TimeConstants.SEC * speedParameter.getValuef() * -1;
    t = t % n;

    for (LXPoint p : model.points) {
      if ((Math.floor(t) + p.index) % n == 0) {
        colors[p.index] = LXColor.hsb((hue + p.index * spread) % 360, 100, 100);
      } else {
        colors[p.index] = LXColor.lerp(colors[p.index], LXColor.BLACK, fade);
      }
    }
  }
}
