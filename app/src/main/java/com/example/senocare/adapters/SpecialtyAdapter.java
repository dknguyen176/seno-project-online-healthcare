package com.example.senocare.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.senocare.R;
import com.example.senocare.activity.ShowDoctorList;
import com.example.senocare.model.SpecialtyModel;

import java.util.List;

public class SpecialtyAdapter extends RecyclerView.Adapter < SpecialtyAdapter.ViewHolder > {

    private Context context;
    private List<SpecialtyModel> list;
    private int layoutId;

    public SpecialtyAdapter(Context context, List<SpecialtyModel> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.specImg);
        String name = list.get(position).getName();

        holder.specName.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDoctorList.class);
                intent.putExtra("specialty", name);
                intent.putExtra("tags", "");
                if (name.compareToIgnoreCase("all") != 0) intent.putExtra("Specialty", name);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView specImg;
        TextView specName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            specImg = itemView.findViewById(R.id.spec_img);
            specName = itemView.findViewById(R.id.spec_name);

        }
    }


}
