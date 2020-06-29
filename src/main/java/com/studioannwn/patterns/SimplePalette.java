package com.studioannwn.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.pattern.LXPattern;

@LXCategory(LXCategory.COLOR)
public class SimplePalette extends LXPattern {
  private int[] palette = new int[] {
    LXColor.RED,
    LXColor.GREEN,
    LXColor.BLUE,
  };

  public SimplePalette(LX lx) {
    super(lx);
  }

  @Override
  protected void run(double v) {
    for (LXPoint p : model.points) {
      colors[p.index] = palette[p.index % palette.length];
    }
  }
}
