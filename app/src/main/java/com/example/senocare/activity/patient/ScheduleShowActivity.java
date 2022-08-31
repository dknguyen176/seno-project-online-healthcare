package com.example.senocare.activity.patient;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.adapters.AppointmentAdapter;
import com.example.senocare.helper.SenoDB;

public class ScheduleShowActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView appointmentRecyclerView;
    AppointmentAdapter appointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_show);

        createToolbar();

        createAppointmentReviewRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createAppointmentReviewRecyclerView() {
        appointmentRecyclerView = findViewById(R.id.app_rec);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(com.example.senocare.activity.patient.ScheduleShowActivity.this, LinearLayoutManager.VERTICAL, false));

        appointmentAdapter = new AppointmentAdapter(
                ScheduleShowActivity.this,
                SenoDB.getScheduleList(),
                R.layout.appointment_patient
        );

        appointmentRecyclerView.setAdapter(appointmentAdapter);
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());

        TextView title = findViewById(R.id.title);
        title.setText("All Appointments");
    }
}