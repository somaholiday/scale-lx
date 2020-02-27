package com.studioannwn.output;

import com.studioannwn.model.ScaleFinalModel;
import com.studioannwn.model.ScaleModel;
import com.studioannwn.output.pixlite.PixLite;
import com.studioannwn.output.pixlite.PixLite16;

import heronarts.lx.LX;
import heronarts.lx.model.LXModel;

public class ScaleLayout {
	private final static String[] PIXLITE_IPS = new String[] {
		"192.168.2.18",
		"192.168.2.19",
	};

	private final ScaleModel model;
	private PixLite[] pixlites;

	public ScaleLayout() {
		model = new ScaleFinalModel();

		System.out.println(String.format(
				"Model stats: \n\tcx=%f \tcy=%f \tcz=%f\n\txAvg=%f \tyAvg=%f \tzAvg=%f\n\txRange=%f \tyRange=%f \tzRange=%f\n",
				model.cx, model.cy, model.cz, model.ax, model.ay, model.az, model.xRange, model.yRange, model.zRange));
	}

	public void addOutputs(LX lx) {
		pixlites = new PixLite[PIXLITE_IPS.length];
		for (int i=0; i < pixlites.length; i++) {
			pixlites[i] = new PixLite16(lx, PIXLITE_IPS[i]);
		}

		assignOutput(0, 1, 0);
		assignOutput(0, 2, 1);
		assignOutput(0, 3, 2);
		assignOutput(0, 4, 3);
		assignOutput(0, 5, 4);
		assignOutput(0, 6, 5);
		assignOutput(0, 7, 6);
		assignOutput(0, 8, 7);
		assignOutput(0, 9, 8);
		assignOutput(0, 10, 9);
		assignOutput(0, 11, 10);
		assignOutput(0, 12, 11);
		assignOutput(0, 13, 12);
		assignOutput(0, 14, 13);
		assignOutput(0, 15, 14);
		assignOutput(0, 16, 15);
		assignOutput(1, 1, 16);
		assignOutput(1, 2, 17);
		assignOutput(1, 3, 18);
		assignOutput(1, 4, 19);
		assignOutput(1, 5, 20);
		assignOutput(1, 6, 21);
		assignOutput(1, 7, 22);
		assignOutput(1, 8, 23);
		assignOutput(1, 9, 24);
		assignOutput(1, 10, 25);
		assignOutput(1, 11, 26);
		assignOutput(1, 12, 27);
		assignOutput(1, 13, 28);
		assignOutput(1, 14, 29);
		assignOutput(1, 15, 30);

		int maxFixtures = model.children.length;
		System.out.println(String.format("Model has %d fixtures.", maxFixtures));

		// int fixtureCount = 0;
		for (int pixliteIndex = 0; pixliteIndex < pixlites.length; pixliteIndex++) {
			PixLite pixlite = pixlites[pixliteIndex];
			// for (int outputIndex = 1; outputIndex <= pixlite.numOutputs && fixtureCount < maxFixtures; outputIndex++) {
			// 	assignOutput(pixliteIndex, outputIndex, fixtureCount++);
			// }

			lx.addOutput(pixlite);
		}
	}

	private void assignOutput(int pixliteIndex, int outputIndex, int fixtureIndex) {
		pixlites[pixliteIndex].addOutput(outputIndex, model.children[fixtureIndex].getPoints());
	}

	public ScaleModel getModel() {
		return model;
	}
}
