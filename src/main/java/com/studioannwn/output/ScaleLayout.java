package com.studioannwn.output;

import java.util.ArrayList;
import java.util.List;

import com.studioannwn.model.ModelCollection;
import com.studioannwn.model.ScaleFinalModel;
import com.studioannwn.model.ScaleModel;
import com.studioannwn.output.pixlite.PixLite;
import com.studioannwn.output.pixlite.PixLite16;
import com.studioannwn.output.pixlite.PixLite4;

import heronarts.lx.LX;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.studio.LXStudio;

public class ScaleLayout {
	private final static String[] PIXLITE_IPS = new String[] { "192.168.2.18", "192.168.2.19", };

	private final ScaleModel model;

	public ScaleLayout() {
		model = new ScaleFinalModel();

		System.out.println(String.format(
				"Model stats: \n\tcx=%f \tcy=%f \tcz=%f\n\txAvg=%f \tyAvg=%f \tzAvg=%f\n\txRange=%f \tyRange=%f \tzRange=%f\n",
				model.cx, model.cy, model.cz, model.ax, model.ay, model.az, model.xRange, model.yRange, model.zRange));
	}

	/*
	 * TODO: I left this in a bit of an unfinished mess.
	 */
	public void addOutputs(LX lx) {
		PixLite pixlite = new PixLite4(lx, PIXLITE_IPS[0]);

		int maxFixtures = model.children.length;
		System.out.println(String.format("Model has %d fixtures.", maxFixtures));
		int fixtureCount = 0;
		for (int outputIndex = 1; outputIndex <= pixlite.numOutputs && fixtureCount < maxFixtures; outputIndex++) {
			LXModel fixture = model.children[fixtureCount++];
			pixlite.addOutput(outputIndex, fixture.getPoints());
		}

		lx.addOutput(pixlite);
	}

	public ScaleModel getModel() {
		return model;
	}
}
