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

import com.example.senocare.R;
import com.example.senocare.activity.DetailedDoctor;
import com.example.senocare.activity.patient.PrescriptionSeeActivity;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Prescription;

import io.realm.OrderedRealmCollection;

public class PrescriptionAdapter extends RealmRecyclerViewAdapter<Prescription, PrescriptionAdapter.ViewHolder>  {
    String TAG = "REALM_RECYCLER_ADAPTER";

    private Context context;
    private int layoutId;

    public PrescriptionAdapter(Context context, OrderedRealmCollection<Prescription> data, int layoutId) {
        super(data, true);
        this.context = context;
        this.layoutId = layoutId;
        Log.i(TAG, "Created PrescriptionAdapter for " + getData().size() + " items.");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Creating view holder");
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(layoutId,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescription prescription = getItem(position);
        Log.i(TAG, "Binding view holder: " + prescription.getDoctor());

        holder.diagnostic.setText(prescription.getDiagnostic());
        holder.docname.setText(prescription.getDoctor());
        holder.date.setText(prescription.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrescriptionSeeActivity.class);
                intent.putExtra("_id", prescription.get_id());

                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView diagnostic, docname, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            diagnostic = itemView.findViewById(R.id.diagnostic);
            docname = itemView.findViewById(R.id.doc_name);
            date = itemView.findViewById(R.id.date);
        }
    }
}


