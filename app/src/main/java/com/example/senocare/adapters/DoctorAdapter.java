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
import com.example.senocare.activity.DetailedDoctor;
import com.example.senocare.model.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter < DoctorAdapter.ViewHolder >  {

    private Context context;
    private List<Doctor> list;
    private int layoutId;

    public DoctorAdapter(Context context, List<Doctor> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Doctor doctor = list.get(position);

        // Glide.with(context).load(productsModel.getImg_url()).into(holder.img);
        holder.name.setText(doctor.getName());
        holder.spec.setText(doctor.getSpec());
        holder.exper.setText(doctor.getExper());
        // holder.rating.setText(doctor.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedDoctor.class);
                intent.putExtra("id", doctor.get_id());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, spec, exper, rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            img = itemView.findViewById(R.id.doc_img);
            name = itemView.findViewById(R.id.doc_name);
            spec = itemView.findViewById(R.id.doc_spec);
            exper = itemView.findViewById(R.id.doc_exper);
            rating = itemView.findViewById(R.id.doc_rating);

        }
    }
}

