package com.v24.recycle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.usb.UsbConnection;
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.Modelclass.getUrllist;
import com.v24.recycle.Printer.AsyncBluetoothEscPosPrint;
import com.v24.recycle.Printer.AsyncUsbEscPosPrint;
import com.v24.recycle.dbhelper.Databasehelper;
import com.v24.recycle.uttil.Utill;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public static final int PERMISSION_BLUETOOTH = 1;
    private Button btn_next, btn_print, btn_blutooth;
    private TextView tv_serialNo;
    private TextView tv_price;
    private AppCompatSpinner spinner_from, spinner_viewurl;
    private ArrayList<Stationlist> stationlists;
    private ArrayList<getUrllist> Arrayurl;
    private Databasehelper db;
    private ArrayList arrayList, arrayListauto, urlArray;
    private ArrayAdapter arrayAdapter, arrayAdapter2, UrlArrayAdopter;
    private static String Station_to = "", Station_from = "", fare = "00.00", S_No = "", Barcode = "";
    private Utill utill;
    private long serialnumber = 0;
    private static int fromselaction = 0;
    private static int toselaction = 0;
    int ticketqty = 1;
    private AppCompatEditText barcodeurl, teciketquntity;
    private SearchableSpinner spinnersearch;
    private BluetoothConnection selectedDevice;
    private int urlid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Databasehelper(this);
        arrayList = new ArrayList();
        arrayListauto = new ArrayList();
        urlArray = new ArrayList();
        Arrayurl = new ArrayList();
        btn_next = findViewById(R.id.btn_next);
        btn_print = findViewById(R.id.btn_print);
        btn_blutooth = findViewById(R.id.btn_blutooth);
        btn_next.setOnClickListener(this::onClick);
        btn_print.setOnClickListener(this::onClick);
        btn_blutooth.setOnClickListener(this::onClick);
        tv_serialNo = findViewById(R.id.tv_S_No);
        tv_price = findViewById(R.id.tv_price);
        tv_price.setOnClickListener(this::onClick);
        barcodeurl = findViewById(R.id.barcodeurl);
        teciketquntity = findViewById(R.id.teciketquntity);
        spinner_from = findViewById(R.id.Spinner_from);
        spinner_viewurl = findViewById(R.id.spinner_viewurl);
        spinner_viewurl.setSelection(0);
        spinner_from.setSelection(fromselaction);
        spinnersearch = findViewById(R.id.autosearch);
        spinnersearch.setSelection(toselaction);
        utill = new Utill(this);


        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (stationlists.size() > 0) {

                    if (position != 0) {
                        fromselaction = position;
                        utill.shavespinnerTovalue(toselaction, fromselaction);
                        Station_from = spinner_from.getSelectedItem().toString();
                        // Toasty.success(MainActivity.this, "Id=: " + position, 10).show();
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnersearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toselaction = Integer.parseInt(String.valueOf(parent.getAdapter().getItemId(position)));
                if (stationlists.size() > 0) {

                    Station_to = String.valueOf(parent.getAdapter().getItem(position));
                    if (toselaction != 0 && fromselaction != 0 && toselaction != fromselaction) {
                        totalfare(fromselaction, toselaction);
                        urllistget(toselaction);

                        utill.shavespinnerTovalue(toselaction, fromselaction);


                    } else {
                        Toasty.error(MainActivity.this, "Select From or To Station", 10).show();

                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_viewurl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (urlArray.size() > 0) {
                    if (position != 0) {
                        urlid=position;

                        barcodeurl.setText(spinner_viewurl.getSelectedItem().toString());
                    } else {
                        barcodeurl.setHint("Enter URL,Phone number");

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void urllistget(int toselaction) {
        urlArray.clear();
        urlArray.add("Select URl");
        Arrayurl = db.getUrl(toselaction);

        if (Arrayurl.size() > 0) {
            for (int i = 0; i <= Arrayurl.size() - 1; i++) {
                urlArray.add(Arrayurl.get(i).getUrl());
            }
        } else {
            Toasty.error(this, "Add A URl ", 10).show();
        }
        UrlArrayAdopter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, urlArray);
        UrlArrayAdopter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_viewurl.setAdapter(UrlArrayAdopter);
    }
    private void totalfare(int fromselaction, int toselaction) {
        String totalfare = db.Fareget(fromselaction, toselaction);
        if (!totalfare.equals("00.00")) {
            fare = totalfare;
            tv_price.setText("Rs. " + fare + ".00");
        } else {
            Toasty.error(this, "Not found this Fare", 10).show();
            tv_price.setText("Rs." + fare);
        }
    }

    private void getlistofstation() {
        arrayList.clear();
        arrayListauto.clear();


        arrayList.add("Select From  Station");
        arrayListauto.add("Select To Station");
        stationlists = db.getAllCotacts();

        if (stationlists.size() > 0) {
            for (int i = 0; i <= stationlists.size() - 1; i++) {
                arrayList.add(stationlists.get(i).getName());
                arrayListauto.add(stationlists.get(i).getName());
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayListauto);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_from.setAdapter(arrayAdapter);
            spinnersearch.setAdapter(arrayAdapter2);//setting the adapter data into the AutoCompleteTextView

        } else {
            Toasty.error(this, "Add A Station Name ", 10).show();

        }

    }


    @Override
    protected void onResume() {
        getlistofstation();
        gettcserialnumber();
        // db.Stationfarelist();
        super.onResume();

        Log.e("start", "R");
        if (stationlists.size() > 0) {
            fromselaction = utill.getFrompos();
            toselaction = utill.getTopos();
            spinner_from.setSelection(fromselaction);
            spinnersearch.setSelection(toselaction);

        }
    }

    private void gettcserialnumber() {
        String tsnomber = db.getTSNO();
        // serialnumber = Long.parseLong(utill.getshareprefrance());
        if (!tsnomber.equals("")) {
            serialnumber = Long.parseLong(tsnomber);
            tv_serialNo.setText(String.valueOf(serialnumber));
        } else {
            utill.customedilogbox(getResources(), S_No);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("start", "B");
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("start", "sav");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dasboard:
                startActivity(new Intent(this, Dasboard.class));
                return true;
            case R.id.aboutus:
                utill.customedilogbox(getResources(), S_No);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:

                S_No = tv_serialNo.getText().toString();
                Barcode = barcodeurl.getText().toString();


                if (!Barcode.equals("") && !fare.equals("00.00") && !Station_from.equals("") && !Station_to.equals("") && !tv_serialNo.getText().toString().equals(" ") && !tv_serialNo.getText().toString().equals("")) {
                    startActivity(new Intent(this, printticket.class)
                            .putExtra("Fare", fare)
                            .putExtra("S_no", S_No)
                            .putExtra("S_to", Station_to)
                            .putExtra("S_from", Station_from)
                            .putExtra("barcodelink", Barcode)

                    );
                } else {
                    Toasty.error(this, "Please Check the required Filde", 10).show();

                }


                break;

            case R.id.btn_print:
                Barcode = barcodeurl.getText().toString();
                S_No = tv_serialNo.getText().toString();


                if (!Barcode.equals("") && !fare.equals("00.00") && !Station_from.equals("") && !Station_to.equals("") && !tv_serialNo.getText().toString().equals(" ") && !tv_serialNo.getText().toString().equals("")) {


                    usbconnection();
                } else {
                    Toasty.error(this, "Please Check the required Filde", 10).show();

                }
                break;
            case R.id.tv_price:
                if (toselaction != 0 && fromselaction != 0 && toselaction != fromselaction) {
                    totalfare(fromselaction, toselaction);
                } else {
                    Toasty.error(this, "Please Check the required Filde", 10).show();

                }
                break;
            case R.id.btn_blutooth:
                Barcode = barcodeurl.getText().toString();
                S_No = tv_serialNo.getText().toString();
                if (!Barcode.equals("") && !fare.equals("00.00") && !Station_from.equals("") && !Station_to.equals("") && !tv_serialNo.getText().toString().equals(" ") && !tv_serialNo.getText().toString().equals("")) {

                    selectedDevice = utill.browseBluetoothDevice();
                    if (selectedDevice != null) {
                        blutootheprinter(selectedDevice);

                    } else {
                        BluetoothAdapter.getDefaultAdapter().enable();
                        Toasty.warning(this, "Please Check the Bluetooth Status  ", 10).show();

                    }
                } else {
                    Toasty.error(this, "Please Check the required Filde", 10).show();

                }


                break;


            default:
                throw new IllegalStateException("Unexpected value: ");
        }

    }

    private void blutootheprinter(BluetoothConnection selectedDevice) {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, MainActivity.PERMISSION_BLUETOOTH);
        } else {


            try {

                String curent_dateToStr = utill.todaydate();
                String validatenewTime = utill.valitatetime();
                String uptovalide = utill.valideteupto();

                ticketqty = Integer.parseInt(teciketquntity.getText().toString());
                if (ticketqty != 0) {
                    for (int i = 0; i < ticketqty; i++) {
                        S_No = tv_serialNo.getText().toString();
                        new AsyncBluetoothEscPosPrint(this).execute(this.utill.tcprint(selectedDevice, S_No, curent_dateToStr, Station_to, Station_from, Barcode, fare, validatenewTime, uptovalide));
                        if (Long.parseLong(S_No) == serialnumber) {
                            serialnumber++;
                            db.updateTSNO(1, String.valueOf(serialnumber));
                            gettcserialnumber();
                        }
                        Thread.sleep(5000);

                    }
                   boolean result= db.deleteURl(urlid);
                    if(result){
                        urllistget(toselaction);
                    }
                } else {
                    Toasty.error(this, "Please Enter teciket quentity ", 5).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void usbconnection() {

        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(this);
        UsbManager usbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);

        if (usbConnection == null || usbManager == null) {

            new AlertDialog.Builder(this)
                    .setTitle("USB Connection")
                    .setMessage("No USB printer found.")
                    .show();
            return;
        }

        PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(MainActivity.ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(MainActivity.ACTION_USB_PERMISSION);
        registerReceiver(this.usbReceiver, filter);
        usbManager.requestPermission(usbConnection.getDevice(), permissionIntent);


    }


    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MainActivity.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbManager != null && usbDevice != null) {




                            try {

                                String curent_dateToStr = utill.todaydate();
                                String validatenewTime = utill.valitatetime();
                                String uptovalide = utill.valideteupto();

                                ticketqty = Integer.parseInt(teciketquntity.getText().toString());
                                if (ticketqty != 0) {
                                    for (int i = 0; i < ticketqty; i++) {
                                        S_No = tv_serialNo.getText().toString();
                                        new AsyncUsbEscPosPrint(context).execute(utill.tcprint(new UsbConnection(usbManager, usbDevice), S_No, curent_dateToStr, Station_to, Station_from, Barcode, fare, validatenewTime, uptovalide));
                                        if (Long.parseLong(S_No) == serialnumber) {
                                            serialnumber++;
                                            db.updateTSNO(1, String.valueOf(serialnumber));
                                            gettcserialnumber();
                                        }
                                        Thread.sleep(5000);

                                    }
                                    boolean result= db.deleteURl(urlid);
                                    if(result){
                                        urllistget(toselaction);
                                    }                                } else {
                                    Toasty.error(context, "Please Enter teciket quentity ", 5).show();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }



                        }
                    }
                }
            }
        }
    };



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case MainActivity.PERMISSION_BLUETOOTH:
                    blutootheprinter(selectedDevice);
                    // new AsyncBluetoothEscPosPrint(this).execute(this.getAsyncEscPosPrinter(selectedDevice));
                    break;
            }
        }
    }


}