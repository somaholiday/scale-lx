package com.studioannwn;

import com.studioannwn.model.ScaleModel;

public class ScaleLayout {

	private final ScaleModel model;

	public ScaleLayout() {
		model = new ScaleModel();

		// model.addDatagram(d);

		System.out.println(String.format("Model stats: \n\tcx=%f cy=%f cz=%f\n\txAvg=%f yAvg=%f zAvg=%f\n\txRange=%f yRange=%f zRange=%f\n",
				model.cx, model.cy, model.cz, model.ax, model.ay, model.az,
				model.xRange, model.yRange, model.zRange));
	}

	public ScaleModel getModel() {
		return model;
	}
}
