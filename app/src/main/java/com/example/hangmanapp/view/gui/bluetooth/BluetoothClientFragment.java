package com.example.hangmanapp.view.gui.bluetooth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangmanapp.R;
import com.example.hangmanapp.control.bluetooth.BluetoothManager;
import com.example.hangmanapp.control.bluetooth.BluetoothManagerInterface;

public class BluetoothClientFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private BluetoothManagerInterface btManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth_lobby, container, false);

        btManager = new BluetoothManager(getContext());
        btManager.scan(this);

        recyclerView = view.findViewById(R.id.BT_recyclerview);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.hasFixedSize();

        layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new BluetoothDeviceAdapter(btManager.getBluetoothDeviceList(), btManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyDataSetHasChanged() {
        mAdapter.notifyDataSetChanged();
    }

}
