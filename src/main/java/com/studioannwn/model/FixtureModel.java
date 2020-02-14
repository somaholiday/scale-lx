package com.studioannwn.model;

import java.util.List;
import java.util.ArrayList;

import com.google.common.collect.ImmutableList;
import com.studioannwn.Scale;

import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.transform.LXTransform;

public class FixtureModel extends LXModel {
	public static final String MODEL_KEY = "fixture";

	public static final float PITCH = Scale.METER / 80.0f;

	public FixtureModel(int ledCount) {
		this(ledCount, new LXTransform());
	}

	public FixtureModel(int ledCount, LXTransform t) {
		this(ledCount, t, new LXTransform().translate(0, 0, 1), ImmutableList.of());
	}

	public FixtureModel(int ledCount, LXTransform t, List<String> extraKeys) {
		this(ledCount, t, new LXTransform().translate(0, 0, 1), extraKeys);
	}

	public FixtureModel(int ledCount, LXTransform t, LXTransform r) {
		this(ledCount, t, r, ImmutableList.of());
	}

	public FixtureModel(int ledCount, LXTransform t, LXTransform r, List<String> extraKeys) {
		super(buildPoints(ledCount, t, r));

		String[] keys = new String[extraKeys.size() + 1];
		keys[0] = MODEL_KEY;

		for (int i = 0; i < extraKeys.size(); ++i) {
			keys[i + 1] = extraKeys.get(i);
		}

		setKeys(keys);
	}

	private static List<LXPoint> buildPoints(int ledCount, LXTransform t, LXTransform r) {
		List<LXPoint> points = new ArrayList<>();

		t.push();

		for (int i = 0; i < ledCount; i++) {
			t.translate(PITCH, 0, 0);

			points.add(new LXPoint(
					t.x(), t.y(), t.z()
			));
		}

		t.pop();

		return points;
	}
}
