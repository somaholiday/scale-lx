package com.studioannwn.patterns;

import static com.studioannwn.util.MathUtils.*;

import java.util.ArrayList;
import java.util.List;

import com.studioannwn.patterns.base.DPat;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.parameter.DiscreteParameter;
import heronarts.lx.transform.LXVector;
import processing.core.PVector;

@LXCategory("Texture")
public class Voronoi extends DPat {
  final CompoundParameter speed = new CompoundParameter("SPEED", 1.8, 0, 10);
  final CompoundParameter width = new CompoundParameter("WIDTH", 0.2, 0.1, 1);
  final DiscreteParameter num = new DiscreteParameter("NUM", 14, 5, 28);
  private final List<Site> sites = new ArrayList<Site>();
  private float xMaxDist = model.xMax - model.xMin;
  private float yMaxDist = model.yMax - model.yMin;
  private float zMaxDist = model.zMax - model.zMin;

  class Site {
    float xPos = 0;
    float yPos = 0;
    float zPos = 0;
    LXVector velocity = new LXVector(0, 0, 0);

    public Site() {
      xPos = random(model.xMin, model.xMax);
      yPos = random(model.yMin, model.yMax);
      zPos = random(model.zMin, model.zMax);
      velocity = new LXVector(random(-1, 1), random(-1, 1), random(-1, 1));
    }

    public void move(float speed) {
      xPos += speed * velocity.x;
      if ((xPos < model.xMin - 20) || (xPos > model.xMax + 20)) {
        velocity.x *= -1;
      }
      yPos += speed * velocity.y;
      if ((yPos < model.yMin - 20) || (yPos > model.yMax + 20)) {
        velocity.y *= -1;
      }
      zPos += speed * velocity.z;
      if ((zPos < model.zMin - 20) || (zPos > model.zMax + 20)) {
        velocity.z *= -1;
      }
    }
  }

  public Voronoi(LX lx) {
    super(lx);
    addParameter(speed.getLabel().toLowerCase(), speed);
    addParameter(width.getLabel().toLowerCase(), width);
    addParameter(num.getLabel().toLowerCase(), num);
  }

  @Override
  protected void StartRun(double deltaMs) {
    for (Site site : sites) {
      site.move(speed.getValuef());
    }
  }

  @Override
  protected int CalcPoint(PVector p) {
    float numSites = num.getValuef();
    float lineWidth = width.getValuef();

    while (sites.size() > numSites) {
      sites.remove(0);
    }

    while (sites.size() < numSites) {
      sites.add(new Site());
    }

    float minDistSq = 10000;
    float nextMinDistSq = 10000;
    float calcRestraintConst = 20 / (numSites + 15);
    lineWidth = lineWidth * 40 / (numSites + 20);

    for (Site site : sites) {
      float dx = site.xPos - p.x;
      float dy = site.yPos - p.y;
      float dz = site.zPos - p.z;

      if (abs(dy) < yMaxDist * calcRestraintConst && abs(dx) < xMaxDist * calcRestraintConst
          && abs(dz) < zMaxDist * calcRestraintConst) { // restraint on calculation
        float distSq = dx * dx + dy * dy + dz * dz;
        if (distSq < nextMinDistSq) {
          if (distSq < minDistSq) {
            nextMinDistSq = minDistSq;
            minDistSq = distSq;
          } else {
            nextMinDistSq = distSq;
          }
        }
      }
    }
    return LX.hsb(palette.getHuef(), palette.getSaturationf(),
        max(0f, min(100, 100 - sqrt(nextMinDistSq - minDistSq) / lineWidth)));
  }
}
