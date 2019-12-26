package com.studioannwn.model;

import java.util.List;
import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.transform.LXTransform;

public class ToriiModel extends LXModel {
	public static final String MODEL_KEY = "torii";
	public static final String NINE_OCLOCK_KEY = "nine-oclock";
	public static final String THREE_OCLOCK_KEY = "three-oclock";

	private static final float WALL_WASHER_GAP = 3.f / 12;

	public static enum ToriiType {
		T1, T2, T3, T4, T5, T6;

		private static ToriiType[] values = values();

		public static ToriiType fromIndex(int i) {
			return values[i];
		}

		public int toIndex() {
			return ordinal();
		}
	}

	private static final double[] OPENING_WIDTH_FEET = new double[] {
			13 + 0.5 / 12., 17 + 4.5 / 12., 21 + 8.5 / 12., 26 + 0.5 / 12., 30 + 4.5 / 12., 34 + 8.5 / 12.
	};
	private static final double[] COLUMN_WIDTH_FEET = new double[] {
			3 + 1.5 / 12., 3 + 1.5 / 12., 3 + 1.5 / 12., 3 + 1.5 / 12., 3 + 1.5 / 12., 3 + 1.5 / 12.
	};
	private static final double[] COLUMN_HEIGHT_FEET = new double[] {
			14 + 11.24 / 12., 19 + 11.24 / 12., 24 + 11.24 / 12., 29 + 11.24 / 12., 34 + 11.24 / 12., 39 + 11.24 / 12.
	};
	private static final double[] COLUMN_DEPTH_FEET = new double[] {
			8 + 1.5 / 12., 8 + 1.5 / 12., 8 + 1.5 / 12., 8 + 1.5 / 12., 8 + 1.5 / 12., 8 + 1.5 / 12.
	};
	private static final double[] BEAM_HEIGHT_FEET = new double[] {
			3 + 1.5 / 12., 3 + 1.5 / 12., 3 + 1.5 / 12., 3 + 1.5 / 12., 3 + 1.5 / 12., 3 + 1.5 / 12.
	};
	private static final double[] BEAM_LENGTH_FEET = new double[] {
			26 + 11.5 / 12., 34 + 2.75 / 12., 41 + 8.75 / 12., 48 + 1.5 / 12., 55 + 11.25 / 12., 60 + 2.5 / 12.
	};
	private static final double[] LIGHT_HEIGHT_FEET = new double[] {
		0, 6., 10 + 6 / 12., 15., 19 + 6 / 12., 24.
	};

	public final ToriiType toriiType;
	public final int direction;
	public final float originX, originY, originZ;
	public final float openingWidth;
	public final float columnWidth, columnHeight, columnDepth;
	public final float beamHeight, beamLength;
	public final float eaveDepth; // calculated
	public final float lightHeight;

	private List<LXPoint> nineOclockPoints = new ArrayList<>();
	private List<LXPoint> threeOclockPoints = new ArrayList<>();

	public ToriiModel(ToriiType toriiType, int direction) {
		this(toriiType, direction, new LXTransform());
	}

	public ToriiModel(ToriiType toriiType, int direction, LXTransform t) {
		this(toriiType, direction, t, ImmutableList.of());
	}

	public ToriiModel(ToriiType toriiType, int direction, LXTransform t, List<String> extraKeys) {
		this(toriiType, direction, t, extraKeys,
				(float)OPENING_WIDTH_FEET[toriiType.toIndex()],
				(float)COLUMN_WIDTH_FEET[toriiType.toIndex()],
				(float)COLUMN_HEIGHT_FEET[toriiType.toIndex()],
				(float)COLUMN_DEPTH_FEET[toriiType.toIndex()],
				(float)BEAM_HEIGHT_FEET[toriiType.toIndex()],
				(float)BEAM_LENGTH_FEET[toriiType.toIndex()],
				(float)LIGHT_HEIGHT_FEET[toriiType.toIndex()]);
	}

