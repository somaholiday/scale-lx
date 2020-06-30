package com.studioannwn.model;

import com.studioannwn.model.discopussy.DiscoPussyModel;
import heronarts.lx.model.LXModel;

import java.util.List;

public abstract class StripsModel extends LXModel {
  public StripsModel(LXModel[] children) {
    super(children);
  }

  abstract public List<? extends LXModel> getStrips();
}
