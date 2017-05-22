package org.lejos.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Discover {

    public static boolean isReachable(String addr, int openPort, int timeOutMillis) {
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

}