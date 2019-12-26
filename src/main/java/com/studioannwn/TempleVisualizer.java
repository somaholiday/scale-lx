package com.studioannwn;

import processing.core.PGraphics;

import heronarts.p3lx.P3LX;
import heronarts.p3lx.ui.UI;
import heronarts.p3lx.ui.UI3dComponent;

public class TempleVisualizer extends UI3dComponent {
  private static final int BASE_COLOR = 0xFF555555;

  private final P3LX lx;

  public TempleVisualizer(P3LX lx) {
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
      pg.line(0, 0, 100, 0);
    }
    pg.popMatrix();
    pg.popStyle();
  }

  @Override
  protected void onDraw(UI ui, PGraphics pg) {
    pg.lights();

    // drawAxes(pg);

    pg.noStroke();
    pg.fill(BASE_COLOR);

    pg.beginShape();
    pg.vertex(0.f, 0.f, 0.f);
    pg.vertex(10.f, 0.f, -(float)(10.f*Math.sqrt(3)));
    pg.vertex(-10.f, 0.f, -(float)(10.f*Math.sqrt(3)));
    pg.endShape();
  }
}
