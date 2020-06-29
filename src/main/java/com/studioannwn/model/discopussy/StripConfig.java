package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

import static com.studioannwn.model.discopussy.DiscoPussyConfig.LED_PITCH;

public class StripConfig {
  private float pitch = LED_PITCH;
  private int ledCount;
  private LXVector direction;
  private LXVector start;
  private float preSpacing;

  public StripConfig(int ledCount, LXVector start, LXVector direction) {
    this(ledCount, start, direction, 0);
  }

  public StripConfig(int ledCount, LXVector start, LXVector direction, float preSpacing) {
    this.ledCount = ledCount;
    this.start = start;
    this.direction = direction;
    this.preSpacing = preSpacing;
  }

  public int getLedCount() {
    return ledCount;
  }

  public LXVector getDirection() {
    return direction.copy();
  }

  public LXVector getStart() {
    return start.copy();
  }

  public float getPitch() {
    return pitch;
  }

  public float getPreSpacing() {
    return preSpacing;
  }
}
