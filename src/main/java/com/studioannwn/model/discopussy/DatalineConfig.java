package com.studioannwn.model.discopussy;

import heronarts.lx.transform.LXVector;

import java.util.ArrayList;
import java.util.List;

public class DatalineConfig {
  public static final LXVector NORTH = new LXVector(0, 1, 0);
  public static final LXVector SOUTH = new LXVector(0, -1, 0);
  public static final LXVector EAST = new LXVector(1, 0, 0);
  public static final LXVector WEST = new LXVector(-1, 0, 0);

  private final String id;
  private final String ipAddress;
  private final int channel;

  private List<StripConfig> stripConfigs = new ArrayList<>();

  public DatalineConfig(String id, String ipAddress, int channel) {
    this.id = id;
    this.ipAddress = ipAddress;
    this.channel = channel;
  }

  public DatalineConfig addStrip(int ledCount, LXVector start, LXVector direction) {
    stripConfigs.add(new StripConfig(ledCount, start, direction));
    return this;
  }

  public DatalineConfig addStrip(int ledCount, LXVector start, LXVector direction, float prespacing) {
    stripConfigs.add(new StripConfig(ledCount, start, direction, prespacing));
    return this;
  }

  public String getId() {
    return id;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public int getChannel() {
    return channel;
  }

  public List<StripConfig> getStripConfigs() {
    return stripConfigs;
  }
}
