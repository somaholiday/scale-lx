package com.studioannwn.output.pixlite;

import heronarts.lx.LX;

public class PixLite16 extends PixLite {
  private static final int OUTPUT_COUNT = 16;
  private static final int UNIVERSES_PER_OUTPUT = 2;

  public PixLite16(LX lx, String ipAddress) {
    super(lx, ipAddress, OUTPUT_COUNT);
  }

  public int getNumUniversesPerOutput() {
    return UNIVERSES_PER_OUTPUT;
  }
}