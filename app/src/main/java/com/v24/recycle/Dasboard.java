package com.v24.recycle;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.v24.recycle.Adopter.recycleviewAdopter;
import com.v24.recycle.Modelclass.Ticket_list;
import com.v24.recycle.Modelclass.dasboardlist;
import com.v24.recycle.Modelclass.filtermodelclass;
import com.v24.recycle.dbhelper.Databasehelper;
import com.v24.recycle.fragment.AddUrlList;
import com.v24.recycle.fragment.Addfarelist;
import com.v24.recycle.fragment.StationList;
import com.v24.recycle.fragment.Stationfarelist;
import com.v24.recycle.fragment.Ticketlist;
import com.v24.recycle.uttil.Utill;

import java.util.ArrayList;
import java.util.List;

public class Dasboard extends AppCompatActivity implements recycleviewAdopter.clicklisner {
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private recycleviewAdopter adopter;
    private dasboardlist dasboardlist;
    private Utill utill;
    List<dasboardlist> dasboardlists = new ArrayList<>();
    private ArrayList<Ticket_list> totlalcolection = new ArrayList<>();
    private ArrayList<filtermodelclass> todaycollection = new ArrayList<>();

    private int a = 00;
    private int b = 00;
    Databasehelper db;
    String[] name = {"Add/Station List", "Ticket List", "Add URL", "Station Fare List"};
    int[] image = {R.drawable.ic_baseline_view_list_24, R.drawable.ic_baseline_format_list_bulleted_24, R.drawable.ic_baseline_web_24, R.drawable.ic_baseline_post_add_24};
    AppCompatTextView textView, todaycollectiontv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        db = new Databasehelper(this);
        utill = new Utill(this);
        textView = findViewById(R.id.totoalcolection);
        todaycollectiontv = findViewById(R.id.todaycollection);
        totlalcolection = db.getAllTicket();
        if (totlalcolection.size() > 0) {
            for (int i = 0; i <= totlalcolection.size() - 1; i++) {
                a = a + Integer.parseInt(totlalcolection.get(i).getKEY_TL_FARE());

            }
        }
        textView.setText("Rs. " + a + ".00");


        todaycollection = db.getAllFilter(utill.filterdate());

        if (todaycollection.size() > 0) {
            Log.e("colection", "today");
            for (int i = 0; i <= todaycollection.size() - 1; i++) {
                b = b + Integer.parseInt(todaycollection.get(i).getKEY_TL_FARE());

            }
        }
        todaycollectiontv.setText("Rs. " + b + ".00");
        Log.e("count", b + "" + new Gson().toJson(todaycollection));


        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        for (int i = 0; i <= name.length - 1; i++) {
            dasboardlist = new dasboardlist(i, image[i], name[i]);
            dasboardlists.add(dasboardlist);
        }


        adopter = new recycleviewAdopter(this, dasboardlists);
        recyclerView.setAdapter(adopter);

    }

    @Override
    public void onClick(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (position) {
            case 0:

                transaction.add(R.id.container, StationList.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 1:
                transaction.add(R.id.container, Ticketlist.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();

                break;
            case 2:
                // Toasty.warning(this, "Under working this module.......", 10).show();
                transaction.add(R.id.container, AddUrlList.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 3:
                transaction.add(R.id.container, Stationfarelist.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();

                break;
            default:
                throw new IllegalStateException("Unexpected value: ");
        }


    }
}