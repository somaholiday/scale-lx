package com.studioannwn.patterns.base;

import static processing.core.PConstants.ADD;

import java.util.ArrayList;
import java.util.SplittableRandom;

import com.studioannwn.util.ColorOps8;
import com.studioannwn.util.MathUtils;
import com.studioannwn.util.NoiseUtils;

import heronarts.lx.LX;
import heronarts.lx.pattern.LXPattern;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.BooleanParameter;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.transform.LXVector;
import processing.core.PImage;
import processing.core.PVector;

public abstract class DPat extends LXPattern {
    public ArrayList<DBool> bools = new ArrayList<DBool>();
    public PVector pTrans = new PVector();
    public PVector mMax, mCtr, mHalf;
    public SplittableRandom splittableRandom = new SplittableRandom();

    public float LastJog = -1;
    public float[] xWaveNz, yWaveNz;
    public PVector xyzJog = new PVector(), modmin;

    public float NoiseMove = random(10000);
    public CompoundParameter pSpark;
    public CompoundParameter pWave;
    public CompoundParameter pRotX;
    public CompoundParameter pRotY;
    public CompoundParameter pRotZ;
    public CompoundParameter pSpin;
    public CompoundParameter pTransX;
    public CompoundParameter pTransY;
    public BooleanParameter pXsym, pYsym, pRsym, pXdup, pXtrip, pJog, pGrey;

    public float lxh() {
        return palette.getHuef();
    }

    public int c1c(float a) {
        return MathUtils.round(100 * MathUtils.constrain(a, 0, 1));
    }

    public float interpWv(float i, float[] vals) {
        i = Math.max(0, Math.min(vals.length - 1, i));
        return interp(i - MathUtils.floor(i), vals[MathUtils.floor(i)], vals[MathUtils.ceil(i)]);
    }

    public PVector getNorm(PVector vec) {
        return new PVector(vec.x / mMax.x, vec.y / mMax.y, vec.z / mMax.z);
    }

    public void setNorm(PVector vec) {
        vec.set(vec.x / mMax.x, vec.y / mMax.y, vec.z / mMax.z);
    }

    public void setRand(PVector vec) {
        vec.set(random(mMax.x), random(mMax.y), random(mMax.z));
    }

    public void setVec(PVector vec, LXVector v) {
        vec.set(v.x, v.y, v.z);
    }

    public void interpolate(float i, PVector a, PVector b) {
        a.set(interp(i, a.x, b.x), interp(i, a.y, b.y), interp(i, a.z, b.z));
    }

    public float val(CompoundParameter p) {
        return p.getValuef();
    }

    protected abstract void StartRun(double deltaMs);

    /**
     * CalcPoint will be invoked in parallel, so implementations of CalcPoint must be
     * thread-safe!  In particular, be careful in CalcPoint not to mutate PVectors
     * that are declared at the class or instance level.
     */
    protected abstract int CalcPoint(PVector p);

    public int blend3(int c1, int c2, int c3) {
        return PImage.blendColor(c1, PImage.blendColor(c2, c3, ADD), ADD);
    }

    public void rotateZ(PVector p, PVector o, float nSin, float nCos) {
        p.set(nCos * (p.x - o.x) - nSin * (p.y - o.y) + o.x, nSin * (p.x - o.x) + nCos * (p.y - o.y) + o.y, p.z);
    }

    public void rotateX(PVector p, PVector o, float nSin, float nCos) {
        p.set(p.x, nCos * (p.y - o.y) - nSin * (p.z - o.z) + o.y, nSin * (p.y - o.y) + nCos * (p.z - o.z) + o.z);
    }

    public void rotateY(PVector p, PVector o, float nSin, float nCos) {
        p.set(nSin * (p.z - o.z) + nCos * (p.x - o.x) + o.x, p.y, nCos * (p.z - o.z) - nSin * (p.x - o.x) + o.z);
    }

    public CompoundParameter addParam(String label, double value) {
        CompoundParameter p = new CompoundParameter(label, value);
        addParameter(p);
        return p;
    }

