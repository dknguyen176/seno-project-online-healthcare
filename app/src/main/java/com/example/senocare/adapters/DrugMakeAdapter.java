package com.example.senocare.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.model.Drugs;

import java.util.ArrayList;


public class DrugMakeAdapter extends RecyclerView.Adapter <DrugMakeAdapter.ViewHolder>  {
    private final String TAG = "DRUG_ADAPTER";

    private Context context;
    private ArrayList<Drugs> list;

    public DrugMakeAdapter(Context context, ArrayList<Drugs> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.drug_make,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.quantity.setText(String.valueOf(list.get(position).getQuantity()));
        holder.note.setText(list.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public EditText name, quantity, note;
        Button remove_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            note = itemView.findViewById(R.id.drug_note);
            remove_btn = itemView.findViewById(R.id.remove_btn);

            remove_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            });

            name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Log.v(TAG, "Focus = true");
                    } else {
                        Log.v(TAG, "Focus = false");

                        int position = getAdapterPosition();
                        list.get(position).setName(((EditText) v).getText().toString());
                    }
                }
            });

            quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Log.v(TAG, "Focus = true");
                    } else {
                        Log.v(TAG, "Focus = false");

                        int position = getAdapterPosition();
                        list.get(position).setQuantity(Integer.parseInt((((EditText) v).getText().toString())));
                    }
                }
            });

            note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Log.v(TAG, "Focus = true");
                    } else {
                        Log.v(TAG, "Focus = false");

                        int position = getAdapterPosition();
                        list.get(position).setNote(((EditText) v).getText().toString());
                    }
                }
            });
        }
    }
}