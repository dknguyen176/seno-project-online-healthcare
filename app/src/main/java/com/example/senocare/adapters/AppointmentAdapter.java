package com.example.senocare.adapters;

import static androidx.core.content.ContextCompat.getColor;
import static com.example.senocare.helper.SenoDB.IS_PATIENT;
import static com.example.senocare.helper.SenoDB.getDoctorByEmail;
import static com.example.senocare.helper.SenoDB.getPatientByEmail;
import static com.example.senocare.helper.ViewSupporter.putByteArrayToImageView;

import android.content.Context;
import android.content.Intent;
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
import com.example.senocare.activity.DetailedAppointment;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Schedule;

import io.realm.OrderedRealmCollection;

public class AppointmentAdapter extends RealmRecyclerViewAdapter<Schedule, AppointmentAdapter.ViewHolder> {
    String TAG = "REALM_RECYCLER_ADAPTER";

    private final Context context;
    private final int layoutId;

    public AppointmentAdapter(Context context, OrderedRealmCollection<Schedule> data, int layoutId) {
        super(data, true);
        this.context = context;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Creating view holder");
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(layoutId, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        Schedule schedule = getItem(position);

        String time_text = "At " + TimeConverter.toString(schedule.getTime(), "dd/MM/yyyy");
        holder.date.setText(time_text);
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
            Doctor doctor = getDoctorByEmail(schedule.getDoctor());
            putByteArrayToImageView(doctor.getImg(), holder.img, doctor.getSex());
        } else {
            String email = schedule.getPatient();
            String name = SenoDB.getNameByEmail(email);

            holder.name.setText("Meeting with " + name);
            Patient patient = getPatientByEmail(schedule.getPatient());
            putByteArrayToImageView(patient.getImg(), holder.img, patient.getSex());
        }

        if (holder.deny_btn != null) {
            holder.deny_btn.setOnClickListener(v -> SenoDB.modifySchedule(schedule.get_id(), "Denied"));
        }

        if (holder.accept_btn != null) {
            holder.accept_btn.setOnClickListener(v -> SenoDB.modifySchedule(schedule.get_id(), "Accepted"));
        }

        if (holder.cancel_btn != null) {
            holder.cancel_btn.setOnClickListener(v -> {
                if (IS_PATIENT) SenoDB.removeSchedule(schedule.get_id());
                else SenoDB.modifySchedule(schedule.get_id(), "Denied");
            });
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedAppointment.class);
            intent.putExtra("_id", schedule.get_id().toString());

            context.startActivity(intent);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