    public CompoundParameter addParam(String label, double value, double min, double max) {
        CompoundParameter p2 = new CompoundParameter(label, value, min, max);
        addParameter(p2);
        return p2;
    }

    public float calcCone(PVector v1, PVector v2, PVector c) {
        PVector r1 = PVector.sub(v1, c);
        PVector r2 = PVector.sub(v2, c);
        return MathUtils.degrees(PVector.angleBetween(r1, r2));
    }

    public float random(float max) {
        return (float)splittableRandom.nextDouble((double)max);
    }

    protected float random(float min, float max) {
        return (float)splittableRandom.nextDouble((double)min, (double)max) + min;
    }

    protected boolean btwn(int a, int b, int c) {
        return a >= b && a <= c;
    }

    protected boolean btwn(double a, double b, double c) {
        return a >= b && a <= c;
    }

    protected float interp(float a, float b, float c) {
        return (1 - a) * b + a * c;
    }

    protected float randctr(float a) {
        return (float) (splittableRandom.nextDouble((double) a) - a * 0.5f);
    }

    protected float min4(float a, float b, float c, float d) {
        return MathUtils.min(MathUtils.min(a, b), MathUtils.min(c, d));
    }

    protected float pointDist(LXPoint p1, LXPoint p2) {
        return MathUtils.dist(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
    }

    protected float xyDist(LXPoint p1, LXPoint p2) {
        return MathUtils.dist(p1.x, p1.y, p2.x, p2.y);
    }

    protected float distToSeg(float x, float y, float x1, float y1, float x2, float y2) {
        float A = x - x1, B = y - y1, C = x2 - x1, D = y2 - y1;
        float dot = A * C + B * D, len_sq = C * C + D * D;
        float xx, yy, param = dot / len_sq;

        if (param < 0 || (x1 == x2 && y1 == y2)) {
            xx = x1;
            yy = y1;
        } else if (param > 1) {
            xx = x2;
            yy = y2;
        } else {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }
        float dx = x - xx, dy = y - yy;
        return MathUtils.sqrt(dx * dx + dy * dy);
    }

    public void onReset() {}

    public DPat(LX lx) {
        super(lx);
        pSpark = addParam("Spark", 0);
        pWave = addParam("Wave", 0);
        pSpin = addParam("Spin", .5);
        pTransX = addParam("xPos", .5);
        pTransY = addParam("yPos", .5);
        pRotX = addParam("xRot", .5);
        pRotY = addParam("yRot", .5);
        pRotZ = addParam("zRot", .5);

        pXsym = new BooleanParameter("X-SYM");
        pYsym = new BooleanParameter("Y-SYM");
        pRsym = new BooleanParameter("R-SYM");
        pXdup = new BooleanParameter("X-DUP");
        pJog = new BooleanParameter("JOG");
        pGrey = new BooleanParameter("GREY");

        addParameter(pXsym);
        addParameter(pYsym);
        addParameter(pRsym);
        addParameter(pXdup);
        // addParameter(pJog);
        addParameter(pGrey);

        modmin = new PVector(model.xMin, model.yMin, model.zMin);
        mMax = new PVector(model.xMax, model.yMax, model.zMax);
        mMax.sub(modmin);
        mCtr = new PVector();
        mCtr.set(mMax);
        mCtr.mult(.5f);
        mHalf = new PVector(.5f, .5f, .5f);
        xWaveNz = new float[MathUtils.ceil(mMax.y) + 1];
        yWaveNz = new float[MathUtils.ceil(mMax.x) + 1];
    }

    public float spin() {
        float raw = val(pSpin);
        if (raw <= 0.45f) {
            return raw + 0.05f;
        } else if (raw >= 0.55f) {
            return raw - 0.05f;
        }
        return 0.5f;
    }

    @Override
    public void run(double deltaMs) {
        NoiseMove += deltaMs;
        NoiseMove = (float) (NoiseMove % 1e7);
        StartRun(deltaMs);

        pTrans.set(val(pTransX) * 200 - 100, val(pTransY) * 100 - 50, 0);

        if (pJog.getValueb()) {
            // TODO: find replacement for lx.tempo, re-add pJog
            // float tRamp = (lx.tempo.rampf() % .25f);
            // if (tRamp < LastJog) xyzJog.set(randctr(mMax.x * .2f), randctr(mMax.y * .2f), randctr(mMax.z * .2f));
            // LastJog = tRamp;
        }

        // precalculate this stuff
        final float wvAmp = val(pWave), sprk = val(pSpark);
        if (wvAmp > 0) {
            for (int i = 0; i < MathUtils.ceil(mMax.x) + 1; i++)
                yWaveNz[i] = wvAmp * (NoiseUtils.noise((float) (i / (mMax.x * .3f) - (2e3 + NoiseMove) / 1500f)) - .5f) * (mMax.y / 2f);

            for (int i = 0; i < MathUtils.ceil(mMax.y) + 1; i++)
                xWaveNz[i] = wvAmp * (NoiseUtils.noise((float) (i / (mMax.y * .3f) - (1e3 + NoiseMove) / 1500f)) - .5f) * (mMax.x / 2f);
        }

        /* clear all colors to transparent black */
        for (int i = 0; i < colors.length; i++) {
            colors[i] = 0x00000000;
        }

        for (LXPoint p : model.points) {
            PVector P = new PVector(), tP = new PVector();

            setVec(P, new LXVector(p));
            P.sub(modmin);
            P.sub(pTrans);
            if (sprk > 0) {
                P.y += sprk * randctr(50);
                P.x += sprk * randctr(50);
                P.z += sprk * randctr(50);
            }
            if (wvAmp > 0) P.y += interpWv(p.x - modmin.x, yWaveNz);
            if (wvAmp > 0) P.x += interpWv(p.y - modmin.y, xWaveNz);
            if (pJog.getValueb()) P.add(xyzJog);

            int cNew;
            {
                tP.set(P);
                cNew = CalcPoint(tP);
            }
            if (pXsym.getValueb()) {
                tP.set(mMax.x - P.x, P.y, P.z);
                cNew = ColorOps8.add(cNew, CalcPoint(tP));
            }
            if (pYsym.getValueb()) {
                tP.set(P.x, mMax.y - P.y, P.z);
                cNew = ColorOps8.add(cNew, CalcPoint(tP));
            }
            if (pRsym.getValueb()) {
                tP.set(mMax.x - P.x, mMax.y - P.y, mMax.z - P.z);
                cNew = ColorOps8.add(cNew, CalcPoint(tP));
            }
            if (pXdup.getValueb()) {
                tP.set((P.x + mMax.x * .5f) % mMax.x, P.y, P.z);
                cNew = ColorOps8.add(cNew, CalcPoint(tP));
            }
            if (pGrey.getValueb()) {
                cNew = ColorOps8.gray(ColorOps8.level(cNew));
            }
            colors[p.index] = cNew;
        }
    }

    public static class NDat {
        public float xz, yz, zz, hue, speed, angle, den;
        public float xoff, yoff, zoff;
        public float sinAngle;
        public float cosAngle;
        public boolean isActive;

        public NDat() {
            isActive = false;
        }

        public boolean Active() {
            return isActive;
        }

        public void set(float _hue, float _xz, float _yz, float _zz, float _den, float _speed, float _angle) {
            isActive = true;
            hue = _hue;
            xz = _xz;
            yz = _yz;
            zz = _zz;
            den = _den;
            speed = _speed;
            angle = _angle;
            xoff = MathUtils.random(100e3f);
            yoff = MathUtils.random(100e3f);
            zoff = MathUtils.random(100e3f);
        }
    }

    public static class DBool {
        boolean def, b;
        String tag;
        int row, col;

        void reset() {
            b = def;
        }

        boolean set(int r, int c, boolean val) {
            if (r != row || c != col) return false;
            b = val;
            return true;
        }

        boolean toggle(int r, int c) {
            if (r != row || c != col) return false;
            b = !b;
            return true;
        }

        DBool(String _tag, boolean _def, int _row, int _col) {
            def = _def;
            b = _def;
            tag = _tag;
            row = _row;
            col = _col;
        }
    }
}
