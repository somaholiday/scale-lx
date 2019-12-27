package com.studioannwn;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.primitives.Ints;
import com.studioannwn.model.ModelCollection;
import com.studioannwn.model.ScaleModel;

import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.output.ArtNetDatagram;
import heronarts.lx.output.LXDatagram;
import heronarts.lx.output.LXDatagramOutput;
import heronarts.lx.studio.LXStudio;

public class ScaleLayout {
  private final static String PIXLITE_IP = "192.168.1.18";
	private final static String NETWORK_INTERFACE_IP = "192.168.1.102";

	private final ScaleModel model;

	private List<LXDatagram> mutableDatagrams = new ArrayList<LXDatagram>();
	public final List<LXDatagram> datagrams =  Collections.unmodifiableList(this.mutableDatagrams);

	public ScaleLayout() {
		model = new ScaleModel();

		for (int output = 1; output <= 4; output++) {
			List<LXModel> models = ModelCollection.filterChildren(model, "output-" + output);

			List<Integer> indexBuffer = new ArrayList<Integer>();
			for (LXModel m : models) {
				for (LXPoint p : m.getPoints()) {
					indexBuffer.add(p.index);
				}
			}

			System.out.println(String.format("Output %d", output));
			for (Integer index : indexBuffer) {
				System.out.print(index + "\t");
			}
			System.out.println("\n");

			// TODO: use model keys to refer to output number and map from output to universe in software instead of on PixLite
			LXDatagram d = new ArtNetDatagram(Ints.toArray(indexBuffer), output-1);

			try {
				d.setAddress(PIXLITE_IP);
			}	catch (Exception e) {
				System.err.println("Error when setting datagram IP address: " + e.getMessage());
				e.printStackTrace();
			}

			mutableDatagrams.add(d);
		}

		System.out.println(String.format("Model stats: \n\tcx=%f \tcy=%f \tcz=%f\n\txAvg=%f \tyAvg=%f \tzAvg=%f\n\txRange=%f \tyRange=%f \tzRange=%f\n",
				model.cx, model.cy, model.cz, model.ax, model.ay, model.az,
				model.xRange, model.yRange, model.zRange));
	}

	public ScaleModel getModel() {
		return model;
	}

	public void setupOutput(LXStudio lx) {
		DatagramSocket socket;

    try {
      socket = new DatagramSocket(new InetSocketAddress(NETWORK_INTERFACE_IP, 0));
      LXDatagramOutput datagramOutput = new LXDatagramOutput(lx, socket);
			for (LXDatagram datagram : this.datagrams) {
        datagramOutput.addDatagram(datagram);
			}

			lx.engine.output.addChild(datagramOutput);
    } catch (SocketException e) {
      e.printStackTrace();
    }
	}
}
