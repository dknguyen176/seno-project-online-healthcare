package com.example.senocare.fragments.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.activity.doctor.PrescriptionMakeActivity;
import com.example.senocare.activity.doctor.ScheduleReviewActivity;
import com.example.senocare.activity.doctor.ScheduleUpcomingActivity;
import com.example.senocare.adapters.AppointmentAdapter;
import com.example.senocare.helper.SenoDB;

public class DoctorHomeFragment extends Fragment {

    private AppointmentAdapter appointmentAdapter;

    RecyclerView appointmentRecyclerView;

    public DoctorHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_doctor, container, false);

        CardView reviewAppointment = root.findViewById(R.id.review_appointment_btn);
        reviewAppointment.setOnClickListener(v -> startActivity(new Intent(getContext(), ScheduleReviewActivity.class)));


        CardView makePrescription = root.findViewById(R.id.make_prescription_btn);
        makePrescription.setOnClickListener(v -> startActivity(new Intent(getContext(), PrescriptionMakeActivity.class)));

        TextView app_btn = root.findViewById(R.id.app_see_all);
        app_btn.setOnClickListener(v -> startActivity(new Intent(getContext(), ScheduleUpcomingActivity.class)));

        createAppointmentRecyclerView(root);

        return root;
    }

    private void createAppointmentRecyclerView(View root) {
        appointmentRecyclerView = root.findViewById(R.id.app_rec);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new AppointmentAdapter(getContext(), SenoDB.getUpcomingScheduleList(3), R.layout.appointment_upcoming_doctor);
        appointmentRecyclerView.setAdapter(appointmentAdapter);
    }
}