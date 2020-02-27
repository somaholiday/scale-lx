package com.studioannwn.model;

import static com.studioannwn.model.FixtureDef.*;

public class ScaleFinalModel extends ScaleModel {
	private static final FixtureDef[] fixtureDefs = {
			// z range 3-12
			// 0 = ceiling
	    new FixtureDef(7, 0, 12, SIZE_S),
			new FixtureDef(59, 0, 36, SIZE_S),
			new FixtureDef(65, 0, 47, SIZE_S),
			new FixtureDef(108, 0, 39, SIZE_S),
			new FixtureDef(169, 0, 60, SIZE_S),
			new FixtureDef(181, 0, 25, SIZE_S),
			new FixtureDef(184, 0, 40.5f, SIZE_S),
			new FixtureDef(10, 0, 46, SIZE_M),
			new FixtureDef(12, 0, 33, SIZE_M),
			new FixtureDef(12, 0, 56, SIZE_M),
			new FixtureDef(23, 0, 52, SIZE_M),
			new FixtureDef(24, 0, 17, SIZE_M),
			new FixtureDef(25, 0, 63, SIZE_M),
			new FixtureDef(47, 0, 8, SIZE_M),
			new FixtureDef(75, 0, 23, SIZE_M),
			new FixtureDef(81, 0, 53, SIZE_M),
			new FixtureDef(87, 0, 15, SIZE_M),
			new FixtureDef(107, 0, 64, SIZE_M),
			new FixtureDef(108, 0, 9.5f, SIZE_M),
			new FixtureDef(109, 0, 29, SIZE_M),
			new FixtureDef(128, 0, 38, SIZE_M),
			new FixtureDef(140, 0, 54, SIZE_M),
			new FixtureDef(142, 0, 20, SIZE_M),
			new FixtureDef(142, 0, 32, SIZE_M),
			new FixtureDef(146, 0, 6, SIZE_M),
			new FixtureDef(5, 0, 42, SIZE_L),
			new FixtureDef(20, 0, 27, SIZE_L),
			new FixtureDef(76, 0, 58.5f, SIZE_L),
			new FixtureDef(82, 0, 45, SIZE_L),
			new FixtureDef(133, 0, 13, SIZE_L),
			new FixtureDef(138, 0, 50, SIZE_L),
	};

	public ScaleFinalModel() {
		super(fixtureDefs);
	}
}
