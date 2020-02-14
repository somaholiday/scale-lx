package com.studioannwn.model;

import java.util.List;
import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

import heronarts.lx.model.LXModel;
import heronarts.lx.transform.LXTransform;
import heronarts.lx.transform.LXVector;

import static com.studioannwn.model.FixtureDef.*;

public class ScaleModel extends LXModel {
	private static final FixtureDef[][] fixtureDefs = {
		{ // OUTPUT 1
			new FixtureDef(0, 0, 20, SIZE_S),
			new FixtureDef(0, 4, 20, SIZE_M),
			new FixtureDef(0, 8, 20, SIZE_L),
		},
		{ // OUTPUT 2
			new FixtureDef(0, 0, 40, SIZE_S),
			new FixtureDef(0, 8, 40, SIZE_S),
			new FixtureDef(0, 16, 40, SIZE_S),
		},
		{ // OUTPUT 3
			new FixtureDef(0, 0, 80, SIZE_M),
			new FixtureDef(0, 8, 80, SIZE_M),
			new FixtureDef(0, 16, 80, SIZE_M),
		},
		{ // OUTPUT 4
			new FixtureDef(0, 0, 120, SIZE_L),
			new FixtureDef(0, 4, 120, SIZE_L),
			new FixtureDef(0, 8, 120, SIZE_L),
		},
	};

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

		LXVector pos;
		for (int i = 0; i < fixtureDefs.length; i++) {
			for (FixtureDef def : fixtureDefs[i]) {
				t.push();
				pos = def.getPosition();
				t.translate(pos.x, pos.y, pos.z);
				submodels.add(new FixtureModel(def.getLEDCount(), t, ImmutableList.of("output-" + (i+1))));
				t.pop();
			}
		}

		return submodels.toArray(new LXModel[0]);
	}
}
