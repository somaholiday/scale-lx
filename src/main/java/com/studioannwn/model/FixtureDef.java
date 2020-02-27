package com.studioannwn.model;

import com.studioannwn.util.DistanceConstants;

import heronarts.lx.transform.LXVector;
import processing.core.PApplet;

public class FixtureDef {
  public static final int SIZE_S = 40;
  public static final int SIZE_M = 80;
  public static final int SIZE_L = 120;

  private LXVector position;
  private int ledCount;

  public FixtureDef(float x, float y, float z, int ledCount) {
    float factor = Math.abs(PApplet.map(z, 6, 64, -1, 1));
    float yAdj = (float) (3 + 9 * factor);
    this.position = new LXVector(x * DistanceConstants.IN, yAdj * DistanceConstants.IN, z * DistanceConstants.IN);
    // this.position = new LXVector(x * DistanceConstants.IN, y * DistanceConstants.IN, z * DistanceConstants.IN);
    this.ledCount = ledCount;
  }

  public LXVector getPosition() {
    return position.copy();
  }

  public int getLEDCount() {
    return ledCount;
  }
}