package com.v24.recycle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.v24.recycle.Adopter.StationListAdopter;
import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.R;
import com.v24.recycle.dbhelper.Databasehelper;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class StationList extends Fragment implements StationListAdopter.deletupdate {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StationListAdopter adopter;
    private Databasehelper dbhalper;
    Stationlist stationlist;
    ExtendedFloatingActionButton floatingActionButton;
    ArrayList<Stationlist> array_list;

    public StationList() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StationList newInstance() {

        return new StationList();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getAllstationlist();
            adopter.notifyDataSetChanged();
            //Write down your refresh code here, it will call every time user come to this fragment.
            //If you are using listview with custom adapter, just call notifyDataSetChanged().
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_station_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbhalper = new Databasehelper(getActivity());
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
                transaction.replace(R.id.container, AddStation.newInstance());

                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
        getAllstationlist();
    }

    private void getAllstationlist() {
        array_list = dbhalper.getAllCotacts();
        if (array_list.size() > 0) {
            adopter = new StationListAdopter(getActivity(), array_list, this);
            recyclerView.setAdapter(adopter);
            adopter.notifyDataSetChanged();
        } else {
            Toasty.warning(getContext(), "Data Not found", 10).show();
        }
    }

    @Override
    public void updater(int position) {

        Toasty.warning(getContext(), "Somthing Want to worng", 5).show();
    }

    @Override
    public void Delete(int position) {
        dbhalper.deleteStation(Integer.valueOf(array_list.get(position).getId()));
        if (true) {
            Toasty.success(getContext(), "Delete SucessFully", 5).show();
            getAllstationlist();
        } else
            Toasty.error(getContext(), "Somthing Want to worng", 5).show();


    }

    @Override
    public void onResume() {
        super.onResume();
       // getAllstationlist();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
      //  getAllstationlist();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

}