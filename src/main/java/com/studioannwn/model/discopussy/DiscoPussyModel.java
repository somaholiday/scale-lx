package com.studioannwn.model.discopussy;

import com.studioannwn.model.discopussy.builder.BarBuilder;
import com.studioannwn.model.discopussy.builder.TentacleBuilder;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import javafx.scene.chart.BarChartBuilder;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class DiscoPussyModel extends LXModel {
  private static List<Tentacle> tentacles = new ArrayList<>();
  private static List<Dataline> datalines = new ArrayList<>();
  private static Bar bar;
  private static DiscoPussyConfig config;

  public DiscoPussyModel(DiscoPussyConfig config) {
    super(setup(config));
    DiscoPussyModel.config = config;
  }

  private static Strip[] setup(DiscoPussyConfig config) {
    List<Strip> strips = new ArrayList<>();

    // build tentacles
    List<Tentacle> tentacles = TentacleBuilder.buildTentacles(config);

    for (Tentacle tentacle : tentacles) {
      List<Dataline> datalines = tentacle.getDatalines();

      for (Dataline dataline : datalines) {
        strips.addAll(dataline.getStrips());
      }

      DiscoPussyModel.datalines.addAll(datalines);
    }

    DiscoPussyModel.tentacles.addAll(tentacles);

    // build bar
    DiscoPussyModel.bar = BarBuilder.buildBar(config);

    List<Dataline> barDatalines = bar.getDatalines();

    for (Dataline dataline : barDatalines) {
      strips.addAll(dataline.getStrips());
    }

    DiscoPussyModel.datalines.addAll(barDatalines);

    return stripsListToArray(strips);
  }

  public static DiscoPussyConfig getConfig() {
    return config;
  }

  public static List<Tentacle> getTentacles() {
    return tentacles;
  }

  public static Bar getBar() {
    return bar;
  }

  public static List<Dataline> getDatalines() {
    return datalines;
  }

  public static Dataline[] getDatalinesArray() {
    return datalines.stream().toArray(Dataline[]::new);
  }

  public static class Bar extends LXModel {
    private List<Dataline> datalines = new ArrayList<>();

    public Bar(List<Dataline> datalines) {
      this.datalines.addAll(datalines);
    }

    public List<Dataline> getDatalines() {
      return datalines;
    }
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

