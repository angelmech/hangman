package com.example.hangmanapp.control.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hangmanapp.control.bluetooth.threads.AcceptThread;
import com.example.hangmanapp.control.bluetooth.threads.ConnectThread;
import com.example.hangmanapp.view.gui.bluetooth.BluetoothClientFragment;
import com.example.hangmanapp.view.gui.lobby.MainActivity;

import java.util.ArrayList;
import java.util.Objects;

//import joinactivity;

/**
 * Bluetooth Manager class
 * manages bt functionalities
 */
public class BluetoothManager implements BluetoothManagerInterface {

    //-----------ATTRIBUTES-----------
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    /**
     * adapter
     */
    BluetoothAdapter btAdapter;

    /**
     * context
     */
    private Context context;

    /**
     * activity
     */
    private Activity activity;

    /**
     * list of BtDevices
     */
    ArrayList<BluetoothDevice> bluetoothDeviceList;

    //-----------CONSTRUCTOR-----------

    /**
     * constructor
     * @param context c
     * @param activity a
     */
    @SuppressLint("MissingPermission")
    public BluetoothManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        try {
            initBluetooth();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * constructor
     * @param context c
     */
    @SuppressLint("MissingPermission")
    public BluetoothManager(Context context) {
        this.context = context;
        try {
            initBluetooth();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //-----------BLUETOOTH ON/OFF-----------

    /**
     * initializes Bluetooth
     */
    // TODO maybe init adapter in constructor + create enableBt()
    @SuppressLint("MissingPermission")
    private void initBluetooth() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }

        try {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Log.e("BtAdapter","null adapter");
        }
        if (!Objects.requireNonNull(adapter).isEnabled()) {
            adapter.enable();
        }} catch (Exception e) {
            e.printStackTrace();
        }

        /*
        try {
            btAdapter = BluetoothAdapter.getDefaultAdapter();
            if (btAdapter == null) {
                Log.e("Bluetooth device", "no Bluetooth support!");
                throw new Exception("This device does not support Bluetooth");
            }
            if (!btAdapter.isEnabled()) {
                // btAdapter.enable(); // Bluetooth on is forced, no request!
                //showToast("Turning On Bluetooth...");
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(intent, REQUEST_ENABLE_BT);
            }
        } catch (Exception e) {
            showToast(e.getMessage());
            e.printStackTrace();
        }*/
    }

    /**
     * turns off Bluetooth
     */
    @SuppressLint("MissingPermission")
    public void disableBluetooth() {
        if (btAdapter.isEnabled()) {
            btAdapter.disable();
            showToast("Turning Bluetooth Off");
            // action...
        } else {
            showToast("Bluetooth is already off");
        }
    }

    //-----------START THREAD-----------

    // maybe do this in connection handler, and also the data exchange

    /**
     * start a server Thread, purpose: accept connections from client
     */
    public void startServerThread() {
        //initBluetooth();

        AcceptThread serverThread = new AcceptThread(context);
        serverThread.start();
    }

    /**
     * start Client thread, purpose: connecting to server
     * @param context context
     * @param device bluetooth
     */
    public void startConnectThread(Context context, BluetoothDevice device) {
        ConnectThread connectThread = new ConnectThread(context, device);
        connectThread.start();
    }

    //-----------DISCOVERY & DISCOVERABLE-----------

    /**
     * discovery
     * @param clientFragment cF
     */
    @SuppressLint("MissingPermission")
    public void scan(BluetoothClientFragment clientFragment) {
        // initBluetooth();

        bluetoothDeviceList = new ArrayList<>();

        //Requests Location permission
        ActivityCompat.requestPermissions(clientFragment.requireActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);


        if (btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
            Log.println(Log.INFO, "BluetoothAdapter", "already discovering stopped");
        }
        this.btAdapter.startDiscovery();
        Log.println(Log.INFO, "BluetoothAdapter", "started Discovery");

        //Discover new Devices
        //Create a BR for ACTION_FOUND
        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            // this action executed on every found device
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    getBluetoothDeviceList().add(device);

                    clientFragment.notifyDataSetHasChanged();
                }
            }
        };

        // Register BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);
    }

    /**
     * discoverable
     */
    @SuppressLint("MissingPermission")
    public void makeDiscoverable() {
        //initBluetooth();

        // discoverable
        if (!btAdapter.isDiscovering()) {
            Log.println(Log.INFO, "BluetoothAdapter", "started Discoverable");
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            activity.startActivityForResult(intent, REQUEST_DISCOVER_BT);
        }
    }

    //-----------LIST-----------
    /**
     * list with all devices
     * @return btdevice lsit
     */
    public ArrayList<BluetoothDevice> getBluetoothDeviceList() {
        return bluetoothDeviceList;
    }

    //-----------MESSAGE-----------
    /**
     * shows a message
     * @param msg String
     */
    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}