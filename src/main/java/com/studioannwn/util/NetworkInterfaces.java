package com.studioannwn.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class NetworkInterfaces {
  public static List<InetAddress> getNetworkInterfaceInetAddresses() {
    List<InetAddress> ret = new ArrayList<InetAddress>();
    try {
      Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
      for (NetworkInterface netint : Collections.list(nets)) {
        if (!netint.isUp()
          || netint.isLoopback()
          || netint.isPointToPoint()
          || netint.isVirtual()) {
            continue;
          }

        List<InetAddress> inetAddresses = Collections.list(netint.getInetAddresses());
        inetAddresses.removeIf(addr -> !(addr instanceof Inet4Address));
        if (!inetAddresses.isEmpty()) {
          ret.add(inetAddresses.get(0));
        }
      }
    } catch (SocketException e) {
      throw new Error("Could not list network interfaces", e);
    }
    return ret;
  }
}
