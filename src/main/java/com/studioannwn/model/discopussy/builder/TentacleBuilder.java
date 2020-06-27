package com.studioannwn.model.discopussy.builder;

import com.studioannwn.model.discopussy.DiscoPussyConfig;
import com.studioannwn.model.discopussy.DiscoPussyModel;
import com.studioannwn.model.discopussy.TentacleConfig;
import heronarts.lx.model.LXPoint;
import heronarts.lx.transform.LXTransform;
import heronarts.lx.transform.LXVector;

import java.util.ArrayList;
import java.util.List;

import static com.studioannwn.model.discopussy.DiscoPussyConfig.DATALINE_DISTANCE;
import static com.studioannwn.model.discopussy.DiscoPussyConfig.DISCO_BALL_TENTACLE_OFFSET;
import static com.studioannwn.util.MathConstants.HALF_PI;
import static com.studioannwn.util.MathConstants.TWO_PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class TentacleBuilder {

  public static List<DiscoPussyModel.Tentacle> buildTentacles(DiscoPussyConfig config) {
    return buildTentacles(config, new LXTransform());
  }

  public static List<DiscoPussyModel.Tentacle> buildTentacles(DiscoPussyConfig config, LXTransform t) {
    List<DiscoPussyModel.Tentacle> tentacles = new ArrayList<>();

    for (TentacleConfig tentacleConfig : config.getTentacleConfigs()) {
      tentacles.add(buildTentacle(tentacleConfig, t));
    }

    return tentacles;
  }

  private static DiscoPussyModel.Tentacle buildTentacle(TentacleConfig tentacleConfig, LXTransform t) {
    List<DiscoPussyModel.Dataline> datalines = buildDatalines(tentacleConfig, t);

    return new DiscoPussyModel.Tentacle(datalines);
  }

  private static List<DiscoPussyModel.Dataline> buildDatalines(TentacleConfig tentacleConfig, LXTransform t) {
    List<DiscoPussyModel.Dataline> datalines = new ArrayList<>();

    float distance = DiscoPussyConfig.DISCO_BALL_RADIUS;
    float angle = tentacleConfig.getAngle();
    LXVector offset = tentacleConfig.getStartPositionOffset();
    int[] channels = tentacleConfig.getChannels();

    t.push();
    t.rotateZ(angle);
    t.translate(0, distance);
    t.translate(0, DISCO_BALL_TENTACLE_OFFSET);

    t.rotateZ(-angle);
    t.translate(offset.x, offset.y);
    t.rotateZ(angle);

    for (int datalineIndex = 0; datalineIndex < channels.length; datalineIndex++) {
      t.push();
      float datalineIndexN = 1.f * datalineIndex / channels.length;
      float datalineAngle = datalineIndexN * TWO_PI + HALF_PI;
      t.translate((float) (DATALINE_DISTANCE * cos(datalineAngle)), 0, (float) (DATALINE_DISTANCE * sin(datalineAngle)));

      datalines.add(buildDataline(tentacleConfig, datalineIndex, t));

      t.pop();
    }

    t.pop();

    return datalines;
  }

  private static DiscoPussyModel.Dataline buildDataline(TentacleConfig tentacleConfig, int index, LXTransform t) {
    List<DiscoPussyModel.Strip> strips = new ArrayList<>();

    for (int strip = 0; strip < tentacleConfig.getSize().MODULE_COUNT; strip++) {
      List<LXPoint> points = new ArrayList<>();

      if (strip != 0) {
        t.translate(0, DiscoPussyConfig.LEDModule.MODULE_PITCH);
      }

      for (int i = 0; i < DiscoPussyConfig.LEDModule.POINT_COUNT; i++) {
        if (i != 0) {
          t.translate(0, DiscoPussyConfig.LEDModule.LED_PITCH);
        }

        points.add(new LXPoint(t));
      }

      strips.add(new DiscoPussyModel.Strip(points));
    }

    return new DiscoPussyModel.Dataline(
      tentacleConfig.getId() + "_" + index,
      tentacleConfig.getIpAddress(),
      tentacleConfig.getChannels()[index],
      strips
    );
  }
}
