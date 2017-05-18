package org.lejos.util;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Discover {

    private static final int DISCOVERY_PORT = 3016;
    private static final int MAX_PACKET_SIZE = 32;
    private static final String FIND_CMD = "find";
    private static final int MAX_HOPS = 2;
    private static final int MAX_DISCOVERY_TIME = 2000;

    public Discover() {
    }

    public static BrickInfo[] discover(String name) throws Exception {
        DatagramSocket socket = null;
        Map<String, BrickInfo> ev3s = new HashMap<>();

        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            if(name == null) {
                name = "*";
            }

            boolean devices = name.equalsIgnoreCase("*");
            broadcastFindRequest(socket, name, (InetAddress)null, -1, 2);
            socket.setSoTimeout(500);
            DatagramPacket datagramPacket = new DatagramPacket(new byte[32], 32);
            long ev3 = System.currentTimeMillis();

            while(System.currentTimeMillis() - ev3 < 2000L) {
                try {
                    socket.receive(datagramPacket);
                    String message = (new String(datagramPacket.getData(), "UTF-8")).trim();
                    if(devices || message.equalsIgnoreCase(name)) {
                        String ip = datagramPacket.getAddress().getHostAddress();
                        ev3s.put(ip, new BrickInfo(message.trim(), ip, "EV3"));
                    }
                } catch (SocketTimeoutException e) {
                    broadcastFindRequest(socket, name, (InetAddress)null, -1, 2);
                }
            }
        } catch (IOException e) {
//            LeJOSEV3Util.message("Exception opening socket : " + e);
        } finally {
            if(socket != null) {
                socket.close();
            }
        }

        BrickInfo[] bricks = new BrickInfo[ev3s.size()];
        int index = 0;

        String ipAddress;
        Iterator ip = ev3s.keySet().iterator();
        while (ip.hasNext()) {
            ipAddress = (String)ip.next();
            bricks[index++] = ev3s.get(ipAddress);
        }

        return bricks;
    }

    private static void broadcastFindRequest(DatagramSocket socket, String name, InetAddress replyAddr, int replyPort, int hop) {
        try {
            Enumeration ex = NetworkInterface.getNetworkInterfaces();

            while(true) {
                NetworkInterface networkInterface;
                do {
                    do {
                        if(!ex.hasMoreElements()) {
                            return;
                        }

                        networkInterface = (NetworkInterface)ex.nextElement();
                    } while(networkInterface.isLoopback());
                } while(!networkInterface.isUp());

                Iterator interfaceAddressIterator = networkInterface.getInterfaceAddresses().iterator();

                while(interfaceAddressIterator.hasNext()) {
                    InterfaceAddress interfaceAddress = (InterfaceAddress)interfaceAddressIterator.next();
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if(broadcast != null) {
                        String message = "find " + name;
                        if(replyAddr == null) {
                            message = message + " " + interfaceAddress.getAddress().getHostAddress() + " " + socket.getLocalPort();
                        } else {
                            message = message + " " + replyAddr.getHostAddress() + " " + replyPort;
                        }

                        message = message + " " + hop;
                        byte[] sendData = message.getBytes();

                        try {
                            DatagramPacket datagramPacket = new DatagramPacket(sendData, sendData.length, broadcast, 3016);
                            socket.send(datagramPacket);
                        } catch (Exception e) {
//                            LeJOSEV3Util.message("Exception sending to : " + networkInterface.getDisplayName() + " : " + e);
                        }
                    }
                }
            }
        } catch (IOException e) {
//            LeJOSEV3Util.message("Exception opening socket : " + e);
        }
    }
}