package com.studioannwn.effect.discopussy;

import com.studioannwn.model.discopussy.DiscoPussyModel;
import heronarts.lx.LX;
import heronarts.lx.color.LXColor;
import heronarts.lx.effect.LXModelEffect;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.BooleanParameter;
import heronarts.lx.parameter.LXParameter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DiscoPussyMask extends LXModelEffect<DiscoPussyModel> {
  public final BooleanParameter tentaclesEnabled = new BooleanParameter("tentacles", true);
  public final BooleanParameter barEnabled = new BooleanParameter("bar", true);

  private boolean[] pointMask;

  public DiscoPussyMask(LX lx) {
    super(lx);

    addParameter(tentaclesEnabled.getLabel(), tentaclesEnabled);
    addParameter(barEnabled.getLabel(), barEnabled);

    pointMask = new boolean[model.points.length];

    refreshPoints();
  }

  private void refreshPoints() {
    Set<LXPoint> activePoints = new HashSet<>();

    if (tentaclesEnabled.isOn()) {
      activePoints.addAll(model.getTentaclePoints());
    }
    if (barEnabled.isOn()) {
      activePoints.addAll(model.getBarPoints());
    }

    Arrays.fill(pointMask, false);
    for (LXPoint p : activePoints) {
      pointMask[p.index] = true;
    }
  }

  @Override
  public void onParameterChanged(LXParameter p) {
    refreshPoints();
  }

  @Override
  protected void run(double deltaMs, double enabledAmount) {
    if (enabledAmount > 0) {
      for (int i = 0; i < pointMask.length; ++i) {
        if (!pointMask[i]) {
          colors[i] = LXColor.ALPHA_MASK;
        }
      }
    }
  }
}
