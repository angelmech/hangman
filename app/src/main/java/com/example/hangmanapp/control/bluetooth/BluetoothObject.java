package com.example.hangmanapp.control.bluetooth;

import android.bluetooth.BluetoothDevice;

public class BluetoothObject {
    private String name;
    private String address;
    private String signalquality;
    private int state;
    private int type;
    private BluetoothDevice device;

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getSignalquality() {
        return signalquality;
    }

    public int getState() {
        return state;
    }

    public int getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSignalquality(String signalquality) {
        this.signalquality = signalquality;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setType(int type) {
        this.type = type;
    }
}
