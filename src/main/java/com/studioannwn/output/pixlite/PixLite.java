package com.studioannwn.output.pixlite;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import com.google.common.collect.Lists;
import com.studioannwn.AnnwnLX;
import com.studioannwn.output.ScaleOutput;

import heronarts.lx.LX;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.output.ArtNetDatagram;
import heronarts.lx.output.LXDatagram;
import heronarts.lx.output.LXOutputGroup;

// NOTE: All output and universe numbers are indexed from 1.
public abstract class PixLite extends LXOutputGroup {
  public static final int LEDS_PER_UNIVERSE = 170;

  private LX lx;
  public final String ipAddress;
  public final int numOutputs;

  public PixLite(LX lx, String ipAddress, int numOutputs) {
    super(lx, ipAddress);
    this.lx = lx;
    this.ipAddress = ipAddress;
    this.numOutputs = numOutputs;
  }

  public PixLite addOutput(int outputIndex, List<LXPoint> points) {
    if (this.children.size() == numOutputs) {
      String message = String.format("PixLite @ %s already has maximum of %d outputs assigned.  Cannot add another.",
          ipAddress, numOutputs);
      throw new RuntimeException(message);
    }

    int numPoints = points.size();
    int maxLEDsPerOutput = LEDS_PER_UNIVERSE * this.getNumUniversesPerOutput();
    if (numPoints > maxLEDsPerOutput) {
      String message = String.format("Max LEDs per output is %d. Output %d has too many points (%d).", maxLEDsPerOutput,
          outputIndex, numPoints);
      throw new RuntimeException(message);
    }

    try {
      addChild(new PixLiteOutput(lx, outputIndex, points));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return this;
  }

  public String toString() {
    return String.format("PixLite - %s", ipAddress);
  }

  abstract int getNumUniversesPerOutput();

  /*
   * PixLiteOutput
   */
  public class PixLiteOutput extends ScaleOutput {
    private final int outputIndex;
    private final int startUniverse;
    private final List<LXPoint> points;

    public PixLiteOutput(LX lx, int outputIndex, List<LXPoint> points) throws SocketException {
      super(lx);

      this.outputIndex = outputIndex;
      this.startUniverse = (outputIndex - 1) * getNumUniversesPerOutput() + 1;
      this.points = points;
      setupDatagrams();
    }

    protected void beforeSend(int[] colors) {
      if (this.selected.isOn()) {
        for (LXPoint p : points) {
          colors[p.index] = LXColor.WHITE;
        }
      }
    }

    private void setupDatagrams() {
      List<List<LXPoint>> pointsByUniverse = Lists.partition(points, LEDS_PER_UNIVERSE);
      int universeOffset = 0;

      if (AnnwnLX.DEBUG) {
        System.out.println(String.format("Output %d (Universes %d-%d)", outputIndex, startUniverse,
            startUniverse + getNumUniversesPerOutput()));
      }

      for (List<LXPoint> universePoints : pointsByUniverse) {
        int[] universeIndexBuffer = new int[universePoints.size()];

        for (int i = 0; i < universeIndexBuffer.length; i++) {
          universeIndexBuffer[i] = universePoints.get(i).index;
        }

        if (AnnwnLX.DEBUG) {
          printUniverseIndexBuffer(startUniverse + universeOffset, universeIndexBuffer);
        }

        LXDatagram datagram = new ArtNetDatagram(lx, universeIndexBuffer, (startUniverse - 1) + universeOffset++); // zero-indexed
        try {
          datagram.setAddress(InetAddress.getByName(ipAddress));
        } catch (Exception e) {
          System.err.println("Exception when setting PixLite datagram IP address: " + e.getMessage());
          e.printStackTrace();
        }

        addChild(datagram);
      }
    }

    private void printUniverseIndexBuffer(int universeIndex, int[] universeIndexBuffer) {
      System.out.println(String.format("\tUniverse %d", universeIndex));
      System.out.print("\t");
      int i = 0;
      for (int index : universeIndexBuffer) {
        System.out.print(index + "\t");
        if (++i % 10 == 0) {
          System.out.print("\n\t");
        }
      }
      System.out.println("\n");
    }

    public int getOutputIndex() {
      return outputIndex;
    }

    public String toString() {
      return String.format("PixLite - %s OP%d", ipAddress, outputIndex);
    }
  }
}
