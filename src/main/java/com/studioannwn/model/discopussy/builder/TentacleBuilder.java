package com.studioannwn.model.discopussy.builder;

import com.studioannwn.model.discopussy.DiscoPussyConfig;
import com.studioannwn.model.discopussy.DiscoPussyModel;
import com.studioannwn.model.discopussy.TentacleConfig;
import heronarts.lx.model.LXPoint;
import heronarts.lx.transform.LXVector;

import java.util.ArrayList;
import java.util.List;

public class TentacleBuilder {

  public static List<DiscoPussyModel.Tentacle> buildTentacles(DiscoPussyConfig config) {
    List<DiscoPussyModel.Tentacle> tentacles = new ArrayList<>();

    for (TentacleConfig tentacleConfig : config.getTentacleConfigs()) {
      tentacles.add(buildTentacle(tentacleConfig));
    }

    return tentacles;
  }

  private static DiscoPussyModel.Tentacle buildTentacle(TentacleConfig tentacleConfig) {
    List<DiscoPussyModel.Dataline> datalines = buildDatalines(tentacleConfig);

    return new DiscoPussyModel.Tentacle(datalines);
  }

  private static List<DiscoPussyModel.Dataline> buildDatalines(TentacleConfig tentacleConfig) {
    List<DiscoPussyModel.Dataline> datalines = new ArrayList<>();

    LXVector center = DiscoPussyConfig.DISCO_BALL_CENTER;
    LXVector direction = tentacleConfig.getDirection();
    float distance = DiscoPussyConfig.DISCO_BALL_RADIUS;

    LXVector start = center.copy().add(direction.mult(distance));
    start.add(tentacleConfig.getStartPositionOffset());

    //TODO: make a triangle of channels
    int datalineIndex = 0;
    LXVector cursor = start.copy();

    List<DiscoPussyModel.Strip> strips = new ArrayList<>();

    for (int strip = 0; strip < tentacleConfig.size.STRIP_COUNT; strip++) {
      List<LXPoint> points = new ArrayList<>();

      if (strip != 0) {
        cursor.add(direction.mult(DiscoPussyConfig.LEDModule.PITCH));
      }

      for (int i = 0; i < DiscoPussyConfig.LEDModule.POINT_COUNT; i++) {
        if (i != 0) {
          cursor.add(direction.mult(DiscoPussyConfig.LEDModule.LED_PITCH));
        }

        points.add(new LXPoint(cursor));
      }

      strips.add(new DiscoPussyModel.Strip(points));
    }

    datalines.add(new DiscoPussyModel.Dataline(
      tentacleConfig.id + datalineIndex,
      DiscoPussyConfig.PIXLITE_IP,
      tentacleConfig.channels[datalineIndex],
      strips
    ));

    return datalines;
  }
}
