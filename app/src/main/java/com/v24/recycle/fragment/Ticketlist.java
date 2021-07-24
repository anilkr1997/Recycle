package com.v24.recycle.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.v24.recycle.Adopter.StationListAdopter;
import com.v24.recycle.Adopter.TicketlistAdopter;
import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.Modelclass.Ticket_list;
import com.v24.recycle.R;
import com.v24.recycle.dbhelper.Databasehelper;
import com.v24.recycle.uttil.Utill;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class Ticketlist extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TicketlistAdopter adopter;
    private Databasehelper dbhalper;
    Ticket_list ticket_list;

    ArrayList<Ticket_list> array_list;
    private Utill utill=new Utill(getActivity());

    public Ticketlist() {
        // Required empty public constructor
    }


    public static Ticketlist newInstance() {


        return new Ticketlist();
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
        return inflater.inflate(R.layout.fragment_ticketlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbhalper = new Databasehelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getAllticketlist();
    }

    private void getAllticketlist() {

        array_list = dbhalper.getAllTicket();
        Log.e("arraylist", String.valueOf(array_list.size()));
        if (array_list.size() > 0) {
            adopter = new TicketlistAdopter(getActivity(), utill,array_list, this);
            recyclerView.setAdapter(adopter);
            adopter.notifyDataSetChanged();
        } else {
            Toasty.warning(getContext(), "Data Not found", 10).show();

        }
    }
}