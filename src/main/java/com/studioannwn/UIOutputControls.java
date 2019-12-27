package com.studioannwn;

import java.util.ArrayList;
import java.util.List;

import heronarts.lx.output.ArtNetDatagram;
import heronarts.lx.output.LXDatagram;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.parameter.LXParameterListener;
import heronarts.lx.studio.LXStudio;
import heronarts.lx.studio.LXStudio.UI;
import heronarts.p3lx.ui.component.UICollapsibleSection;
import heronarts.p3lx.ui.component.UIItemList;

public class UIOutputControls extends UICollapsibleSection {
  public UIOutputControls(LXStudio.UI ui, ScaleLayout layout) {
    super(ui, 0, 0, ui.leftPane.global.getContentWidth(), 100);
    setTitle("OUTPUT");

    List<OutputItem> items = new ArrayList<OutputItem>();
    for (LXDatagram datagram : layout.datagrams) {
      items.add(new OutputItem(datagram));
    }
    UIItemList.ScrollList list =  new UIItemList.ScrollList(ui, 0, 0, getContentWidth(), getContentHeight());
    list.setShowCheckboxes(true);
    list.setItems(items);
    list.addToContainer(this);
  }
}

class OutputItem extends UIItemList.Item {

  private final LXDatagram datagram;

  public OutputItem(LXDatagram datagram) {
    this.datagram = datagram;
    datagram.error.addListener(new LXParameterListener() {
      public void onParameterChanged(LXParameter p) {
        // redraw();
      }
    });
  }

  public boolean isActive() {
    return this.datagram.error.isOn();
  }

  public int getActiveColor(UI ui) {
    return ui.theme.getAttentionColor();
  }

  public boolean isChecked() {
    return this.datagram.enabled.isOn();
  }

  public void onActivate() {
    this.datagram.enabled.toggle();
  }

  public void onCheck(boolean checked) {
    this.datagram.enabled.setValue(checked);
  }

  public String getLabel() {
    if (datagram instanceof ArtNetDatagram) {
      ArtNetDatagram artNetDatagram = (ArtNetDatagram) datagram;
      return String.format("%s Universe %d", artNetDatagram.getAddress().getHostAddress(), artNetDatagram.getUniverseNumber() + 1);
    }
    return String.format("%s", this.datagram.getAddress().getHostAddress());
  }
}