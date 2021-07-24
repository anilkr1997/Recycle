package com.v24.recycle.Adopter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.v24.recycle.Dasboard;
import com.v24.recycle.Modelclass.dasboardlist;
import com.v24.recycle.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class recycleviewAdopter extends RecyclerView.Adapter<recycleviewAdopter.MyViewHolder> {
    List<dasboardlist> dasboardlists;
    private Activity activity;
    clicklisner clicklisner;
    public recycleviewAdopter(Dasboard dasboard, List<dasboardlist> dasboardlist) {
        this.activity=dasboard;
        this.dasboardlists=dasboardlist;
        this.clicklisner=dasboard;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewlayout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(dasboardlists.get(position).getImageurl());
        holder.textView.setText(dasboardlists.get(position).getDasbordname());
holder.imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
clicklisner.onClick(position);
    }
});

    }

    @Override
    public int getItemCount() {
        return dasboardlists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.dbimage);
            textView=itemView.findViewById(R.id.dbtext);
        }
    }
  public   interface clicklisner{
        void onClick(int position);

    }
}
