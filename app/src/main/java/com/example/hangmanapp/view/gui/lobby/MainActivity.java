package com.example.hangmanapp.view.gui.lobby;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.hangmanapp.R;
import com.example.hangmanapp.control.bluetooth.BluetoothManager;
import com.example.hangmanapp.control.bluetooth.BluetoothManagerInterface;
import com.example.hangmanapp.view.gui.bluetooth.BluetoothActivity;

public class MainActivity extends AppCompatActivity {

    private BluetoothManagerInterface btManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btManager = new BluetoothManager(this, this);



    }

    public void openBluetoothLobby(View view) {
        Intent intent = new Intent(this, BluetoothActivity.class);
        startActivity(intent);
    }

}