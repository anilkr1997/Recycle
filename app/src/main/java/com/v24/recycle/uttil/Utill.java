package com.v24.recycle.uttil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.v24.recycle.MainActivity;
import com.v24.recycle.Printer.AsyncEscPosPrinter;
import com.v24.recycle.R;
import com.v24.recycle.dbhelper.Databasehelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class Utill {
    Databasehelper db;
    private Activity context;
    private SharedPreferences sharedPreferences;
    private Dialog dialog;
    private AppCompatEditText editText;
    private AppCompatButton button;
    private String tserialno = null;
    private FloatingActionButton floatingActionButton;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private BluetoothConnection selectedDevice;

    public Utill(Activity splacescreen) {
        this.context = splacescreen;
    }


    public boolean sharedprefrence(String Tcno) {
        sharedPreferences = context.getSharedPreferences("TicketSerialNo", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("S.No", Tcno);
        myEdit.apply();
        Toasty.success(context, "value Updated", 5).show();
        return true;
    }

    public String getshareprefrance() {
        sharedPreferences = context.getSharedPreferences("TicketSerialNo", MODE_PRIVATE);

        String anInt = sharedPreferences.getString("S.No", "");
        return anInt;
    }

    public boolean shavespinnerTovalue(int topos, int frompos) {
        sharedPreferences = context.getSharedPreferences("spinnerTovalue", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("S.To", topos);
        myEdit.putInt("S.From", frompos);
        myEdit.apply();
        return true;
    }

    public int getTopos() {
        sharedPreferences = context.getSharedPreferences("spinnerTovalue", MODE_PRIVATE);
        int pos = sharedPreferences.getInt("S.To", 0);
        return pos;
    }

    public int getFrompos() {
        sharedPreferences = context.getSharedPreferences("spinnerTovalue", MODE_PRIVATE);
        int frompos = sharedPreferences.getInt("S.From", 0);
        return frompos;
    }

    public void customedilogbox(Resources resources, String s_No) {
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(80, 80);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.customedilogbox);
        dialog.show();
        db = new Databasehelper(context);

        editText = dialog.findViewById(R.id.serialnumber);
        button = dialog.findViewById(R.id.sumbite);
        floatingActionButton = dialog.findViewById(R.id.fab_close);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (db.getTSNO() != "") {
            tserialno = db.getTSNO();
            editText.setText(db.getTSNO());
        } else {
            Toasty.warning(context, "Data not Found", 10).show();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serialnumber = "";

                serialnumber = editText.getText().toString();

                if (tserialno != null) {
                    db.updateTSNO(1, serialnumber);
                } else {

                    if (serialnumber != "") {
                        db.inserttTSNO(serialnumber);
                        dialog.dismiss();
                        context.startActivity(new Intent(context, MainActivity.class));
                        context.finish();
                        context.startActivity(context.getIntent());

                    } else {
                        Toasty.error(context, "Somthing want to wrong", 10).show();
                    }
                }

            }
        });


    }

    public boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        List<String> permissinongratted = new ArrayList<>();
        listPermissionsNeeded.add(Manifest.permission.BLUETOOTH_ADMIN);
        listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        listPermissionsNeeded.add(Manifest.permission.INTERNET);
        listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        for (int i = 0; i <= listPermissionsNeeded.size() - 1; i++) {
            int BlutoothePermission = ContextCompat.checkSelfPermission(context, listPermissionsNeeded.get(i));
            if (BlutoothePermission != PackageManager.PERMISSION_GRANTED) {
                permissinongratted.add(listPermissionsNeeded.get(i));

            }
        }


        if (!permissinongratted.isEmpty()) {
            ActivityCompat.requestPermissions(context, permissinongratted.toArray(new String[permissinongratted.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            // startActivity(new Intent(Splacescreen.this,MainActivity.class));
            return true;
        } else {
            return true;

        }

    }

    public AsyncEscPosPrinter tcprint(DeviceConnection printerConnection, String s_No, String curent_dateToStr, String station_to, String station_from, String barcode, String fare, String validatenewTime, String uptovalide) {
        AsyncEscPosPrinter printer = null;
        printer = new AsyncEscPosPrinter(printerConnection, 203, 80f, 32);
        printer.setTextToPrint(
                "[C]<font size='big'>" + "    Surat Sitilink Ltd.\n     TRAVER TICKET" + "</font>\n" +
                        "[L]\n" +
                        "[L]<b>  T.No :  </b>[R] " + s_No + " \n" +
                        "[L]<b>  DATE  </b>[R] " + curent_dateToStr + " \n" +
                        "[L]<b>  FROM </b>[R]" + station_from + " \n" +
                        "[L]<b>  To </b>[R]" + station_to + " \n" +
                        "[L]<b>  Total Fare  </b>[R]RS." + fare + "00 \n" +
                        "[L]<b>  ENTERY VALID WITH IN  </b>[R] " + validatenewTime + " \n" +
                        "[L]<b>  Ticket Validupto  </b>[R]" + uptovalide + "\n" +
                        "[L]\n" +
                        "[C]<qrcode size='23'>" + barcode + "</qrcode>\n" +
                        "[L]\n" +
                        "[C]    This Ticket is not Refundable \n     Customer Care No : 18002330233\n" +
                        "[L]\n"


        );
        uploaddata(s_No, curent_dateToStr, station_to, station_from, barcode, fare, validatenewTime, uptovalide);
        return printer;
    }


    public void uploaddata(String s_No, String str_Date, String station_to, String station_from, String barcode, String fare, String str_validation, String str_valide_up) {
        db = new Databasehelper(context);

        db.Ticket_insert(s_No, str_Date, station_to, station_from, barcode, fare, str_validation, str_valide_up);
        if (true) {
            // PrinterBlutoothe();
            Toasty.success(context, "Add SucessFully", 5).show();

        } else
            Toasty.error(context, "Somthing Want to worng", 5).show();

    }

    public String filterdate() {
        String curent_dateToStr = "";
        Date today = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        curent_dateToStr = format.format(today);

        return curent_dateToStr;
    }

    public String todaydate() {
        String curent_dateToStr = "";
        Date today = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        curent_dateToStr = format.format(today);

        return curent_dateToStr;
    }

    public String valitatetime() {
        String validatenewTime = "";
        try {
            Date today = new Date();
            SimpleDateFormat format15 = new SimpleDateFormat("HH:mm:ss");
            String next_15_minut = format15.format(today);
            today = format15.parse(next_15_minut);
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.MINUTE, 10);
            validatenewTime = format15.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return validatenewTime;
    }

    public String valideteupto() {
        String uptovalide = "";
        try {
            Date today = new Date();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String curent_dateToStr = format.format(today);
            Calendar cal = Calendar.getInstance();

            today = format.parse(curent_dateToStr);
            cal.setTime(today);
            cal.add(Calendar.HOUR, 2);
            uptovalide = format.format(cal.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return uptovalide;
    }


    public Bitmap barcodegeneration(String barcode) {
        Bitmap bitmap = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(barcode, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public BluetoothConnection browseBluetoothDevice() {
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();

        if (bluetoothDevicesList != null) {
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Bluetooth  printer List ";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                items[++i] = device.getDevice().getName();
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = i - 1;
                    if (index == -1) {
                        selectedDevice = null;
                    } else {
                        selectedDevice = bluetoothDevicesList[index];
                    }
//                    Button button = (Button) findViewById(R.id.button_bluetooth_browse);
//                    button.setText(items[i]);
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
        return selectedDevice;
    }


}
