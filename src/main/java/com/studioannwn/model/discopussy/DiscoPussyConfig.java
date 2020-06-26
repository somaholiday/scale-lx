package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

import static com.studioannwn.util.DistanceConstants.INCHES;
import static com.studioannwn.util.DistanceConstants.METER;

public class DiscoPussyConfig {
  public final static String PIXLITE_IP = "192.168.2.18";

  public final static float DISCO_BALL_RADIUS = 24 * INCHES;
  public final static LXVector DISCO_BALL_CENTER = new LXVector(0, 0, 0);
  public final static float DISCO_BALL_TENTACLE_OFFSET = 6 * INCHES;

  public final static float DATALINE_DISTANCE = 1 * INCHES;

  public final static class LEDModule {
    public final static int POINT_COUNT = 4;
    public final static float LED_PITCH = 1 * METER / 60;
    // space between modules
    public final static float MODULE_PITCH = 4 * INCHES;
  }

  private final TentacleConfig[] tentacles = new TentacleConfig[]{
    new TentacleConfig("0", 0, TentacleConfig.Size.LONG)
      .setBottomChannel(1)
      .setLeftChannel(2)
      .setRightChannel(3),
    new TentacleConfig("1", 45, TentacleConfig.Size.SHORT)
      .setBottomChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),
    new TentacleConfig("2", 90, TentacleConfig.Size.LONG)
      .setBottomChannel(7)
      .setLeftChannel(8)
      .setRightChannel(9),
    new TentacleConfig("3", 135, TentacleConfig.Size.SHORT)
      .setBottomChannel(10)
      .setLeftChannel(11)
      .setRightChannel(12),
    new TentacleConfig("4", 180, TentacleConfig.Size.LONG)
      .setBottomChannel(13)
      .setLeftChannel(14)
      .setRightChannel(15),
    new TentacleConfig("5", 225, TentacleConfig.Size.SHORT)
      .setBottomChannel(16)
      .setLeftChannel(17)
      .setRightChannel(18),
    new TentacleConfig("6", 270, TentacleConfig.Size.LONG)
      .setBottomChannel(19)
      .setLeftChannel(20)
      .setRightChannel(21),
    new TentacleConfig("7", 315, TentacleConfig.Size.SHORT)
      .setBottomChannel(22)
      .setLeftChannel(23)
      .setRightChannel(24),
  };

  public TentacleConfig[] getTentacleConfigs() {
    return tentacles;
  }
}
