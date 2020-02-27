package com.studioannwn.patterns;

import java.util.Arrays;
import java.util.List;

import com.studioannwn.model.ScaleModel;
import com.studioannwn.patterns.base.ModelPattern;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.DiscreteParameter;

@LXCategory(LXCategory.TEST)
public class FixtureTest extends ModelPattern<ScaleModel> {
  public final DiscreteParameter fixtureKnob;

  public FixtureTest(LX lx) {
    super(lx);
    fixtureKnob = new DiscreteParameter("Fixture", 0, 0, model.children.length).setDescription("Fixture number");
    addParameter(fixtureKnob);
  }

  public void run(double deltaMs) {
    int fixtureNumber = fixtureKnob.getValuei();
    String fixtureKey = String.format("fixture-%d", fixtureNumber);
    List<LXPoint> points;
    int color;
    for (LXModel fixture : model.children) {
      color = Arrays.stream(fixture.getKeys()).anyMatch(fixtureKey::equals) ? LXColor.WHITE : LXColor.BLACK;
      points = fixture.getPoints();

      for (LXPoint p : points) {
        colors[p.index] = color;
      }
    }
  }
}
