package com.studioannwn.model.discopussy;

import com.studioannwn.util.MathUtils;
import heronarts.lx.transform.LXVector;

public class TentacleConfig {

  public enum Size {
    SHORT(40),
    LONG(70);

    public final int MODULE_COUNT;

    Size(int moduleCount) {
      this.MODULE_COUNT = moduleCount;
    }
  }

  /*
    Channel index positions, looking outward from the disco ball
           0
         /   \
        /     \
       /       \
      /         \
     /           \
    1 ----------- 2
   */
  private final int TOP = 0;
  private final int LEFT = 1;
  private final int RIGHT = 2;

  public static final String[] CHANNEL_NAMES = new String[] {
    "B",
    "L",
    "R",
  };

  private final String id;
  private final Size size;
  private String ipAddress;
  private final int[] channels = new int[3];
  private final float angle; // radians
  private LXVector startPositionOffset = new LXVector(0, 0, 0);

  public TentacleConfig(String id, float angleDegrees, Size size) {
    this.id = id;
    // negative angle combined with Y translation in builder gives a clockwise compass with 0º at the top
    this.angle = MathUtils.radians(-angleDegrees);
    this.size = size;
  }

  public TentacleConfig setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  public TentacleConfig setTopChannel(int channel) {
    this.channels[TOP] = channel;
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

  public String getId() {
    return id;
  }

  public Size getSize() {
    return size;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public int[] getChannels() {
    return channels;
  }

  public LXVector getStartPositionOffset() {
    return startPositionOffset.copy();
  }

  public float getAngle() {
    return angle;
  }
}
