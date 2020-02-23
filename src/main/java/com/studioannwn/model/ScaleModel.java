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
		// z range 3-12
		// 0 = ceiling
		{ // OUTPUT 1
			new FixtureDef(0, 0, 12, 10),
			// new FixtureDef(7, 0, 12, SIZE_S),
			// new FixtureDef(59, 0, 36, SIZE_S),
			// new FixtureDef(65, 0, 47, SIZE_S),
			// new FixtureDef(108, 0, 39, SIZE_S),
			// new FixtureDef(169, 0, 60, SIZE_S),
			// new FixtureDef(184, 0, 40.5f, SIZE_S),
			// new FixtureDef(181, 0, 25, SIZE_S),
		},
		{ // OUTPUT 2
			new FixtureDef(10, 0, 46, SIZE_M),
			new FixtureDef(12, 0, 56, SIZE_M),
			new FixtureDef(12, 0, 33, SIZE_M),
			new FixtureDef(25, 0, 63, SIZE_M),
			new FixtureDef(23, 0, 52, SIZE_M),
			new FixtureDef(24, 0, 17, SIZE_M),
			// new FixtureDef(47, 0, 8, SIZE_M),
			// new FixtureDef(75, 0, 23, SIZE_M),
			// new FixtureDef(81, 0, 53, SIZE_M),
			// new FixtureDef(87, 0, 15, SIZE_M),
			// new FixtureDef(108, 0, 9.5f, SIZE_M),
			// new FixtureDef(109, 0, 29, SIZE_M),
			// new FixtureDef(107, 0, 64, SIZE_M),
			// new FixtureDef(128, 0, 38, SIZE_M),
			// new FixtureDef(140, 0, 54, SIZE_M),
			// new FixtureDef(142, 0, 32, SIZE_M),
			// new FixtureDef(142, 0, 20, SIZE_M),
			// new FixtureDef(146, 0, 6, SIZE_M),
		},
		{ // OUTPUT 3
			new FixtureDef(8, 8, 16, 10),
			// new FixtureDef(5, 0, 42, SIZE_L),
			// new FixtureDef(20, 0, 27, SIZE_L),
			// new FixtureDef(76, 0, 58.5f, SIZE_L),
			// new FixtureDef(82, 0, 45, SIZE_L),
			// new FixtureDef(133, 0, 13, SIZE_L),
			// new FixtureDef(138, 0, 50, SIZE_L),
		},
		{ // OUTPUT 4
			// new FixtureDef(0, 0, 120, SIZE_S),
			// new FixtureDef(0, 4, 120, SIZE_S),
			// new FixtureDef(0, 8, 120, SIZE_S),
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
		int fixtureCount = 0;
		for (int i = 0; i < fixtureDefs.length; i++) {
			for (FixtureDef def : fixtureDefs[i]) {
				t.push();
				pos = def.getPosition();
				t.translate(pos.x, pos.y, pos.z);
				submodels.add(new FixtureModel(def.getLEDCount(), t, ImmutableList.of("fixture-" + fixtureCount, "output-" + (i+1))));
				t.pop();
				fixtureCount++;
			}
		}

		return submodels.toArray(new LXModel[0]);
	}
}
