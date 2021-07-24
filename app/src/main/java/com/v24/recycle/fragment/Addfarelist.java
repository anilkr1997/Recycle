package com.v24.recycle.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.R;
import com.v24.recycle.dbhelper.Databasehelper;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class Addfarelist extends Fragment implements View.OnClickListener {
    AppCompatSpinner sp_from, sp_to;
    AppCompatEditText et_fare;
    AppCompatButton btn_enter;
    int str_from = 0;
    int str_to = 0;
    String str_fare = "";
    private Databasehelper db;
    private ArrayAdapter arrayAdapter;
    private ArrayList<Stationlist> stationlists;
    private ArrayList arrayList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Addfarelist() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Addfarelist newInstance() {
        //  Addfarelist fragment = new Addfarelist();


        return new Addfarelist();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addfarelist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new Databasehelper(getContext());
        arrayList = new ArrayList();
        et_fare = view.findViewById(R.id.fare_btw);
        sp_from = view.findViewById(R.id.tv_Station_from_name);
        sp_to = view.findViewById(R.id.tv_Station_name_to);
        btn_enter = view.findViewById(R.id.btn_addfare);
        btn_enter.setOnClickListener(this::onClick);
        sp_to.setSelection(0);
        sp_from.setSelection(0);
        sp_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (stationlists.size() > 0) {

                    if (position != 0) {
                        str_from = position;
                       // Toasty.success(getContext(), "Id=: " + position, 10).show();


                    } else {

                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (stationlists.size() > 0) {

                    if (position != 0) {
                        str_to = position;
                       // Toasty.success(getContext(), "Id=: " + position, 10).show();


                    } else {

                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getlistofstation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addfare:

                str_fare = et_fare.getText().toString().trim();
                if (!str_fare.equals("") && str_from != 0 && str_to != 0 && str_from != str_to) {
                    String faree = db.Fareget(str_from, str_to);
                    if (faree.equals("00.00")) {
                        boolean result = db.insertfarelist(str_from, str_to, str_fare);
                        if (result) {

                            sp_to.setSelection(0);

                            et_fare.setText("");

                            Toasty.success(getContext(), "Add SucessFully", 5).show();

                        } else
                            Toasty.error(getContext(), "Somthing Want to worng", 5).show();

                    } else {
                        Toasty.error(getContext(), "This is All ready exists", 5).show();

                    }

                    //  db.insertfarelist(str_from,str_to,str_fare);


                } else {
                    Toasty.warning(getContext(), "Somthing Want to worng", 5).show();

                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: ");
        }
    }

    private void getlistofstation() {
        arrayList.clear();
        arrayList.add("Selecte  Station");
        stationlists = db.getAllCotacts();

        if (stationlists.size() > 0) {
            for (int i = 0; i <= stationlists.size() - 1; i++) {
                arrayList.add(stationlists.get(i).getName());
            }
            arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_from.setAdapter(arrayAdapter);
            sp_to.setAdapter(arrayAdapter);

        } else {
            Toasty.warning(getContext(), "Data not found", 5).show();

        }

    }

}