package com.studioannwn.model.discopussy;

import com.studioannwn.model.discopussy.builder.TentacleBuilder;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class DiscoPussyModel extends LXModel {
  private static List<Dataline> dataline = new ArrayList<>();
  private DiscoPussyConfig config;

  public DiscoPussyModel(DiscoPussyConfig config) {
    super(setup(config));
    this.config = config;
  }

  private static Strip[] setup(DiscoPussyConfig config) {
    List<Tentacle> tentacles = TentacleBuilder.buildTentacles(config);
    List<Strip> strips = new ArrayList<>();

    for (Tentacle tentacle : tentacles) {
      for (Dataline dataline : tentacle.getDatalines()) {
        strips.addAll(dataline.getStrips());
      }
    }

    return stripsListToArray(strips);
  }

  public DiscoPussyConfig getConfig() {
    return this.config;
  }

  public static class Tentacle extends LXModel {
    private List<Dataline> datalines = new ArrayList<>();

    public Tentacle(List<Dataline> datalines) {
      this.datalines.addAll(datalines);
    }

    public List<Dataline> getDatalines() {
      return datalines;
    }
  }

  public static class Dataline extends LXModel {
    private String id;
    private String ipAddress;
    private int channel;
    private List<Strip> strips = new ArrayList<>();

    public Dataline(String id, String ipAddress, int channel, List<Strip> strips) {
      super(stripsListToArray(strips));
      this.id = id;
      this.ipAddress = ipAddress;
      this.channel = channel;
      this.strips.addAll(strips);
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

    public List<Strip> getStrips() {
      return strips;
    }

    public Strip[] getStripsArray() {
      return strips.stream().toArray(Strip[]::new);
    }
  }

  public static class Strip extends LXModel {
    public Strip(List<LXPoint> points) {
      super(points);
    }
  }

  // quick hack
  private static Strip[] stripsListToArray(List<Strip> strips) {
    return strips.stream().toArray(Strip[]::new);
  }
}

