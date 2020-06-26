package com.studioannwn.ui;

import com.studioannwn.model.discopussy.DiscoPussyModel;
import com.studioannwn.model.discopussy.TentacleConfig;
import heronarts.p3lx.P3LX;
import heronarts.p3lx.ui.UI;
import heronarts.p3lx.ui.UI3dComponent;
import processing.core.PConstants;
import processing.core.PGraphics;

import static com.studioannwn.model.discopussy.DiscoPussyConfig.*;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.PI;

public class DiscoPussyVisualizer extends UI3dComponent {
  private final P3LX lx;
  private final DiscoPussyModel model;

  public DiscoPussyVisualizer(P3LX lx) {
    this.lx = lx;
    this.model = (DiscoPussyModel)lx.getModel();
  }

  @Override
  protected void onDraw(UI ui, PGraphics pg) {
    pg.lights();

    pg.pushMatrix();
    pg.translate(
      DISCO_BALL_CENTER.x,
      DISCO_BALL_CENTER.y,
      DISCO_BALL_CENTER.z
    );

    drawDiscoBall(ui, pg);
    drawTentacleLabels(ui, pg);

    pg.popMatrix();
  }

  private void drawDiscoBall(UI ui, PGraphics pg) {
    pg.pushMatrix();
    pg.pushStyle();

    pg.rotateX(HALF_PI);

    pg.stroke(0);
    pg.fill(255);
    pg.sphereDetail(10);
    pg.sphere(DISCO_BALL_RADIUS);

    pg.popStyle();
    pg.popMatrix();
  }

  private void drawTentacleLabels(UI ui, PGraphics pg) {
    TentacleConfig[] tentacleConfigs = model.getConfig().getTentacleConfigs();

    for (TentacleConfig tentacleConfig : tentacleConfigs) {
      float angle = tentacleConfig.getAngle();

      pg.pushMatrix();
      pg.pushStyle();

      pg.rotateZ(angle);
      pg.translate(0, DISCO_BALL_RADIUS + DISCO_BALL_TENTACLE_OFFSET + tentacleConfig.getSize().MODULE_COUNT / 2);
      pg.rotateZ(-angle);

      pg.rotateX(PI);
      pg.stroke(255, 0, 0);
      pg.fill(255);
      pg.text(tentacleConfig.getId(), -4, 5, -2);

      pg.popStyle();
      pg.popMatrix();
    }
  }
}
