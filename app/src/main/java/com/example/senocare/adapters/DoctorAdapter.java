package com.example.senocare.adapters;

import static com.example.senocare.helper.ViewSupporter.putByteArrayToImageView;

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

import com.example.senocare.R;
import com.example.senocare.activity.DetailedDoctor;
import com.example.senocare.model.Doctor;

import io.realm.OrderedRealmCollection;

public class DoctorAdapter extends RealmRecyclerViewAdapter<Doctor, DoctorAdapter.ViewHolder>  {
    String TAG = "REALM_RECYCLER_ADAPTER";

    private final Context context;
    private final int layoutId;

    public DoctorAdapter(Context context, OrderedRealmCollection<Doctor> data, int layoutId) {
        super(data, true);
        this.context = context;
        this.layoutId = layoutId;
        Log.i(TAG, "Created DoctorAdapter for " + getData().size() + " items.");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Creating view holder");
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(layoutId, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor = getItem(position);
        Log.i(TAG, "Binding view holder: " + doctor.getName());

        holder.name.setText("Dr. " + doctor.getLast());
        holder.spec.setText(doctor.getSpec());
        holder.exper.setText(doctor.getExper() + " years");
        holder.rating.setText(String.valueOf(doctor.getRating()));
        putByteArrayToImageView(doctor.getImg(), holder.img, doctor.getSex());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedDoctor.class);
            intent.putExtra("_id", doctor.get_id());

            context.startActivity(intent);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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

