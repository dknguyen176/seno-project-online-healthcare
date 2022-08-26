package com.example.senocare.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;

public class DoctorAdapter extends RealmRecyclerViewAdapter<Doctor, DoctorAdapter.ViewHolder>  {
    String TAG = "REALM_RECYCLER_ADAPTER";

    private Context context;
    //private List<Doctor> list;
    private int layoutId;

    /*public DoctorAdapter(Context context, List<Doctor> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }*/

    public DoctorAdapter(Context context, OrderedRealmCollection<Doctor> data, int layoutId) {
        super(data, true);
        this.context = context;
        this.layoutId = layoutId;
        Log.i(TAG, "Created RealmRecyclerViewAdapter for "
                + getData().size() + " items.");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Creating view holder");
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Doctor doctor = getItem(position);
        Log.i(TAG, "Binding view holder: " + doctor.getName());

        // Glide.with(context).load(productsModel.getImg_url()).into(holder.img);
        holder.name.setText(doctor.getName());
        holder.spec.setText(doctor.getSpec());
        holder.exper.setText(doctor.getExper().toString());
        // holder.rating.setText(doctor.getRating());

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Doctor doctor = getItem(getAdapterPosition());

                    Intent intent = new Intent(context, DetailedDoctor.class);
                    intent.putExtra("id", doctor.get_id());

                    context.startActivity(intent);
                }
            });
        }
    }
}

