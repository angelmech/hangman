package com.example.hangmanapp.control.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.example.hangmanapp.view.gui.bluetooth.BluetoothClientFragment;

import java.util.ArrayList;

public interface BluetoothManagerInterface {

    /**
     * Turns off Bluetooth if it's currently enabled.
     */
    void disableBluetooth();

    /**
     * Starts a server thread for accepting connections.
     */
    void startServerThread();

    /**
     * Starts a client thread for connecting to a server.
     *
     * @param context Context
     * @param device  Bluetooth device to connect to
     */
    void startConnectThread(Context context, BluetoothDevice device);

    /**
     * Initiates a device discovery and updates the list of discovered Bluetooth devices.
     *
     * @param clientFragment Fragment that displays the list of discovered devices
     */
    void scan(BluetoothClientFragment clientFragment);

    /**
     * Makes the device discoverable for a limited duration.
     */
    void makeDiscoverable();

    /**
     * Returns the list of discovered Bluetooth devices.
     *
     * @return List of Bluetooth devices
     */
    ArrayList<BluetoothDevice> getBluetoothDeviceList();
}

