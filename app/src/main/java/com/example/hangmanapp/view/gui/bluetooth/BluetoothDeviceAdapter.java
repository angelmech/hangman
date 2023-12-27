package com.example.hangmanapp.view.gui.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangmanapp.R;
import com.example.hangmanapp.control.bluetooth.BluetoothManagerInterface;

import java.util.ArrayList;


public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceAdapter.BluetoothDeviceViewHolder>  {
    private ArrayList<BluetoothDevice> mDataSet;

    private BluetoothManagerInterface btManager;

    private Context context;

    public static class BluetoothDeviceViewHolder extends RecyclerView.ViewHolder {

        public TextView devicename;

        public Button connectBTN;

        public BluetoothDeviceViewHolder(View itemView) {
            super(itemView);

            devicename = itemView.findViewById(R.id.playerName);
            connectBTN = itemView.findViewById(R.id.joinButton);
        }
    }

    public BluetoothDeviceAdapter(ArrayList<BluetoothDevice> dataSet, BluetoothManagerInterface btManager) {
        this.btManager = btManager;
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public BluetoothDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_device_view_holder, parent, false);
        BluetoothDeviceViewHolder bvh = new BluetoothDeviceViewHolder(v);

        context = parent.getContext();

        return bvh;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull final BluetoothDeviceViewHolder holder, int position) {
        final BluetoothDevice currentItem = mDataSet.get(position);
        if (position != RecyclerView.NO_POSITION) {
            holder.devicename.setText(currentItem.getName());
            holder.connectBTN.setOnClickListener(v -> btManager.startConnectThread(context, currentItem));
        }
        //holder.playername.setText(Player.getName));

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
