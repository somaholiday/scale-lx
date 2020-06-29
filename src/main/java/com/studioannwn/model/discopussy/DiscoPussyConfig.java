package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

import static com.studioannwn.model.discopussy.DatalineConfig.*;
import static com.studioannwn.model.discopussy.DiscoPussyConfig.BarDimensions.*;
import static com.studioannwn.model.discopussy.TentacleConfig.Size.LONG;
import static com.studioannwn.model.discopussy.TentacleConfig.Size.SHORT;
import static com.studioannwn.util.DistanceConstants.*;

public class DiscoPussyConfig {
  // IP addresses of PixLite16s (for tentacles)
  public final static String PIXLITE16_1 = "192.168.2.18";
  public final static String PIXLITE16_2 = "192.168.2.19";

  // IP address of PixLite4 (for bar)
  public final static String PIXLITE4 = "192.168.2.20";

  // position of disco ball
  public final static LXVector DISCO_BALL_CENTER = new LXVector(0, 0, 0);

  // radius of disco ball
  public final static float DISCO_BALL_RADIUS = 24 * INCHES;

  // space between disco ball and tentacle
  public final static float DISCO_BALL_TENTACLE_OFFSET = 6 * INCHES;

  // distance of datalines from center of tentacle triangle
  public final static float DATALINE_DISTANCE = 1 * INCHES;

  // space between LEDs
  public final static float LED_PITCH = 1 * METER / 60;

  public final static class LEDModule {
    // LEDs per module
    public final static int POINT_COUNT = 4;

    // space between modules
    public final static float MODULE_PITCH = 4 * INCHES;
  }

  public final static class BarDimensions {
    // bar length (north-south)
    public final static float LENGTH = 15 * FEET;

    // bar width (east-west)
    public final static float WIDTH = 30 * FEET;

    public final static LXVector NW_CORNER = new LXVector(-WIDTH * 0.5f, LENGTH * 0.5f, 0);
    public final static LXVector NE_CORNER = new LXVector(WIDTH * 0.5f, LENGTH * 0.5f, 0);
    public final static LXVector SW_CORNER = new LXVector(-WIDTH * 0.5f, -LENGTH * 0.5f, 0);
    public final static LXVector SE_CORNER = new LXVector(WIDTH * 0.5f, -LENGTH * 0.5f, 0);
    public final static LXVector W_CENTER = new LXVector(-WIDTH * 0.5f, 0, 0);
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
      .setIpAddress(PIXLITE16_1)
      .setBottomChannel(1)
      .setLeftChannel(2)
      .setRightChannel(3),

    new TentacleConfig("1", 45, SHORT)
      .setIpAddress(PIXLITE16_1)
      .setBottomChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),

    new TentacleConfig("2", 90, LONG)
      .setIpAddress(PIXLITE16_1)
      .setBottomChannel(7)
      .setLeftChannel(8)
      .setRightChannel(9),

    new TentacleConfig("3", 135, SHORT)
      .setIpAddress(PIXLITE16_1)
      .setBottomChannel(10)
      .setLeftChannel(11)
      .setRightChannel(12),

    new TentacleConfig("4", 180, LONG)
      .setIpAddress(PIXLITE16_2)
      .setBottomChannel(1)
      .setLeftChannel(2)
      .setRightChannel(3),

    new TentacleConfig("5", 225, SHORT)
      .setIpAddress(PIXLITE16_2)
      .setBottomChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),

    new TentacleConfig("6", 270, LONG)
      .setIpAddress(PIXLITE16_2)
      .setBottomChannel(7)
      .setLeftChannel(8)
      .setRightChannel(9),

    new TentacleConfig("7", 315, SHORT)
      .setIpAddress(PIXLITE16_2)
      .setBottomChannel(10)
      .setLeftChannel(11)
      .setRightChannel(12),
  };

   /*
   BAR CONFIGURATION


   */

  private final DatalineConfig[] barDatalines = new DatalineConfig[] {
    new DatalineConfig("BAR_1", PIXLITE4, 1)
      .addStrip(137, W_CENTER, NORTH, LED_PITCH * 0.5f)
      .addStrip(549, NW_CORNER, EAST)
      .addStrip(25, NE_CORNER, SOUTH),
    new DatalineConfig("BAR_2", PIXLITE4, 2)
      .addStrip(137, W_CENTER, SOUTH, LED_PITCH * 0.5f)
      .addStrip(549, SW_CORNER, EAST)
      .addStrip(25, SE_CORNER, NORTH)
  };

  public TentacleConfig[] getTentacleConfigs() {
    return tentacles;
  }

  public DatalineConfig[] getBarDatalineConfigs() {
    return barDatalines;
  }
}
