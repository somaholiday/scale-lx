package com.studioannwn.model.discopussy;

import heronarts.lx.LX;
import heronarts.lx.transform.LXVector;

import static com.studioannwn.model.discopussy.DiscoPussyConfig.LED_PITCH;

public class StripConfig {
  private float pitch = LED_PITCH;
  private int ledCount;
  private LXVector direction;
  private LXVector start;
  private float preSpacing;

  private static LXVector ORIGIN = new LXVector(0, 0, 0);
  private static LXVector DEFAULT_DIRECTION = new LXVector(1, 0, 0);

  public StripConfig() {
    this(0, ORIGIN.copy(), DEFAULT_DIRECTION.copy(), 0);
  }

  public StripConfig(int ledCount, LXVector start, LXVector direction) {
    this(ledCount, start, direction, 0);
  }

  public StripConfig(int ledCount, LXVector start, LXVector direction, float preSpacing) {
    this.ledCount = ledCount;
    this.start = start;
    this.direction = direction;
    this.preSpacing = preSpacing;
  }

  public float getPitch() {
    return pitch;
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

  public float getPreSpacing() {
    return preSpacing;
  }

  public StripConfig setLedCount(int ledCount) {
    this.ledCount = ledCount;
    return this;
  }

  public StripConfig setDirection(LXVector direction) {
    this.direction = direction;
    return this;
  }

  public StripConfig setStart(LXVector start) {
    this.start = start;
    return this;
  }

  public StripConfig setPreSpacing(float preSpacing) {
    this.preSpacing = preSpacing;
    return this;
  }
}
