package com.example.hangmanapp.control.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.ArrayList;

public interface BluetoothManagerInterface {
    /**
     * Returns a List of Bluetooth Devices that the device already knows.
     * @return List<BluetoothDevice>
     */
    public List<BluetoothDevice> getKnownDevices();

    /**
     * Returns a BroadcastReceiver used that can be used for device discovery.
     * @return BroadcastReceiver
     */
    public BroadcastReceiver getBluetoothDeviceReceiver();


    /**
     * Enables the discoverability of the device.
     * @param activity
     */
    public void enableDiscoverability(Activity activity);

    /**
     * This method starts the bluetooth device discovery.
     * @param activity
     */
    @SuppressLint("MissingPermission")
    void discoverDevices(Activity activity);

    /**
     * Starts an accept Thread for Host device.
     */
    public void accept();

    /**
     * Starts a connect Thread for Client device.
     * @param device
     */
    public void connect(BluetoothDevice device);
}
