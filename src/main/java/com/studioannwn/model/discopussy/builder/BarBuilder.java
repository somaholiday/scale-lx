package com.studioannwn.model.discopussy.builder;

import com.studioannwn.model.discopussy.*;
import heronarts.lx.model.LXPoint;
import heronarts.lx.transform.LXTransform;
import heronarts.lx.transform.LXVector;

import java.util.ArrayList;
import java.util.List;

public class BarBuilder {

  public static DiscoPussyModel.Bar buildBar(DiscoPussyConfig config) {
    return new DiscoPussyModel.Bar(buildDatalines(config, new LXTransform()));
  }

  private static List<DiscoPussyModel.Dataline> buildDatalines(DiscoPussyConfig config, LXTransform t) {
    List<DiscoPussyModel.Dataline> datalines = new ArrayList<>();

    for (DatalineConfig datalineConfig : config.getBarDatalineConfigs()) {
      datalines.add(buildDataline(datalineConfig, t));
    }

    return datalines;
  }

  private static DiscoPussyModel.Dataline buildDataline(DatalineConfig datalineConfig, LXTransform t) {
    List<DiscoPussyModel.Strip> strips = new ArrayList<>();

    for (StripConfig stripConfig : datalineConfig.getStripConfigs()) {
      t.push();
      List<LXPoint> points = new ArrayList<>();
      LXVector start = stripConfig.getStart();
      LXVector direction = stripConfig.getDirection();

      // move to starting position
      t.translate(start.x, start.y);

      // add pre-spacing
      LXVector offset = direction.copy().mult(stripConfig.getPreSpacing());
      t.translate(offset.x, offset.y);

      LXVector pitchOffset = direction.copy().mult(stripConfig.getPitch());

      for (int i = 0; i < stripConfig.getLedCount(); i++) {
        if (i != 0) {
          t.translate(pitchOffset.x, pitchOffset.y);
        }

        points.add(new LXPoint(t));
      }

      strips.add(new DiscoPussyModel.Strip(points));

      t.pop();
    }

    return new DiscoPussyModel.Dataline(
      datalineConfig.getId(),
      datalineConfig.getIpAddress(),
      datalineConfig.getChannel(),
      strips
    );
  }
}
