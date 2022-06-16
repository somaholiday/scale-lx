package com.studioannwn.model.ladybug;

import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.transform.LXVector;

import java.util.ArrayList;
import java.util.List;

public class LadybugModel extends LXModel {
  private static final int LED_COUNT = 750;
  private static final float C = 92.65f;

  public LadybugModel() {
    super(vectorsToPoints(generateVectors()));
  }

  private static List<LXPoint> vectorsToPoints(List<LXVector> vectors) {
    List<LXPoint> points = new ArrayList<>(vectors.size());

    for (LXVector vector : vectors) {
      points.add(new LXPoint(vector));
    }

    return points;
  }

  private static List<LXVector> generateVectors() {
    List<LXVector> vectors = new ArrayList<>(LED_COUNT);

    LXVector center = new LXVector(0, 0, 0);

    for (int i = 0; i < LED_COUNT; i++) {
      float a = i * (float)Math.toRadians(137.5);
      float r = C * (float)Math.sqrt(i);
      float x = center.x + r * (float)Math.cos(a);
      float y = center.y + r * (float)Math.sin(a);

      LXVector vector = new LXVector(x, y, 0);
      vectors.add(vector);
    }

    return vectors;
  }
}
