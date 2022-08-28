package com.example.senocare.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.model.Schedule;

import io.realm.OrderedRealmCollection;

public class AppointmentAdapter extends RealmRecyclerViewAdapter<Schedule, AppointmentAdapter.ViewHolder> {
    String TAG = "REALM_RECYCLER_ADAPTER";

    private Context context;
    private int layoutId;

    public AppointmentAdapter(Context context, OrderedRealmCollection<Schedule> data, int layoutId) {
        super(data, true);
        this.context = context;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Creating view holder");
        return new AppointmentAdapter.ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(layoutId,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        Schedule schedule = getItem(position);

        if (holder.status == null) holder.name.setText("Meeting with " + schedule.getPatient());
        else holder.name.setText("Meeting with " + schedule.getDoctor());
        holder.date.setText("At " + schedule.getTime());

        holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: doctor cancel this appointment
            }
        });

        if (holder.accept_btn != null)
            holder.accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: doctor accept this appointment
                }
            });

        if (holder.status != null)
            holder.status.setText(schedule.getStatus());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img, cancel_btn, accept_btn;;
        TextView name, date, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.profile_pic);
            cancel_btn = itemView.findViewById(R.id.cancel_button);
            accept_btn = itemView.findViewById(R.id.accept_button);

            name = itemView.findViewById(R.id.app_name);
            date = itemView.findViewById(R.id.app_date);
            status = itemView.findViewById(R.id.app_status);
        }
    }
}
