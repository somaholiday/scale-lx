package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

import javax.naming.NamingEnumeration;

import static com.studioannwn.model.discopussy.DatalineConfig.*;
import static com.studioannwn.model.discopussy.DiscoPussyConfig.BarDimensions.*;
import static com.studioannwn.model.discopussy.TentacleConfig.Size.LONG;
import static com.studioannwn.model.discopussy.TentacleConfig.Size.SHORT;
import static com.studioannwn.util.DistanceConstants.*;

public class DiscoPussyConfig {
  // IP addresses of PixLite16s (for tentacles)
  public final static String PIXLITE16_1 = "192.168.1.136";
  public final static String PIXLITE16_2 = "192.168.1.137";

  // IP address of PixLite4 (for bar)
  public final static String PIXLITE4 = "192.168.1.145";

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

            top
           /   \
          /     \
         /       \
        /         \
       /           \
    left ---------- right
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
      .setTopChannel(14)
      .setLeftChannel(13)
      .setRightChannel(15),//wrong

new TentacleConfig("2", 45, LONG)
      .setIpAddress(PIXLITE16_1)
      .setTopChannel(9)
      .setLeftChannel(11)
      .setRightChannel(10),

new TentacleConfig("3", 105, SHORT)
      .setIpAddress(PIXLITE16_1)
      .setTopChannel(14)
      .setLeftChannel(12)
      .setRightChannel(13),

new TentacleConfig("4", 145, LONG)
      .setIpAddress(PIXLITE16_1)
      .setTopChannel(4)
      .setLeftChannel(5)
      .setRightChannel(6),

new TentacleConfig("5", 165, SHORT)
      .setIpAddress(PIXLITE16_1)
      .setTopChannel(1)
      .setLeftChannel(3)
      .setRightChannel(2),

new TentacleConfig("6", 225, LONG)
      .setIpAddress(PIXLITE16_2)
      .setTopChannel(8)
      .setLeftChannel(7)
      .setRightChannel(6),

new TentacleConfig("7", 300, SHORT)
      .setIpAddress(PIXLITE16_2)
      .setTopChannel(5)
      .setLeftChannel(3)
      .setRightChannel(4),

new TentacleConfig("0", 315, LONG)
      .setIpAddress(PIXLITE16_2)
      .setTopChannel(12)
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

  public final static class BarDimensions {
    // This got ugly and embarrassing fast.
    // The original layout was based on dimensions that weren't quite right, so now basing the corners off of LED counts.
    // This was done on-the-fly and would be much much saner if the DatalineConfig just kept a running position cursor.
    public final static int WEST_NORTH_COUNT = 114;
    public final static int WEST_SOUTH_COUNT = 117;
    public final static int WEST_TOTAL_COUNT = WEST_NORTH_COUNT + WEST_SOUTH_COUNT;

    public final static int SOUTH_WEST_COUNT = 407;
    public final static int SOUTH_EAST_COUNT = 274;
    public final static int SOUTH_TOTAL_COUNT = SOUTH_WEST_COUNT + SOUTH_EAST_COUNT;

    public final static int NORTH_WEST_COUNT = 406;
    public final static int NORTH_EAST_COUNT = 274;

    // bar width (east-west)
    public final static float WIDTH = (SOUTH_TOTAL_COUNT - 1) * LED_PITCH;

    public final static LXVector W_CENTER = new LXVector(-WIDTH * 0.5f, 0, 0);
    public final static LXVector NW_CORNER = W_CENTER.copy().add(0, WEST_NORTH_COUNT * LED_PITCH, 0);
    public final static LXVector NE_CORNER = NW_CORNER.copy().add(SOUTH_TOTAL_COUNT * LED_PITCH, 0, 0);
    public final static LXVector SW_CORNER = W_CENTER.copy().add(0, -WEST_SOUTH_COUNT * LED_PITCH, 0);
    public final static LXVector SE_CORNER = SW_CORNER.copy().add(SOUTH_TOTAL_COUNT * LED_PITCH, 0, 0);

  }

  private final StripConfig[] BAR_STRIPS_DATALINE_SW = new StripConfig[]{
    new StripConfig()
      .setLedCount(WEST_SOUTH_COUNT)
      .setStart(W_CENTER)
      .setDirection(SOUTH)
      .setPreSpacing(LED_PITCH * 0.5f),
    new StripConfig()
      .setLedCount(SOUTH_WEST_COUNT)
      .setStart(SW_CORNER)
      .setDirection(EAST)
      .setPreSpacing(LED_PITCH)
  };

  private final StripConfig[] BAR_STRIPS_DATALINE_SE = new StripConfig[] {
    new StripConfig()
      .setLedCount(SOUTH_EAST_COUNT)
      .setStart(SW_CORNER)
      .setDirection(EAST)
      .setPreSpacing(SOUTH_WEST_COUNT * LED_PITCH),
    new StripConfig()
      .setLedCount(26)
      .setStart(SE_CORNER)
      .setDirection(NORTH)
  };

  private final StripConfig[] BAR_STRIPS_DATALINE_NW = new StripConfig[] {
    new StripConfig()
      .setLedCount(WEST_NORTH_COUNT)
      .setStart(W_CENTER)
      .setDirection(NORTH)
      .setPreSpacing(LED_PITCH * 0.5f),
    new StripConfig()
      .setLedCount(NORTH_WEST_COUNT)
      .setStart(NW_CORNER)
      .setDirection(EAST)
      .setPreSpacing(LED_PITCH * 1),
  };

  private final StripConfig[] BAR_STRIPS_DATALINE_NE = new StripConfig[] {
    new StripConfig()
      .setLedCount(NORTH_EAST_COUNT)
      .setStart(NW_CORNER)
      .setDirection(EAST)
      .setPreSpacing((NORTH_WEST_COUNT + 1) * LED_PITCH),
    new StripConfig()
      .setLedCount(25)
      .setStart(NE_CORNER)
      .setDirection(SOUTH)
  };

  private final DatalineConfig[] barDatalines = new DatalineConfig[] {
    new DatalineConfig("BarNE", PIXLITE4, 3, BAR_STRIPS_DATALINE_NE),
    new DatalineConfig("BarNW", PIXLITE4, 4, BAR_STRIPS_DATALINE_NW),
    new DatalineConfig("BarSW", PIXLITE4, 2, BAR_STRIPS_DATALINE_SW),
    new DatalineConfig("BarSE", PIXLITE4, 1, BAR_STRIPS_DATALINE_SE),
  };

  public TentacleConfig[] getTentacleConfigs() {
    return tentacles;
  }

  public DatalineConfig[] getBarDatalineConfigs() {
    return barDatalines;
  }
}
