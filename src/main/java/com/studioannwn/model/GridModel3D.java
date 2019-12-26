package com.studioannwn.model;

import java.util.ArrayList;
import java.util.List;

import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;

public class GridModel3D extends LXModel {

  public final static int SIZE = 20;
  public final static int SPACING = 10;

  public GridModel3D() {
    super(buildPoints());
  }

  private static List<LXPoint> buildPoints() {
    List<LXPoint> points = new ArrayList<>();

    for (int z = 0; z < SIZE; ++z) {
      for (int y = 0; y < SIZE; ++y) {
        for (int x = 0; x < SIZE; ++x) {
          points.add(new LXPoint(x*SPACING, y*SPACING, z*SPACING));
        }
      }
    }

    return points;
  }
}
