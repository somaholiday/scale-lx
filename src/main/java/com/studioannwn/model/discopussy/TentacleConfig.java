package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

public class TentacleConfig {

  public enum Size {
    SHORT(40),
    LONG(70);

    public final int STRIP_COUNT;

    Size(int moduleCount) {
      this.STRIP_COUNT = moduleCount;
    }
  }

  /*
    Channel index positions, looking outward from the disco ball
    1 ----------- 2
     \           /
      \         /
       \       /
        \     /
         \   /
           0
   */
  private final int BOTTOM = 0;
  private final int LEFT = 1;
  private final int RIGHT = 2;

  public String id;
  public Size size;
  public int[] channels = new int[3];

  private LXVector direction;
  private LXVector startPositionOffset = new LXVector(0, 0, 0);

  public TentacleConfig(float angleDegrees, Size size) {
    float angleRadians = (float)(angleDegrees * Math.PI / 180);
    direction = new LXVector((float)Math.cos(angleRadians), (float)Math.sin(angleRadians), 0);
    this.size = size;
  }

  public TentacleConfig setBottomChannel(int channel) {
    this.channels[BOTTOM] = channel;
    return this;
  }

  public TentacleConfig setLeftChannel(int channel) {
    this.channels[LEFT] = channel;
    return this;
  }

  public TentacleConfig setRightChannel(int channel) {
    this.channels[RIGHT] = channel;
    return this;
  }

  public TentacleConfig setStartPositionOffset(float x, float y) {
    this.startPositionOffset = new LXVector(x, y, 0);
    return this;
  }

  public LXVector getDirection() {
    return direction;
  }

  public LXVector getStartPositionOffset() {
    return startPositionOffset;
  }
}
