package com.v24.recycle.fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.R;
import com.v24.recycle.dbhelper.Databasehelper;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AddStation extends Fragment implements View.OnClickListener {
    AppCompatButton submit;
    TextView Tv_stationname, tv_stationnamein_gujrati, tv_fare;
    private Databasehelper dbhalper;
    ArrayList<Stationlist> array_list;
    public AddStation() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance() {

        return new AddStation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_station, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbhalper = new Databasehelper(getActivity());
        submit = view.findViewById(R.id.btn_Submit);
        submit.setOnClickListener(this::onClick);
        tv_fare = view.findViewById(R.id.fare);


//        TextView text_view = new TextView(getActivity());
//        Typeface font = Typeface.cr(getF(), "Shruti.TTF");
//        text_view.setTypeface(font);
        tv_stationnamein_gujrati = view.findViewById(R.id.tv_Station_name_gujrati);
        Tv_stationname = view.findViewById(R.id.tv_Station_name);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit:

                if (tv_stationnamein_gujrati.getText().toString().equals("") && Tv_stationname.getText().toString().equals("")) {
                    Toasty.error(getContext(), "Enter Station name ", 5).show();
                    tv_stationnamein_gujrati.setError("");
                    Tv_stationname.setError("");


                } else if (tv_fare.getText().toString().equals("")) {
                    Toasty.error(getContext(), "Enter Fare  ", 5).show();
                    tv_fare.setError("");

                } else {
                    String Station_name = Tv_stationname.getText().toString() + "\n " + tv_stationnamein_gujrati.getText().toString();
                    String Station_Fare = tv_fare.getText().toString();
                    addstation(Station_name, Station_Fare);

                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: ");
        }
    }

    private void addstation(String station_name, String station_fare) {

        dbhalper.Station_insert(station_name, station_fare);
        if (true) {

            Toasty.success(getContext(), "Add SucessFully", 5).show();
            tv_stationnamein_gujrati.setText("");
            tv_fare.setText("");
            Tv_stationname.setText("");

            array_list = dbhalper.getAllCotacts();
        } else
            Toasty.error(getContext(), "Somthing Want to worng", 5).show();


    }
}