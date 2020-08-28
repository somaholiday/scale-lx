package com.studioannwn.output;

import java.net.SocketException;

import heronarts.lx.LX;
import heronarts.lx.output.LXOutputGroup;
import heronarts.lx.parameter.BooleanParameter;

public class ScaleOutput extends LXOutputGroup {
  public final BooleanParameter selected =
    new BooleanParameter("Selected", false)
    .setDescription("Whether the output is selected");

  public ScaleOutput(LX lx) throws SocketException {
    super(lx);
    addParameter("selected", this.selected);
  }
}
