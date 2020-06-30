package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

import static com.studioannwn.model.discopussy.DatalineConfig.*;
import static com.studioannwn.model.discopussy.DiscoPussyConfig.BarDimensions.*;
import static com.studioannwn.model.discopussy.TentacleConfig.Size.LONG;
import static com.studioannwn.model.discopussy.TentacleConfig.Size.SHORT;
import static com.studioannwn.util.DistanceConstants.*;

public class DiscoPussyConfig {
  // IP addresses of PixLite16s (for tentacles)
  public final static String PIXLITE16_1 = "192.168.0.136";
  public final static String PIXLITE16_2 = "192.168.0.137";

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

  // private final TentacleConfig[] tentacles = new TentacleConfig[]{

  //   new TentacleConfig("0", 315, LONG)
  //     .setIpAddress(PIXLITE16_1)
  //     .setBottomChannel(1)
  //     .setLeftChannel(2)
  //     .setRightChannel(3),

  //   new TentacleConfig("1", 10, SHORT)
  //     .setIpAddress(PIXLITE16_1)
  //     .setBottomChannel(4)
  //     .setLeftChannel(5)
  //     .setRightChannel(6),

  //   new TentacleConfig("2", 45, LONG)
  //     .setIpAddress(PIXLITE16_1)
  //     .setBottomChannel(10)
  //     .setLeftChannel(11)
  //     .setRightChannel(12),

  //   new TentacleConfig("3", 105, SHORT)
  //     .setIpAddress(PIXLITE16_1)
  //     .setBottomChannel(13)
  //     .setLeftChannel(14)
  //     .setRightChannel(9),

  //   new TentacleConfig("4", 145, LONG)
  //     .setIpAddress(PIXLITE16_2)
  //     .setBottomChannel(1)
  //     .setLeftChannel(2)
  //     .setRightChannel(3),

  //   new TentacleConfig("5", 165, SHORT)
  //     .setIpAddress(PIXLITE16_2)
  //     .setBottomChannel(4)
  //     .setLeftChannel(5)
  //     .setRightChannel(6),

  //   new TentacleConfig("6", 225, LONG)
  //     .setIpAddress(PIXLITE16_2)
  //     .setBottomChannel(10)
  //     .setLeftChannel(11)
  //     .setRightChannel(9),

  //   new TentacleConfig("7", 300, SHORT)
  //     .setIpAddress(PIXLITE16_2)
  //     .setBottomChannel(13)
  //     .setLeftChannel(14)
  //     .setRightChannel(12),
  // };

private final TentacleConfig[] tentacles = new TentacleConfig[]{


new TentacleConfig("1", 10, SHORT)
      .setIpAddress(PIXLITE16_2)
      .setBottomChannel(14)
      .setLeftChannel(13)
      .setRightChannel(15),//wrong

new TentacleConfig("2", 45, LONG)
      .setIpAddress(PIXLITE16_1)
      .setBottomChannel(9)
      .setLeftChannel(11)      
      .setRightChannel(10),

new TentacleConfig("3", 105, SHORT)
      .setIpAddress(PIXLITE16_1)
      .setBottomChannel(14)
      .setLeftChannel(12)
      .setRightChannel(13),

new TentacleConfig("4", 145, LONG)
      .setIpAddress(PIXLITE16_1)
      .setBottomChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),

new TentacleConfig("5", 165, LONG)
      .setIpAddress(PIXLITE16_1)
      .setBottomChannel(1)
      .setLeftChannel(3)
      .setRightChannel(2),

new TentacleConfig("6", 225, LONG)
      .setIpAddress(PIXLITE16_2)
      .setBottomChannel(8)
      .setLeftChannel(7)
      .setRightChannel(6),

new TentacleConfig("7", 300, SHORT)
      .setIpAddress(PIXLITE16_2)
      .setBottomChannel(5)
      .setLeftChannel(3)
      .setRightChannel(4),
  
new TentacleConfig("0", 315, LONG)
      .setIpAddress(PIXLITE16_2)
      .setBottomChannel(12)
      .setLeftChannel(11)
      .setRightChannel(9),

    };


   /*
    BAR CONFIGURATION

                   N
       ---------------------------
       |                         |
       |
    W  |                            E
       |
       |                         |
       ---------------------------
                   S

    The bar consists of two datalines of three strips each.

    Strip configs have the following methods:
      setLedCount(numberOfLeds) sets the number of LEDs in the strip
      setStart(startPosition) sets where the strip starts
        options: NW_CORNER, NE_CORNER, SW_CORNER, SE_CORNER, W_CENTER
      setDirection(direction) sets the direction the strip runs
        options: NORTH, SOUTH, EAST, WEST
      setPreSpacing(distance) sets the distance from the start point to the first LED
   */

  // List of strips for bar output 1
  private final StripConfig[] BAR_STRIPS_DATALINE_1 = new StripConfig[] {
    new StripConfig()
      .setLedCount(137)
      .setStart(W_CENTER)
      .setDirection(NORTH)
      .setPreSpacing(LED_PITCH * 0.5f),
    new StripConfig()
      .setLedCount(549)
      .setStart(NW_CORNER)
      .setDirection(EAST),
    new StripConfig()
      .setLedCount(25)
      .setStart(NE_CORNER)
      .setDirection(SOUTH),
  };

  // List of strips for bar output 2
  private final StripConfig[] BAR_STRIPS_DATALINE_2 = new StripConfig[] {
    new StripConfig()
      .setLedCount(137)
      .setStart(W_CENTER)
      .setDirection(SOUTH)
      .setPreSpacing(LED_PITCH * 0.5f),
    new StripConfig()
      .setLedCount(549)
      .setStart(SW_CORNER)
      .setDirection(EAST),
    new StripConfig()
      .setLedCount(25)
      .setStart(SE_CORNER)
      .setDirection(NORTH),
  };

  private final DatalineConfig[] barDatalines = new DatalineConfig[] {
    new DatalineConfig("BAR_1", PIXLITE4, 1, BAR_STRIPS_DATALINE_1),
    new DatalineConfig("BAR_2", PIXLITE4, 2, BAR_STRIPS_DATALINE_2)
  };

  public TentacleConfig[] getTentacleConfigs() {
    return tentacles;
  }

  public DatalineConfig[] getBarDatalineConfigs() {
    return barDatalines;
  }
}
