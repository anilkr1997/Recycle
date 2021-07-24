package com.v24.recycle.Adopter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.Modelclass.stationfarelist;
import com.v24.recycle.R;
import com.v24.recycle.fragment.Stationfarelist;

import java.util.ArrayList;

public class StationfareAdopter extends RecyclerView.Adapter<StationfareAdopter.MyViewHolder> {
    ArrayList<stationfarelist> arrayList;
    private FragmentActivity context;
    public StationfareAdopter(FragmentActivity activity, ArrayList<stationfarelist> array_list, Stationfarelist stationfarelist) {
        this.arrayList = array_list;
        this.context = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationbtfarelist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_station_name_from.setText(arrayList.get(position).getS_From());
        holder.tv_station_name_to.setText(arrayList.get(position).getS_To());
        holder.tv_fare.setText(arrayList.get(position).getS_Fare());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_station_name_to,tv_station_name_from, tv_fare;
        AppCompatImageView img_delet, img_update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_station_name_from = itemView.findViewById(R.id.tv_Station_name_from);
            tv_station_name_to = itemView.findViewById(R.id.tv_Station_name_to);
            tv_fare = itemView.findViewById(R.id.tv_total_fare);
            img_delet = itemView.findViewById(R.id.img_delet);
            img_update = itemView.findViewById(R.id.img_update);

        }

    }
}
