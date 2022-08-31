package com.example.senocare.activity.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.senocare.R;
import com.example.senocare.adapters.PrescriptionAdapter;
import com.example.senocare.helper.SenoDB;

public class PrescriptionListActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView prescriptionRecyclerView;
    PrescriptionAdapter prescriptionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);

        createToolbar();

        prescriptionRecyclerView = findViewById(R.id.pres_rec);
        prescriptionRecyclerView.setLayoutManager(new LinearLayoutManager(PrescriptionListActivity.this, LinearLayoutManager.VERTICAL, false));
        prescriptionAdapter = new PrescriptionAdapter(PrescriptionListActivity.this, SenoDB.getPrescriptionList(), R.layout.prescription_thumbnail);
        prescriptionRecyclerView.setAdapter(prescriptionAdapter);
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> finish());
    }
}