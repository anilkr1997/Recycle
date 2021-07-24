package com.v24.recycle.Adopter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.v24.recycle.Modelclass.Ticket_list;
import com.v24.recycle.R;
import com.v24.recycle.fragment.Ticketlist;
import com.v24.recycle.uttil.Utill;

import java.util.ArrayList;

public class TicketlistAdopter extends RecyclerView.Adapter<TicketlistAdopter.MyViewHolder> {
    StationListAdopter.deletupdate deletupda;
    ArrayList<Ticket_list> arrayList;
    private FragmentActivity context;
    Utill utill;

    public TicketlistAdopter(FragmentActivity activity, Utill utill, ArrayList<Ticket_list> array_list, Ticketlist ticketlist) {
        this.arrayList = array_list;
        this.context = activity;
        this.utill=utill;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticketlist, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_S_From.setText("To  :" + arrayList.get(position).getKEY_TO());
        holder.tv_S_to.setText("FROM : " + arrayList.get(position).getKEY_FROM());
        holder.tv_serialNo.setText("T.S.No: " + arrayList.get(position).getKEY_S_NO());
        holder.tv_total_fare.setText("Total Fare : Rs." + arrayList.get(position).getKEY_TL_FARE() + ".00");
        holder.tv_validation.setText("Entry Valid Within :" + arrayList.get(position).getKEY_VALIDE());
        holder.tv_valide_up.setText("Ticket Valid Upto :  " + arrayList.get(position).getKEY_VALIDE_UPTO());
        holder.Tv_Date.setText("DATE :  " + arrayList.get(position).getKEY_DATE());

        barcode(holder, arrayList.get(position).getKEY_BARCODE());
    }

    private void barcode(MyViewHolder holder, String key_barcode) {
        Bitmap bitmap=utill.barcodegeneration(key_barcode);
        if(bitmap!=null){
            holder.imageView .setImageBitmap(utill.barcodegeneration(key_barcode));
        }else {
            holder.imageView.setImageResource(R.drawable.barcode);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_serialNo, Tv_Date, tv_S_to, tv_S_From, tv_total_fare, tv_validation, tv_valide_up, tv_hader, Tv_notrefantabel;
        AppCompatImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_serialNo = itemView.findViewById(R.id.tv_S_No);
            Tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_S_to = itemView.findViewById(R.id.tv_Station_name2);
            tv_S_From = itemView.findViewById(R.id.tv_Station_name);
            tv_total_fare = itemView.findViewById(R.id.tv_total_fare);
            tv_validation = itemView.findViewById(R.id.tv_validate_time);
            tv_valide_up = itemView.findViewById(R.id.tv_valideteupto);
            imageView = itemView.findViewById(R.id.barcodeimage);
        }
    }
}
