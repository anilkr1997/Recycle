package com.v24.recycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.v24.recycle.Bluetoothemodule.ListofAllbluetoothe;
import com.v24.recycle.uttil.Utill;

import es.dmoral.toasty.Toasty;


public class Splacescreen extends Activity {
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private Utill utill;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mBluetoothDevice;
    private ProgressDialog mBluetoothConnectProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splacescreen);
        utill = new Utill(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!utill.checkAndRequestPermissions()) {
            utill.checkAndRequestPermissions();
        }

        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Splacescreen.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);
        }


    }

    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {


            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    // ListPairedDevices();
                    Intent connectIntent = new Intent(Splacescreen.this, MainActivity.class);
                    startActivity(connectIntent);
                    Toasty.success(this, "Bluetooth Is  On", 10).show();
                    finish();

//                    Intent bluetoothPicker = new Intent("android.bluetooth.devicepicker.action.LAUNCH");
//                    startActivity(bluetoothPicker);
                    // startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Intent connectIntent = new Intent(Splacescreen.this, MainActivity.class);
                    startActivity(connectIntent);
                    finish();

                    Toasty.warning(this, "Bluetooth Is Not On", 10).show();
                }
                break;
        }
    }



}

