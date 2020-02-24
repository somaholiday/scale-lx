package com.studioannwn;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.studioannwn.output.ScaleLayout;
import com.studioannwn.util.NetworkInterfaces;

import heronarts.lx.output.ArtNetDatagram;
import heronarts.lx.output.LXDatagram;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.parameter.LXParameterListener;
import heronarts.lx.studio.LXStudio;
import heronarts.lx.studio.LXStudio.UI;
import heronarts.p3lx.ui.component.UICollapsibleSection;
import heronarts.p3lx.ui.component.UIItemList;

public class UIOutputControls extends UICollapsibleSection {
  LXStudio.UI ui;

  public UIOutputControls(LXStudio.UI ui, ScaleLayout layout) {
    super(ui, 0, 0, ui.leftPane.global.getContentWidth(), 200);

    this.ui = ui;

    setTitle("OUTPUT");

    addNetworkInterfaceList();
    addDatagramList(layout);
  }

  private void addNetworkInterfaceList() {
    List<NetworkInterfaceItem> items = new ArrayList<NetworkInterfaceItem>();
    for (InetAddress address : NetworkInterfaces.getNetworkInterfaceInetAddresses()) {
      items.add(new NetworkInterfaceItem(address));
    }
    UIItemList.ScrollList list =  new UIItemList.ScrollList(ui, 0, 0, getContentWidth(), getContentHeight()*0.5f);
    list.setShowCheckboxes(true);
    list.setItems(items);
    list.addToContainer(this);
  }

  private void addDatagramList(ScaleLayout layout) {
    List<OutputItem> items = new ArrayList<OutputItem>();
    for (LXDatagram datagram : layout.datagrams) {
      items.add(new OutputItem(datagram));
    }
    UIItemList.ScrollList list =  new UIItemList.ScrollList(ui, 0, getContentHeight()*0.5f, getContentWidth(), getContentHeight()*0.5f);
    list.setShowCheckboxes(true);
    list.setItems(items);
    list.addToContainer(this);
  }
}

class NetworkInterfaceItem extends UIItemList.Item {
  private final InetAddress address;

  private boolean isActive;

  public NetworkInterfaceItem(InetAddress address) {
    this.address = address;
  }

  public boolean isActive() {
    return isActive;
  }

  public int getActiveColor(UI ui) {
    return ui.theme.getAttentionColor();
  }

  public boolean isChecked() {
    return isActive;
  }

  public void onActivate() {
    // TODO implement change handler
  }

  public void onCheck(boolean checked) {
    // TODO implement change handler
    isActive = checked;
  }

  @Override
  public String getLabel() {
    return address.toString();
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