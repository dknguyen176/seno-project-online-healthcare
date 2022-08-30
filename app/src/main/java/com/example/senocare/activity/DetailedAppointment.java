package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;
import static com.example.senocare.helper.SenoDB.getSchedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Prescription;
import com.example.senocare.model.Schedule;

import org.bson.types.ObjectId;

public class DetailedAppointment extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_appointment);

        createToolbar();

        createAppointmentView();
    }

    private void createAppointmentView() {
        Intent intent = getIntent();
        String _idString = intent.getStringExtra("_id");
        ObjectId scheduleID = new ObjectId(_idString);
        Schedule schedule = getSchedule(scheduleID);

        ((TextView) findViewById(R.id.doc_email)).setText(schedule.getDoctor());
        ((TextView) findViewById(R.id.pat_email)).setText(schedule.getPatient());
        ((TextView) findViewById(R.id.note)).setText(schedule.getNote());
        ((TextView) findViewById(R.id.app_date)).setText(schedule.getTime());
        ((TextView) findViewById(R.id.app_status)).setText(schedule.getStatus());

        Button deny_btn = findViewById(R.id.deny_button);
        Button accept_btn= findViewById(R.id.accept_button);
        Button cancel_btn = findViewById(R.id.cancel_button);

        if (schedule.getStatus().compareTo("Denied") == 0){
            accept_btn.setVisibility(View.INVISIBLE);
            deny_btn.setVisibility(View.INVISIBLE);
            cancel_btn.setVisibility(View.INVISIBLE);
        }
        else if (schedule.getStatus().compareTo("Pending") == 0 && !IS_PATIENT){
            cancel_btn.setVisibility(View.INVISIBLE);
            accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SenoDB.modifySchedule(schedule.get_id(), "Accepted");
                    finish();
                }
            });
            deny_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SenoDB.modifySchedule(schedule.get_id(), "Denied");
                    finish();
                }
            });
        }
        else{
            accept_btn.setVisibility(View.INVISIBLE);
            deny_btn.setVisibility(View.INVISIBLE);
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (IS_PATIENT) SenoDB.removeSchedule(schedule.get_id());
                    else SenoDB.modifySchedule(schedule.get_id(), "Denied");
                    finish();
                }
            });
        }
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

        TextView title = findViewById(R.id.title);
        title.setText("Appointment's Detail");
    }
}