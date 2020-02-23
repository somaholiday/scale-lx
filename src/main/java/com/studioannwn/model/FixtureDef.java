package com.studioannwn.model;

import com.studioannwn.Scale;

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
    this.position = new LXVector(x * Scale.IN, yAdj * Scale.IN, z * Scale.IN);
    // this.position = new LXVector(x * Scale.IN, y * Scale.IN, z * Scale.IN);
    this.ledCount = ledCount;
  }

  public LXVector getPosition() {
    return position.copy();
  }

  public int getLEDCount() {
    return ledCount;
  }
}