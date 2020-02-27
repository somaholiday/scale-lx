package com.studioannwn.output;

import com.studioannwn.model.ScaleFinalModel;
import com.studioannwn.model.ScaleModel;
import com.studioannwn.output.pixlite.PixLite;
import com.studioannwn.output.pixlite.PixLite16;

import heronarts.lx.LX;
import heronarts.lx.model.LXModel;

public class ScaleLayout {
	private final static String[] PIXLITE_IPS = new String[] { "192.168.2.18", "192.168.2.19", };

	private final ScaleModel model;

	public ScaleLayout() {
		model = new ScaleFinalModel();

		System.out.println(String.format(
				"Model stats: \n\tcx=%f \tcy=%f \tcz=%f\n\txAvg=%f \tyAvg=%f \tzAvg=%f\n\txRange=%f \tyRange=%f \tzRange=%f\n",
				model.cx, model.cy, model.cz, model.ax, model.ay, model.az, model.xRange, model.yRange, model.zRange));
	}

	public void addOutputs(LX lx) {
		PixLite[] pixlites = new PixLite[PIXLITE_IPS.length];
		for (int i=0; i < pixlites.length; i++) {
			pixlites[i] = new PixLite16(lx, PIXLITE_IPS[i]);
		}

		int maxFixtures = model.children.length;
		System.out.println(String.format("Model has %d fixtures.", maxFixtures));

		int fixtureCount = 0;
		for (PixLite pixlite : pixlites) {
			for (int outputIndex = 1; outputIndex <= pixlite.numOutputs && fixtureCount < maxFixtures; outputIndex++) {
				LXModel fixture = model.children[fixtureCount++];
				pixlite.addOutput(outputIndex, fixture.getPoints());
			}

			lx.addOutput(pixlite);
		}
	}

	public ScaleModel getModel() {
		return model;
	}
}
