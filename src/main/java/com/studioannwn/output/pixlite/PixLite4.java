package com.studioannwn.output.pixlite;

import heronarts.lx.LX;

public class PixLite4 extends PixLite {
  private static final int OUTPUT_COUNT = 4;
  private static final int UNIVERSES_PER_OUTPUT = 6;

  public PixLite4(LX lx, String ipAddress) {
    super(lx, ipAddress, OUTPUT_COUNT);
  }

  public int getNumUniversesPerOutput() {
    return UNIVERSES_PER_OUTPUT;
  }
}
