package com.example.hangmanapp.control.bluetooth.threads;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hangmanapp.R;

import java.io.IOException;
import java.util.UUID;

/**
 * AcceptThread
 */
public class AcceptThread extends Thread {

    private Context context;
    private static final String TAG = "AcceptThread";
    // private final String APP_NAME = context.getString(R.string.app_name);
    //private final UUID APP_UUID = UUID.fromString(context.getString(R.string.app_uuid));
    private final BluetoothServerSocket serverSocket;
    private CommunicationThread communicationThread;

    @SuppressLint("MissingPermission")
    public AcceptThread(Context context) {
        this.context = context;
        BluetoothServerSocket tmp = null;
        try {
            tmp = BluetoothAdapter.getDefaultAdapter().
                    listenUsingRfcommWithServiceRecord(
                            context.getString(R.string.app_name),
                            UUID.fromString(context.getString(R.string.app_uuid)));
        } catch (IOException e) {
            Log.e(TAG, "Socket´s listen() method failed", e);
        }
        serverSocket = tmp;
    }

    /**
     * returns Communication Thread
     *
     * @return comms Thread
     */
    public CommunicationThread getCommunicationThread() {
        return communicationThread;
    }

    public void run() {
        Log.d(TAG, "run: start running");
        BluetoothSocket socket = null;
        //Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket´s accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread
                Toast.makeText(context, "Connection accepted", Toast.LENGTH_LONG).show();
                Log.d(TAG, "run: it works");
                communicationThread = new CommunicationThread(context, socket);
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    Log.e(TAG, "Sockets close() method failed", e);
                }
                break;
            }
        }
    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Sockets close() method failed", e);
        }
    }

}
