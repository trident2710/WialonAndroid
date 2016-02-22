package com.worldtrack.wialonconnection.data;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Worldtrack 16.07.15.
 */
public class Credential implements Serializable{
    String ipAddress;
    int port;
    String unitID;
    String password;
    String driverID;

    public Credential(@NonNull String ipAddress, @NonNull int port, @NonNull String unitID, @NonNull String driverID,String password) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.unitID = unitID;
        if(password!=null) password = "NA";
        else this.password = password;

        this.driverID = driverID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }
}
