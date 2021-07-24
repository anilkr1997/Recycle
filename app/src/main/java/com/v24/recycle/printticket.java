package com.v24.recycle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.v24.recycle.dbhelper.Databasehelper;
import com.v24.recycle.uttil.Utill;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class printticket extends Activity implements View.OnClickListener {
    Button btnprint;
    CardView cardView;
    private Utill utill;
    AppCompatImageView img_barcodeimage;
    TextView tv_serialNo, Tv_Date, tv_S_to, tv_S_From, tv_total_fare, tv_validation, tv_valide_up, tv_hader, Tv_notrefantabel;
    private String Station_to, Station_from, fare, S_No, str_Date, str_validation, uptovalide, Barcode, curent_dateToStr, validatenewTime;
    private SimpleDateFormat format, format15;
    private Date today, today2;
    private Calendar cal;
    String note = "This Ticket is not Refundable" + "\n" + "Customer Care No : 18002330233";
    private Databasehelper db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printticket);
        db = new Databasehelper(this);
        utill = new Utill(this);
        btnprint = findViewById(R.id.btn_print);
        btnprint.setVisibility(View.GONE);
        cardView = findViewById(R.id.cardView);
        img_barcodeimage = findViewById(R.id.barcodeimage);
        tv_serialNo = findViewById(R.id.tv_S_No);
        Tv_Date = findViewById(R.id.tv_Date);
        tv_S_to = findViewById(R.id.tv_Station_name);
        tv_S_From = findViewById(R.id.tv_Station_name2);
        tv_total_fare = findViewById(R.id.tv_total_fare);
        tv_validation = findViewById(R.id.tv_validate_time);
        tv_valide_up = findViewById(R.id.tv_valideteupto);
        tv_hader = findViewById(R.id.Tv_hadder);
        Tv_notrefantabel = findViewById(R.id.Tv_notrefantabel);
        Tv_notrefantabel.setText(note);
        btnprint.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Station_from = intent.getExtras().getString("S_from");
            Station_to = intent.getExtras().getString("S_to");
            S_No = intent.getExtras().getString("S_no");
            fare = intent.getExtras().getString("Fare");
            Barcode = intent.getExtras().getString("barcodelink");
            // Toasty.warning(this, " "+fare+" "+S_No+" "+Station_to+" "+Station_from, 10).show();
            tv_S_From.setText(Station_from);
            tv_S_to.setText(Station_to);
            tv_serialNo.setText("T.No  " + S_No);
            tv_total_fare.setText("Total Fare : Rs." + fare + ".00");
             curent_dateToStr = utill.todaydate();
            Tv_Date.setText("DATE  " + curent_dateToStr);
             validatenewTime = utill.valitatetime();
            tv_validation.setText("Entry Valid Within  :" + validatenewTime);
             uptovalide = utill.valideteupto();
            tv_valide_up.setText("Ticket Valid Upto  :" + uptovalide);

        } else {
            Toasty.error(this, "Ticket is not found please try again.......", 10).show();

        }


       // barcodegeneration();
        img_barcodeimage.setImageBitmap(utill.barcodegeneration(Barcode));


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_print:
                str_Date = Tv_Date.getText().toString();
                str_validation = tv_validation.getText().toString();
                //  str_valide_up=tv_valide_up.getText().toString();

                if (S_No != null && !curent_dateToStr.equals(" ") && Station_to != null && Station_from != null && Barcode != null && fare != null && !validatenewTime.equals("") && !uptovalide.equals("")) {
                    uploaddata(S_No, curent_dateToStr, Station_to, Station_from, Barcode, fare, validatenewTime, uptovalide);

                } else
                    Toasty.error(this, "Somthing Want to worng", 10).show();
                break;

            default:
                throw new IllegalStateException("Unexpected value: ");
        }

    }

    private void uploaddata(String s_No, String str_Date, String station_to, String station_from, String barcode, String fare, String str_validation, String str_valide_up) {
        db.Ticket_insert(s_No, str_Date, station_to, station_from, barcode, fare, str_validation, str_valide_up);
        if (true) {
            // PrinterBlutoothe();
            Toasty.success(this, "Add SucessFully", 5).show();

        } else
            Toasty.error(this, "Somthing Want to worng", 5).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void PrinterBlutoothe() {
        try {
            EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 80f, 32);
            printer.printFormattedText(
                    "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.barcode, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                            "[L]\n" +
                            "[C]<u><font size='big'>" + "Surat Sitilink Ltd.\n TRAVER TICKET" + "</font></u>\n" +
                            "[L]\n" +
                            "[L]<u type='double'>" + "T.No : 789456622452" + "</u>\n" +
                            "[L]<u type='double'> Date " + format.format(new Date()) + "</u>\n" +
                            "[L]<b>FROM </b>[R]GONDA \n ગોંડા \n" +
                            "[L]<b>To </b>[R]KANPUR \n કાનપુર \n" +
                            "[L]<b>Total Fare  </b>[R]RS. 10.00 \n" +
                            "[L]<b>ENTERY Valid With In   </b>[R] 15:16:10 \n" +
                            "[L]<b>Ticket  Validupto   </b>[R]" + format.format(new Date()) + "\n" +
                            "[C]\n" +
                            "[C]<qrcode size='20'>Anil kumar maurya</qrcode>\n" +
                            "[L]\n" +
                            "[C]5 This Ticket is not Refundable \n Customer Care No : 18002330233\n"


            );
        } catch (EscPosConnectionException e) {
            e.printStackTrace();
        } catch (EscPosBarcodeException e) {
            e.printStackTrace();
        } catch (EscPosEncodingException e) {
            e.printStackTrace();
        } catch (EscPosParserException e) {
            e.printStackTrace();
        }

    }


}