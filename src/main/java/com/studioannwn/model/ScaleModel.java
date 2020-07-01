package com.studioannwn.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import heronarts.lx.model.LXModel;
import heronarts.lx.transform.LXTransform;
import heronarts.lx.transform.LXVector;

public abstract class ScaleModel extends StripsModel {

	public static final String MODEL_KEY = "scale";

	public ScaleModel(FixtureDef[] fixtureDefs) {
		this(new LXTransform(), ImmutableList.of(), fixtureDefs);
	}

	public ScaleModel(LXTransform t, List<String> extraKeys, FixtureDef[] fixtureDefs) {
		super(buildSubmodels(t, fixtureDefs));

		String[] keys = new String[extraKeys.size() + 1];
		keys[0] = MODEL_KEY;

		for (int i = 0; i < extraKeys.size(); ++i) {
			keys[i + 1] = extraKeys.get(i);
		}

		// setKeys(keys);
	}

	private static LXModel[] buildSubmodels(LXTransform t, FixtureDef[] fixtureDefs) {
		List<LXModel> submodels = new ArrayList<>();

		LXVector pos;
		int fixtureCount = 0;
		for (FixtureDef def : fixtureDefs) {
			t.push();
			pos = def.getPosition();
			t.translate(pos.x, pos.y, pos.z);
			submodels.add(new FixtureModel(def.getLEDCount(), t, ImmutableList.of("fixture-" + fixtureCount)));
			t.pop();
			fixtureCount++;
		}

		return submodels.toArray(new LXModel[0]);
	}

  public List<? extends LXModel> getStrips() {
	  return Arrays.asList(children);
  }
}
