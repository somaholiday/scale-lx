package com.studioannwn.model;

public class ScaleTestModel extends ScaleModel {
	private static final FixtureDef[] fixtureDefs = {
			// z range 3-12
			// 0 = ceiling

			new FixtureDef(0, 0, 12, 10),
			new FixtureDef(8, 8, 16, 10),
	};

	public ScaleTestModel() {
		super(fixtureDefs);
	}
}
