package com.studioannwn;

import java.util.ArrayList;
import java.util.List;

import com.studioannwn.output.ScaleOutput;

import heronarts.lx.LX;
import heronarts.lx.output.LXOutput;
import heronarts.lx.output.LXOutputGroup;
import heronarts.lx.studio.LXStudio.UI;
import heronarts.p3lx.ui.UI2dContainer;
import heronarts.p3lx.ui.UITimerTask;
import heronarts.p3lx.ui.component.UICollapsibleSection;
import heronarts.p3lx.ui.component.UIItemList;

public class UIOutputControls extends UICollapsibleSection {
  private final LX lx;

  private final UIItemList.BasicList outputList;

  public UIOutputControls(LX lx, UI ui) {
    super(ui, 0, 0, ui.leftPane.global.getContentWidth(), 0);

    this.lx = lx;
    setLayout(UI2dContainer.Layout.VERTICAL);

    this.outputList = new UIItemList.BasicList(ui, 0, 24, getContentWidth(), getContentHeight());
    this.outputList.setShowCheckboxes(true);
    this.outputList.addToContainer(this);

    setTitle("OUTPUT");
    this.outputList.setDescription("Available outputs");

    updateItems();

    // TODO: this should be based on changes to the lx.engine.output list, not
    // running constantly.
    addLoopTask(new UITimerTask(30, UITimerTask.Mode.FPS) {
      @Override
      public void run() {
        updateItems();
      }
    });
  }

  private void updateItems() {
    List<OutputItem> outputItems = getOutputList();
    this.outputList.setItems(outputItems);
  }

  private List<OutputItem> getOutputList() {
    List<OutputItem> items = new ArrayList<OutputItem>();
    addOutputToList(lx.engine.output, items);

    return items;
  }

  private void addOutputToList(LXOutput output, List<OutputItem> items) {
    if (output instanceof ScaleOutput) {
      items.add(new OutputItem((ScaleOutput)output));
    }

    if (output instanceof LXOutputGroup) {
      ((LXOutputGroup) output).children.forEach(childOutput -> {
        addOutputToList(childOutput, items);
      });
    }
  }
}

class OutputItem extends UIItemList.Item {

  private final ScaleOutput output;

  public OutputItem(ScaleOutput output) {
    this.output = output;
  }

  public boolean isChecked() {
    return this.output.selected.isOn();
  }

  public void onCheck(boolean checked) {
    this.output.selected.setValue(checked);
  }

  public String getLabel() {
    return this.output.toString();
  }
}
