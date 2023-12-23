package com.example.hangmanapp.control.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.hangmanapp.R;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnectionThread extends Thread{
    private Context context;
    private static final String TAG = BluetoothCommunicationThread.class.getSimpleName();

    private final BluetoothSocket socket;
    private final BluetoothDevice device;
    private BluetoothCommunicationThread communicationThread;

    public BluetoothConnectionThread(Context context, BluetoothDevice device){
        this.context = context;
        BluetoothSocket btSocket = null;
        this.device = device;

        try {
            btSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(context.getString(R.string.BT_UUID)));
        } catch (IOException e) {
            Log.e(TAG, "Sockets create methode failed");
            e.printStackTrace();
        }
        socket = btSocket;
    }



    @SuppressLint("MissingPermission")
    public void run(){
        Log.e(TAG, "run: start running");

        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

        try {
            socket.connect();
        }catch (IOException e){
            try {
                socket.close();
            }catch (IOException ex){
                Log.e(TAG, "Could not close the client socket", ex);
            }
            return;
        }
        Log.i(TAG, "run: Socket connection successful.");
        communicationThread = new BluetoothCommunicationThread(context, socket);
    }

    public BluetoothCommunicationThread getCommunicationThread(){
        return communicationThread;
    }

    public void cancel(){
        try {
            socket.close();
        }catch (IOException e){
            Log.e(TAG, "Could not close the client socket", e);
        }
    }
}

