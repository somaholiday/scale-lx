package com.studioannwn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.studioannwn.model.ModelCollection;
import com.studioannwn.model.ScaleModel;
import com.studioannwn.output.pixlite.PixLite;
import com.studioannwn.output.pixlite.PixLite4;

import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.output.LXDatagram;
import heronarts.lx.studio.LXStudio;

public class ScaleLayout {
  private final static String[] PIXLITE_IPS = new String[] {
		"192.168.2.18"
	};

	private final ScaleModel model;

	private List<LXDatagram> mutableDatagrams = new ArrayList<LXDatagram>();
	public final List<LXDatagram> datagrams =  Collections.unmodifiableList(this.mutableDatagrams);

	public ScaleLayout(LXStudio lx) {
		model = new ScaleModel();

		System.out.println(String.format("Model stats: \n\tcx=%f \tcy=%f \tcz=%f\n\txAvg=%f \tyAvg=%f \tzAvg=%f\n\txRange=%f \tyRange=%f \tzRange=%f\n",
				model.cx, model.cy, model.cz, model.ax, model.ay, model.az,
				model.xRange, model.yRange, model.zRange));

		PixLite pixlite = new PixLite4(lx, PIXLITE_IPS[0]);

		for (int outputIndex = 1; outputIndex <= pixlite.numOutputs; outputIndex++) {
			List<LXModel> models = ModelCollection.filterChildren(model, "output-" + outputIndex);

			List<LXPoint> points = new ArrayList<LXPoint>();
			for (LXModel m : models) {
				points.addAll(m.getPoints());
			}

			pixlite.addOutput(outputIndex, points);
		}

		lx.addOutput(pixlite);
	}

	public ScaleModel getModel() {
		return model;
	}
}
