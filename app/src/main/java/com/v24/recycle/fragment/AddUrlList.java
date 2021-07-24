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
import androidx.fragment.app.Fragment;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.R;
import com.v24.recycle.dbhelper.Databasehelper;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddUrlList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUrlList extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private AppCompatEditText editText;
    private AppCompatButton button;
    private Databasehelper db;
    private SearchableSpinner spinnersearch;
    private ArrayList arrayListauto;
    private ArrayList<Stationlist> stationlists;
    private ArrayAdapter arrayAdapter;
    private static int pos = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddUrlList() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddUrlList newInstance() {

        return new AddUrlList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_url_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new Databasehelper(getContext());
        arrayListauto = new ArrayList();
        spinnersearch = view.findViewById(R.id.autosearch);
        spinnersearch.setOnItemSelectedListener(this);
        editText = view.findViewById(R.id.tv_url_name);
        button = view.findViewById(R.id.btn_Submit_url);
        button.setOnClickListener(this::onClick);
        getlistofstation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit_url:
                if (!editText.getText().toString().isEmpty() && !editText.getText().toString().equals(" ") && pos != 0) {
                    db.insertuel(editText.getText().toString(), pos);
                    if (true) {
                        Toasty.success(getContext(), "Add SucessFully", 5).show();
                        editText.setText("");

                    } else
                        Toasty.error(getContext(), "Somthing Want to worng", 5).show();

                } else {
                    Toasty.error(getContext(), "Enter URL or Station id", 5).show();
                    editText.setError("");

                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: ");
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            pos = position;

        } else {
            Toasty.error(getContext(), "Select  Station id   " + position, 10).show();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getlistofstation() {

        arrayListauto.clear();

        arrayListauto.add("Select To Station");
        stationlists = db.getAllCotacts();

        if (stationlists.size() > 0) {
            for (int i = 0; i <= stationlists.size() - 1; i++) {
                arrayListauto.add(stationlists.get(i).getName());
            }
            arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrayListauto);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnersearch.setAdapter(arrayAdapter);

        } else {
            Toasty.error(getContext(), "Add A Station Name ", 10).show();

        }


    }

}