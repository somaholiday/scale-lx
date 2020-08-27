package com.studioannwn.patterns;

import com.studioannwn.model.discopussy.DiscoPussyModel;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.pattern.LXPattern;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.DiscreteParameter;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.parameter.LXParameterListener;

import java.util.List;

@LXCategory(LXCategory.TEST)
public class DatalineSelector extends LXPattern {

  private final DiscoPussyModel model;

  private final DiscreteParameter selectedDataline;
  private final DiscreteParameter selectedStrip;

  public DatalineSelector(LX lx) {
    super(lx);
    this.model = (DiscoPussyModel) lx.getModel();
    this.selectedDataline = new DiscreteParameter("dataline", getDatalineIds());
    this.selectedStrip = new DiscreteParameter("strip", getSelectedStrips().size());

    selectedDataline.addListener(new LXParameterListener() {
      public void onParameterChanged(LXParameter parameter) {
        selectedStrip.setRange(getSelectedStrips().size());
      }
    });

    addParameter(selectedDataline);
    addParameter(selectedStrip);
  }

  public void run(double deltaMs) {
    setColors(0);
    for (DiscoPussyModel.Strip strip : getSelectedStrips()) {
      for (LXPoint p : strip.points) {
        colors[p.index] = lx.hsb(0, 100, 40);
      }
    }
    for (LXPoint p : getSelectedStrip().points) {
      colors[p.index] = LXColor.GREEN;
    }
  }

  private String[] getDatalineIds() {
    String[] ids = new String[DiscoPussyModel.getDatalinesArray().length];
    for (int i = 0; i < ids.length; i++) {
      ids[i] = DiscoPussyModel.getDatalinesArray()[i].getId();
    }
    return ids;
  }

  private DiscoPussyModel.Dataline getSelectedDataline() {
    return DiscoPussyModel.getDatalinesArray()[selectedDataline.getValuei()];
  }

  private List<DiscoPussyModel.Strip> getSelectedStrips() {
    return getSelectedDataline().getStrips();
  }

  private DiscoPussyModel.Strip getSelectedStrip() {
    return getSelectedStrips().get(selectedStrip.getValuei());
  }
}