	protected ToriiModel(ToriiType toriiType, int direction, LXTransform t, List<String> extraKeys,
			float openingWidth, float columnWidth, float columnHeight, float columnDepth,
			float beamHeight, float beamLength, float lightHeight) {

		super(buildSubmodels(toriiType, direction, openingWidth, columnWidth,
				columnHeight, columnDepth, beamHeight, beamLength, lightHeight, t));

		this.direction = direction;
		this.originX = t.x();
		this.originY = t.y();
		this.originZ = t.z();

		this.toriiType = toriiType;
		this.openingWidth = openingWidth;
		this.columnWidth = columnWidth;
		this.columnHeight = columnHeight;
		this.columnDepth = columnDepth;
		this.beamHeight = beamHeight;
		this.beamLength = beamLength;
		this.eaveDepth = (beamLength - 2 * columnWidth - openingWidth) / 2;
		this.lightHeight = lightHeight;

		String[] keys = new String[extraKeys.size() + 2];
		keys[0] = MODEL_KEY;
		keys[1] = "torii-type-" + (toriiType.toIndex() + 1);

		for (int i = 0; i < extraKeys.size(); ++i) {
			keys[i + 2] = extraKeys.get(i);
		}

		setKeys(keys);

		for (LXModel model : sub(NINE_OCLOCK_KEY)) {
			nineOclockPoints.addAll(model.getPoints());
		}
		for (LXModel model : sub(THREE_OCLOCK_KEY)) {
			threeOclockPoints.addAll(model.getPoints());
		}
	}

	private static LXModel[] buildSubmodels(ToriiType toriiType, int direction,
			float openingWidth, float columnWidth, float columnHeight, float columnDepth,
			float beamHeight, float beamLength, float lightHeight, LXTransform t) {

		if (direction != -1 && direction != 1) {
			throw new RuntimeException("Torii direction must be either -1 or 1.");
		}

		List<LXModel> submodels = new ArrayList<>();

		t.push();
		t.scaleX(direction);
		t.translate(columnDepth / 2, 0, 0);

		float zOffset = 0;
		switch (toriiType.toIndex()) {
			case 0: // T1
				// move 2 meters out from column base
				zOffset = 6 + 6.75f / 12.f + openingWidth / 2 + columnWidth;

				t.push();
				t.translate(0, 0, -zOffset);
				submodels.add(new SpotlightModel(t, ImmutableList.of(THREE_OCLOCK_KEY)));
				t.pop();
				t.push();
				t.translate(0, 0, zOffset);
				submodels.add(new SpotlightModel(t, ImmutableList.of(NINE_OCLOCK_KEY)));
				t.pop();

				break;
			case 1: // T2
				// move half a foot from eave end
				zOffset = (openingWidth / 2) + columnWidth;

				t.push();
				t.translate(0, lightHeight, -zOffset);
				submodels.add(new WallWasherModel(t, ImmutableList.of(THREE_OCLOCK_KEY)));
				t.pop();
				t.push();
				t.translate(0, lightHeight, zOffset);
				submodels.add(new WallWasherModel(t, ImmutableList.of(NINE_OCLOCK_KEY)));
				t.pop();

				break;
			default: // T3-T6
				// move half a foot from eave end
				zOffset = (openingWidth / 2) + columnWidth;

				t.push();
				t.translate(-WALL_WASHER_GAP - WallWasherModel.WIDTH_FEET, lightHeight, -zOffset);
				submodels.add(new WallWasherModel(t, ImmutableList.of(THREE_OCLOCK_KEY)));
				t.pop();
				t.push();
				t.translate(-WALL_WASHER_GAP - WallWasherModel.WIDTH_FEET, lightHeight, zOffset);
				submodels.add(new WallWasherModel(t, ImmutableList.of(NINE_OCLOCK_KEY)));
				t.pop();

				t.push();
				t.translate(WALL_WASHER_GAP, lightHeight, -zOffset);
				submodels.add(new WallWasherModel(t, ImmutableList.of(THREE_OCLOCK_KEY)));
				t.pop();
				t.push();
				t.translate(WALL_WASHER_GAP, lightHeight, zOffset);
				submodels.add(new WallWasherModel(t, ImmutableList.of(NINE_OCLOCK_KEY)));
				t.pop();
		}

		t.pop();

		return submodels.toArray(new LXModel[0]);
	}

	public List<LXPoint> getNineOclockPoints() {
		return nineOclockPoints;
	}

	public List<LXPoint> getThreeOclockPoints() {
		return threeOclockPoints;
	}
}
