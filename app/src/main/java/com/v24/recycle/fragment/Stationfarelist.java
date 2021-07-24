package com.v24.recycle.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.v24.recycle.Adopter.StationfareAdopter;
import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.Modelclass.stationfarelist;
import com.v24.recycle.R;
import com.v24.recycle.dbhelper.Databasehelper;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Stationfarelist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stationfarelist extends Fragment implements AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StationfareAdopter adopter;
    private Databasehelper dbhalper;
    Stationlist stationlist;
    ExtendedFloatingActionButton floatingActionButton;
    ArrayList<stationfarelist> array_list;
    private SearchableSpinner spinnersearch;
    private ArrayList arrayListauto;
    private ArrayList<Stationlist> stationlists;
    private ArrayAdapter arrayAdapter;
    private int id = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Stationfarelist() {
        // Required empty public constructor
    }


    public static Stationfarelist newInstance() {

        return new Stationfarelist();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stationfarelist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbhalper = new Databasehelper(getActivity());
        array_list = new ArrayList<>();
        arrayListauto = new ArrayList();
        spinnersearch = view.findViewById(R.id.autosearch);
        spinnersearch.setOnItemSelectedListener(this);
        recyclerView = view.findViewById(R.id.recyclerView);
        floatingActionButton = view.findViewById(R.id.fab);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, Addfarelist.newInstance());

                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
        getlistofstation();
    }

    private void getAllstationfarelist(int id) {

        array_list.clear();
        array_list = dbhalper.Stationfarelist(id);
        if (array_list.size() > 0) {
            adopter = new StationfareAdopter(getActivity(), array_list, this);
            recyclerView.setAdapter(adopter);
            adopter.notifyDataSetChanged();
        } else {
            Toasty.warning(getContext(), "Data Not found", 10).show();
        }
    }

    private void getlistofstation() {

        arrayListauto.clear();

        arrayListauto.add("Select To Station");
        stationlists = dbhalper.getAllCotacts();

        if (stationlists.size() > 0) {
            for (int i = 0; i <= stationlists.size() - 1; i++) {
                arrayListauto.add(stationlists.get(i).getName());
            }
            arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrayListauto);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnersearch.setAdapter(arrayAdapter);//setting the adapter data into the AutoCompleteTextView

        } else {
            Toasty.error(getContext(), "Add A Station Name ", 10).show();

        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            getAllstationfarelist(position);
        } else {
            getAllstationfarelist(position);

           // Toasty.error(getContext(), "Select An Other Station", 10).show();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}