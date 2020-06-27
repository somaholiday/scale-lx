package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

import static com.studioannwn.model.discopussy.TentacleConfig.Size.LONG;
import static com.studioannwn.model.discopussy.TentacleConfig.Size.SHORT;
import static com.studioannwn.util.DistanceConstants.INCHES;
import static com.studioannwn.util.DistanceConstants.METER;

public class DiscoPussyConfig {
  public final static String PIXLITE_1 = "192.168.2.18";
  public final static String PIXLITE_2 = "192.168.2.19";

  // position of disco ball
  public final static LXVector DISCO_BALL_CENTER = new LXVector(0, 0, 0);

  // radius of disco ball
  public final static float DISCO_BALL_RADIUS = 24 * INCHES;

  // space between disco ball and tentacle
  public final static float DISCO_BALL_TENTACLE_OFFSET = 6 * INCHES;

  // distance of datalines from center of tentacle triangle
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

    new TentacleConfig("0", 0, LONG)
      .setIpAddress(PIXLITE_1)
      .setBottomChannel(1)
      .setLeftChannel(2)
      .setRightChannel(3),

    new TentacleConfig("1", 45, SHORT)
      .setIpAddress(PIXLITE_1)
      .setBottomChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),

    new TentacleConfig("2", 90, LONG)
      .setIpAddress(PIXLITE_1)
      .setBottomChannel(7)
      .setLeftChannel(8)
      .setRightChannel(9),

    new TentacleConfig("3", 135, SHORT)
      .setIpAddress(PIXLITE_1)
      .setBottomChannel(10)
      .setLeftChannel(11)
      .setRightChannel(12),

    new TentacleConfig("4", 180, LONG)
      .setIpAddress(PIXLITE_2)
      .setBottomChannel(1)
      .setLeftChannel(2)
      .setRightChannel(3),

    new TentacleConfig("5", 225, SHORT)
      .setIpAddress(PIXLITE_2)
      .setBottomChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),

    new TentacleConfig("6", 270, LONG)
      .setIpAddress(PIXLITE_2)
      .setBottomChannel(7)
      .setLeftChannel(8)
      .setRightChannel(9),

    new TentacleConfig("7", 315, SHORT)
      .setIpAddress(PIXLITE_2)
      .setBottomChannel(10)
      .setLeftChannel(11)
      .setRightChannel(12),
  };
}
