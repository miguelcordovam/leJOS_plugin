package org.lejos.util;

public class BrickInfo {

    private String name;
    private String ipAddress;
    private String type;

    public BrickInfo(String name, String ipAddress, String type) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getIPAddress() {
        return this.ipAddress;
    }

    public String getType() {
        return this.type;
    }

}
