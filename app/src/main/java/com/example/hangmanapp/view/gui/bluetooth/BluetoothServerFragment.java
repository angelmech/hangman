package com.example.hangmanapp.view.gui.bluetooth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hangmanapp.R;
import com.example.hangmanapp.control.bluetooth.BluetoothManager;
import com.example.hangmanapp.control.bluetooth.BluetoothManagerInterface;

public class BluetoothServerFragment extends Fragment {
    private BluetoothManagerInterface btManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth_server, container, false);

        btManager = new BluetoothManager(getContext());
        btManager.startServerThread();

        return view;
    }
}
