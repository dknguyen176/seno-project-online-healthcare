package com.example.senocare.adapters;

import static androidx.core.content.ContextCompat.getColor;
import static com.example.senocare.helper.SenoDB.IS_PATIENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;
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

        holder.date.setText("At " + schedule.getTime());
        if (IS_PATIENT) {
            String email = schedule.getDoctor();
            String name = SenoDB.getNameByEmail(email);
            String status = schedule.getStatus();

            holder.name.setText("Meeting with Dr. " + name);
            if (holder.status != null) {
                holder.status.setText(status);
                if (status.equals("Denied")) {
                    holder.status.setTextColor(getColor(context, R.color.red));
                } else if (status.equals("Accepted")) {
                    holder.status.setTextColor(getColor(context, R.color.green_light));
                }
            }
        } else {
            String email = schedule.getPatient();
            String name = SenoDB.getNameByEmail(email);

            holder.name.setText("Meeting with " + name);
        }

        if (holder.deny_btn != null) {
            holder.deny_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SenoDB.modifySchedule(schedule.get_id(), "Denied");
                }
            });
        }

        if (holder.accept_btn != null) {
            holder.accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SenoDB.modifySchedule(schedule.get_id(), "Accepted");
                }
            });
        }

        if (holder.cancel_btn != null) {
            holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SenoDB.removeSchedule(schedule.get_id());
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        Button cancel_btn, accept_btn, deny_btn;
        TextView name, date, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.profile_pic);
            cancel_btn = itemView.findViewById(R.id.cancel_button);
            accept_btn = itemView.findViewById(R.id.accept_button);
            deny_btn = itemView.findViewById(R.id.deny_button);

            name = itemView.findViewById(R.id.app_name);
            date = itemView.findViewById(R.id.app_date);
            status = itemView.findViewById(R.id.app_status);
        }
    }
}
