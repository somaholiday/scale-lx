package com.studioannwn.model;

import java.util.List;
import java.util.ArrayList;

import com.google.common.collect.ImmutableList;
import com.studioannwn.Scale;

import heronarts.lx.model.LXModel;
import heronarts.lx.transform.LXTransform;

public class ScaleModel extends LXModel {
	public static final String MODEL_KEY = "scale";

	public ScaleModel() {
		this(new LXTransform());
	}

	public ScaleModel(LXTransform t) {
		this(t, ImmutableList.of());
	}

	public ScaleModel(LXTransform t, List<String> extraKeys) {
		super(buildSubmodels(t));

		String[] keys = new String[extraKeys.size() + 1];
		keys[0] = MODEL_KEY;

		for (int i = 0; i < extraKeys.size(); ++i) {
			keys[i + 1] = extraKeys.get(i);
		}

		setKeys(keys);
	}

	private static LXModel[] buildSubmodels(LXTransform t) {
		List<LXModel> submodels = new ArrayList<>();

		t.push();
			submodels.add(new FixtureModel(10, t, ImmutableList.of("output-1")));

			t.push();
				t.translate(5 * FixtureModel.PITCH, - 8 * Scale.CM, 0);
				submodels.add(new FixtureModel(10, t, ImmutableList.of("output-3")));
			t.pop();
		t.pop();

		return submodels.toArray(new LXModel[0]);
	}
}
