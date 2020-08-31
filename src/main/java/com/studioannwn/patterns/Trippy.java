package com.studioannwn.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.pattern.LXPattern;

import static com.studioannwn.util.NoiseUtils.*;

@LXCategory("Texture")
public class Trippy extends LXPattern {

    public final CompoundParameter scale = new CompoundParameter("scale", 0.7f);
    public final CompoundParameter hue = new CompoundParameter("hue", 0, 0, 360);
    public final CompoundParameter detail = new CompoundParameter("detail", 0.2f, 0.1, 1);
    public final CompoundParameter range = new CompoundParameter("range", 360, 0, 360);
    public final CompoundParameter xSpeed = new CompoundParameter("xSpeed", 0.1f, -1, 1);
    public final CompoundParameter ySpeed = new CompoundParameter("ySpeed", 0.1f, -1, 1);
    public final CompoundParameter zSpeed = new CompoundParameter("zSpeed", 0.4f, -1, 1);
    public final CompoundParameter speed = new CompoundParameter("speed", 0.3f);

    public Trippy(LX lx) {
        super(lx);
        addParameter(scale);
        addParameter(hue);
        addParameter(detail);
        addParameter(range);
        addParameter(xSpeed);
        addParameter(ySpeed);
        addParameter(zSpeed);
        addParameter(speed);
    }

    private int counter = 0;

    private float x = 0;
    private float y = 0;
    private float z = 0;

    public void run(double deltaMs) {
        x += deltaMs * xSpeed.getValuef() / 2500;
        y += deltaMs * ySpeed.getValuef() / 2500;
        z += deltaMs * zSpeed.getValuef() / 2500;

        float s = scale.getValuef() / 100;

        for (LXPoint p : model.points) {
            float n = noise(
                s * p.x + x,
                s * p.y + y,
                s * p.z + z
            );
            float d = detail.getValuef() * n * 2000 + counter;
            float r = d % range.getValuef();
            colors[p.index] = lx.hsb(hue.getValuef() + r, 100, 100);
        }
        counter += speed.getValuef() * 5;
    }
}
