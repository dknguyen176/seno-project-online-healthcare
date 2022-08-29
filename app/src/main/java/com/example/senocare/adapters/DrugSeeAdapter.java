package com.example.senocare.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.model.Drugs;

import io.realm.OrderedRealmCollection;

public class DrugSeeAdapter extends RealmRecyclerViewAdapter<Drugs, DrugSeeAdapter.ViewHolder>  {
    String TAG = "REALM_RECYCLER_ADAPTER";

    private Context context;
    private int layoutId;

    public DrugSeeAdapter(Context context, OrderedRealmCollection<Drugs> data, int layoutId) {
        super(data, true);
        this.context = context;
        this.layoutId = layoutId;
        Log.i(TAG, "Created DrugsAdapter for " + getData().size() + " items.");
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
        Drugs drugs = getItem(position);
        Log.i(TAG, "Binding view holder: " + drugs.getName());

        holder.name.setText(drugs.getName());
        holder.quantity.setText(String.valueOf(drugs.getQuantity()));
        holder.note.setText(drugs.getNote());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, quantity, note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            note = itemView.findViewById(R.id.note);
        }
    }
}