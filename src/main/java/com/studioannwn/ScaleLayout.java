package com.studioannwn;

import java.util.ArrayList;
import java.util.List;

import com.google.common.primitives.Ints;
import com.studioannwn.model.ModelCollection;
import com.studioannwn.model.ScaleModel;

import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.output.ArtNetDatagram;
import heronarts.lx.output.LXDatagram;

public class ScaleLayout {
	private final static String PIXLITE_IP = "192.168.1.18";

	private final ScaleModel model;

	public ScaleLayout() {
		model = new ScaleModel();


		for (int universe = 1; universe <= 4; universe++) {
			List<LXModel> models = ModelCollection.filterChildren(model, "universe" + universe);

			List<Integer> indexBuffer = new ArrayList<Integer>();
			for (LXModel m : models) {
				for (LXPoint p : m.getPoints()) {
					indexBuffer.add(p.index);
				}
			}

			System.out.println(String.format("Universe %d", universe));
			for (Integer index : indexBuffer) {
				System.out.print(index + "\t");
			}
			System.out.println("\n");

			LXDatagram d = new ArtNetDatagram(Ints.toArray(indexBuffer), universe-1);

			try {
				d.setAddress(PIXLITE_IP);
			}	catch (Exception e) {
				System.err.println("Error when setting DMX IP address: " + e.getMessage());
				e.printStackTrace();
			}
			model.addDatagram(d);
		}

		System.out.println(String.format("Model stats: \n\tcx=%f \tcy=%f \tcz=%f\n\txAvg=%f \tyAvg=%f \tzAvg=%f\n\txRange=%f \tyRange=%f \tzRange=%f\n",
				model.cx, model.cy, model.cz, model.ax, model.ay, model.az,
				model.xRange, model.yRange, model.zRange));
	}

	public ScaleModel getModel() {
		return model;
	}
}
