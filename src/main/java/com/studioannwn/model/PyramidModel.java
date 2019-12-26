package com.studioannwn.model;

import java.util.List;
import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

import heronarts.lx.model.LXModel;
import heronarts.lx.transform.LXTransform;

public class PyramidModel extends LXModel {
	public static final String MODEL_KEY = "pyramid";
	public static final float TRIANGLE_SIDE_FEET = 20;
	public static final float TRIANGLE_SIDE_HALF = (float)(TRIANGLE_SIDE_FEET/2.);
	public static final float TRIANGLE_HEIGHT_FEET = (float)(TRIANGLE_SIDE_FEET * 0.5 * Math.sqrt(3));
	private static float WASHER_OFFSET = (float)((TRIANGLE_SIDE_FEET/2.-WallWasherModel.WIDTH_FEET)/2.);

	public PyramidModel() {
		this(new LXTransform());
	}

	public PyramidModel(LXTransform t) {
		this(t, ImmutableList.of());
	}

	public PyramidModel(LXTransform t, List<String> extraKeys) {
		super(buildSubmodels(t));

		String[] keys = new String[extraKeys.size() + 1];
		keys[0] = MODEL_KEY;

		for (int i = 0; i < extraKeys.size(); ++i) {
			keys[i + 1] = extraKeys.get(i);
		}

		setKeys(keys);
	}

	public ModelCollection<ToriiModel> getToriis() {
		return ModelCollection.filterChildren(this, ToriiModel.MODEL_KEY, ToriiModel.class);
	}

	private static void buildSide(List<LXModel> submodels, LXTransform t) {
		t.push();
		t.translate(WASHER_OFFSET, 0, 0);
		submodels.add(new WallWasherModel(t));
		t.pop();

		t.push();
		t.translate((float)(TRIANGLE_SIDE_FEET/2. + WASHER_OFFSET), 0, 0);
		submodels.add(new WallWasherModel(t));
		t.pop();
	}

	private static LXModel[] buildSubmodels(LXTransform t) {
		List<LXModel> submodels = new ArrayList<>();

		t.push();
			submodels.add(new SpotlightModel(t));

			t.push();
				t.translate(0, 0, -TRIANGLE_HEIGHT_FEET);
				submodels.add(new SpotlightModel(t));
			t.pop();

			t.push();
				t.rotateY(Math.PI/3.);
				buildSide(submodels, t);
			t.pop();

			t.push();
				t.rotateY(-Math.PI/3.);
				t.translate(-TRIANGLE_SIDE_FEET, 0, 0);
				buildSide(submodels, t);
			t.pop();

			t.push();
				// this Y may not be correct
				t.translate(0, (float)((TRIANGLE_SIDE_FEET/2.)/Math.sqrt(3)), (float)(-(TRIANGLE_SIDE_FEET/2.)/Math.sqrt(3)));

				t.push();
					t.rotateY(-2*Math.PI/3.);
					t.translate(-TRIANGLE_SIDE_HALF, 0, 0);
					t.translate(WASHER_OFFSET, 0, 0);
					submodels.add(new WallWasherModel(t));
				t.pop();

				t.push();
					t.rotateY(2*Math.PI/3.);
					t.translate(WASHER_OFFSET, 0, 0);
					submodels.add(new WallWasherModel(t));
				t.pop();

				t.push();
					t.rotateY(Math.PI/3.);
					t.translate(TRIANGLE_SIDE_HALF, 0, 0);
					t.rotateY(2*Math.PI/3.);
					t.translate(WASHER_OFFSET, -1, -1);
					submodels.add(new WallWasherModel(t, ImmutableList.of("front")));
				t.pop();
			t.pop();

		t.pop();

		return submodels.toArray(new LXModel[0]);
	}
}
