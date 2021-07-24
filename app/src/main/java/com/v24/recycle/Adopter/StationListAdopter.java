package com.v24.recycle.Adopter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.R;
import com.v24.recycle.fragment.StationList;

import java.util.ArrayList;

public class StationListAdopter extends RecyclerView.Adapter<StationListAdopter.ViewMyHolder> {
    deletupdate deletupda;
    ArrayList<Stationlist> arrayList;
    private FragmentActivity context;

    public StationListAdopter(FragmentActivity activity, ArrayList<Stationlist> array_list, StationList stationList) {
        this.arrayList = array_list;
        this.context = activity;
        this.deletupda = (deletupdate) stationList;
    }


    @NonNull
    @Override
    public ViewMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationlist, parent, false);
        return new ViewMyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewMyHolder holder, int position) {
        holder.tv_station_name.setText(arrayList.get(position).getName());
        holder.tv_fare.setText(arrayList.get(position).getFare());
        holder.tv_Station_id.setText(arrayList.get(position).getId());
        holder.img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletupda.updater(position);
            }
        });
        holder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletupda.Delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewMyHolder extends RecyclerView.ViewHolder {
        TextView tv_station_name, tv_fare,tv_Station_id;
        AppCompatImageView img_delet, img_update;

        public ViewMyHolder(@NonNull View itemView) {
            super(itemView);
            tv_station_name = itemView.findViewById(R.id.tv_Station_name);
            tv_fare = itemView.findViewById(R.id.tv_total_fare);
            img_delet = itemView.findViewById(R.id.img_delet);
            img_update = itemView.findViewById(R.id.img_update);
            tv_Station_id = itemView.findViewById(R.id.tv_Station_id);
        }
    }

    public interface deletupdate {
        void updater(int position);

        void Delete(int position);

    }
}
