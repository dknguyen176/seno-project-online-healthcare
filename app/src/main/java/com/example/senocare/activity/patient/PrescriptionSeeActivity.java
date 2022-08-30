package com.example.senocare.activity.patient;

import static com.example.senocare.helper.SenoDB.getPrescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.adapters.DrugSeeAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Prescription;

import org.bson.types.ObjectId;

public class PrescriptionSeeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_see);

        createView();

        createToolbar();
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

    private void createView() {
        Intent intent = getIntent();

        String _idString = intent.getStringExtra("_id");
        ObjectId prescriptionID = new ObjectId(_idString);
        Prescription prescription = SenoDB.getPrescription(prescriptionID);
        Doctor doctor = SenoDB.getDoctorByEmail(prescription.getDoctor());
        Patient patient = SenoDB.getPatientByEmail(prescription.getPatient());

        ((TextView) findViewById(R.id.doc_name)).setText("Dr. " + doctor.getName());
        ((TextView) findViewById(R.id.doc_loc)).setText(doctor.getLoc());

        ((TextView) findViewById(R.id.pat_name)).setText(patient.getName());
        ((TextView) findViewById(R.id.pat_birth)).setText(TimeConverter.toString(patient.getBirth(), "dd/MM/yyyy"));
        ((TextView) findViewById(R.id.pat_sex)).setText(patient.getSex());
        ((TextView) findViewById(R.id.pat_address)).setText(patient.getAddress());
        ((TextView) findViewById(R.id.pat_phone)).setText(patient.getPhone());

        ((TextView) findViewById(R.id.pres_date)).setText(TimeConverter.toString(prescription.getTime(), "dd/MM/yyyy"));
        ((TextView) findViewById(R.id.pres_note)).setText(prescription.getNote());
        ((TextView) findViewById(R.id.diagnostic)).setText(prescription.getDiagnostic());
        ((TextView) findViewById(R.id.doc_sig)).setText(doctor.getName());

        RecyclerView drugRecyclerView = findViewById(R.id.drug_rec);
        drugRecyclerView.setLayoutManager(new LinearLayoutManager(PrescriptionSeeActivity.this, LinearLayoutManager.VERTICAL, false));
        DrugSeeAdapter drugSeeAdapter = new DrugSeeAdapter(PrescriptionSeeActivity.this, prescription.getDrugs(), R.layout.drug_see);
        drugRecyclerView.setAdapter(drugSeeAdapter);
    }
}