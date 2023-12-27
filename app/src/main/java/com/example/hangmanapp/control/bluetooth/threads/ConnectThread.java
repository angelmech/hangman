package com.example.hangmanapp.control.bluetooth.threads;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.hangmanapp.R;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread{
    private Context context;
    private static final String TAG = "ConnectThread"; //CommunicationThread.class.getSimpleName();
    private final BluetoothSocket socket;
    private final BluetoothDevice device;
    private CommunicationThread communicationThread;
    private final UUID APP_UUID = UUID.fromString(context.getString(R.string.app_uuid));

    /**
     * constructor of Connect thread
     * @param context of activity
     * @param device bluetooth
     */
    @SuppressLint("MissingPermission")
    public ConnectThread(Context context, BluetoothDevice device){
        this.context = context;
        BluetoothSocket tmp = null;
        this.device = device;

        try {
            tmp = device.createInsecureRfcommSocketToServiceRecord(APP_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() methode failed");
            e.printStackTrace();
        }
        socket = tmp;
    }

    /**
     * run method of connect thread
     */
    @SuppressLint("MissingPermission")
    public void run(){
        Log.e(TAG, "run: start running");
        // Cancel discovery because it otherwise slows down the connection.
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

        try {
            // Connect to the remote device through the socket.
            // This call blocks until it succeeds or throws an exception.
            socket.connect();
        }catch (IOException connectException){
            // Unable to connect; close the socket and return.
            try {
                socket.close();
            }catch (IOException closeException){
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded.
        Log.i(TAG, "run: Socket connection successful.");
        // Perform work associated with the connection in a separate thread.
        communicationThread = new CommunicationThread(context, socket);
    }

    /**
     * returns comm thread
     * @return communicationThread object
     */
    public CommunicationThread getCommunicationThread(){
        return communicationThread;
    }

    /**
     * Closes the client socket and causes the thread to finish.
     */
    public void cancel(){
        try {
            socket.close();
        }catch (IOException e){
            Log.e(TAG, "Could not close the client socket", e);
        }
    }
}

