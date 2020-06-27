package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

import static com.studioannwn.util.DistanceConstants.INCHES;
import static com.studioannwn.util.DistanceConstants.METER;

public class DiscoPussyConfig {
  public final static String PIXLITE_1 = "192.168.2.18";
  public final static String PIXLITE_2 = "192.168.2.19";

  public final static LXVector DISCO_BALL_CENTER = new LXVector(0, 0, 0);
  public final static float DISCO_BALL_RADIUS = 24 * INCHES;
  public final static float DISCO_BALL_TENTACLE_OFFSET = 6 * INCHES;

  public final static float DATALINE_DISTANCE = 1 * INCHES;

  public final static class LEDModule {
    // LEDs per module
    public final static int POINT_COUNT = 4;

    // space between LEDs
    public final static float LED_PITCH = 1 * METER / 60;

    // space between modules
    public final static float MODULE_PITCH = 4 * INCHES;
  }

  /*
   TENTACLE CONFIGURATION

   TentacleConfig( id, angle, size )
     angle is in degrees, with 0º at North and going clockwise
     size is SHORT (40 modules) or LONG (70 modules)

   Each tentacle has 3 datalines. It is assumed that all three datalines connect to the same PixLite.
     setIpAddress(ipAddress) sets the tentacle's IP address

   These methods set the PixLite output number for each dataline going down the tentacle:
     setBottomChannel(channelNumber)
     setLeftChannel(channelNumber)
     setRightChannel(channelNumber)

   Channel position names are based on looking from the disco ball outward

    left ---------- right
        \          /
         \        /
          \      /
           \    /
            \  /
           bottom
   */

  private final TentacleConfig[] tentacles = new TentacleConfig[]{
    new TentacleConfig("0", 0, TentacleConfig.Size.LONG)
      .setIpAddress(PIXLITE_1)
      .setBottomChannel(1)
      .setLeftChannel(2)
      .setRightChannel(3),
    new TentacleConfig("1", 45, TentacleConfig.Size.SHORT)
      .setIpAddress(PIXLITE_1)
      .setBottomChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),
    new TentacleConfig("2", 90, TentacleConfig.Size.LONG)
      .setIpAddress(PIXLITE_1)
      .setBottomChannel(7)
      .setLeftChannel(8)
      .setRightChannel(9),
    new TentacleConfig("3", 135, TentacleConfig.Size.SHORT)
      .setIpAddress(PIXLITE_1)
      .setBottomChannel(10)
      .setLeftChannel(11)
      .setRightChannel(12),
    new TentacleConfig("4", 180, TentacleConfig.Size.LONG)
      .setIpAddress(PIXLITE_2)
      .setBottomChannel(1)
      .setLeftChannel(2)
      .setRightChannel(3),
    new TentacleConfig("5", 225, TentacleConfig.Size.SHORT)
      .setIpAddress(PIXLITE_2)
      .setBottomChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),
    new TentacleConfig("6", 270, TentacleConfig.Size.LONG)
      .setIpAddress(PIXLITE_2)
      .setBottomChannel(7)
      .setLeftChannel(8)
      .setRightChannel(9),
    new TentacleConfig("7", 315, TentacleConfig.Size.SHORT)
      .setIpAddress(PIXLITE_2)
      .setBottomChannel(10)
      .setLeftChannel(11)
      .setRightChannel(12),
  };

  public TentacleConfig[] getTentacleConfigs() {
    return tentacles;
  }
}
