package com.studioannwn;

import processing.core.PGraphics;

import heronarts.p3lx.P3LX;
import heronarts.p3lx.ui.UI;
import heronarts.p3lx.ui.UI3dComponent;

public class ScaleVisualizer extends UI3dComponent {
  private final P3LX lx;

  public ScaleVisualizer(P3LX lx) {
    this.lx = lx;
  }

  private void drawAxes(PGraphics pg) {
    pg.pushStyle();
    {
      // X == red
      pg.stroke(255, 0, 0);
      pg.line(0, 0, 100, 0);
    }
    pg.popStyle();

    pg.pushStyle();
    pg.pushMatrix();
    {
      // Y == green
      pg.rotateZ((float) Math.PI / 2);
      pg.stroke(0, 255, 0);
      pg.line(0, 0, 100, 0);
    }
    pg.popMatrix();
    pg.popStyle();

    pg.pushStyle();
    pg.pushMatrix();
    {
      // Z == blue
      pg.rotateY((float) Math.PI / 2);
      pg.stroke(0, 0, 255);
      pg.line(0, 0, -100, 0); // PGraphics Z and LXModel Z directions are switched, apparently
    }
    pg.popMatrix();
    pg.popStyle();
  }

  @Override
  protected void onDraw(UI ui, PGraphics pg) {
    pg.lights();

    drawAxes(pg);
  }
}
