package com.example.senocare.activity.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.senocare.R;
import com.example.senocare.activity.doctor.ScheduleUpcomingActivity;
import com.example.senocare.adapters.AppointmentAdapter;
import com.example.senocare.adapters.PrescriptionAdapter;
import com.example.senocare.helper.SenoDB;

public class PrescriptionListActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView prescriptionRecyclerView;
    public static PrescriptionAdapter prescriptionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);

        createToolbar();

        prescriptionRecyclerView = findViewById(R.id.pres_rec);
        prescriptionRecyclerView.setLayoutManager(new LinearLayoutManager(PrescriptionListActivity.this, LinearLayoutManager.VERTICAL, false));

        // TODO: lay Prescription sort desc theo time
        prescriptionAdapter = new PrescriptionAdapter(PrescriptionListActivity.this, SenoDB.getPrescriptionList(), R.layout.prescription_thumbnail);

        prescriptionRecyclerView.setAdapter(prescriptionAdapter);
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