package com.example.senocare.activity.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.senocare.R;
import com.example.senocare.adapters.AppointmentAdapter;

public class ScheduleReviewActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView appointmentRecyclerView;
    public static AppointmentAdapter appointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_review);

        //Intent intent = getIntent();

        createToolbar();

        createAppointmentReviewRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createAppointmentReviewRecyclerView() {
        appointmentRecyclerView = findViewById(R.id.doc_rec);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(ScheduleReviewActivity.this, LinearLayoutManager.VERTICAL, false));

        // TODO: SenoDB.getPendingScheduleList() lay nhung Schedule co status la Waiting/Pending gi do
        // appointmentAdapter = new AppointmentAdapter(ScheduleUpcomingActivity.this, SenoDB.getScheduleList(), R.layout.appointment_review_doctor);

        appointmentRecyclerView.setAdapter(appointmentAdapter);
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}