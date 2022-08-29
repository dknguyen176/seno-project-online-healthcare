package com.example.senocare.activity.patient;

import static com.example.senocare.helper.SenoDB.getPrescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Prescription;

import org.bson.types.ObjectId;

public class PrescriptionSeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_see);

        createView();
    }

    private void createView() {
        Intent intent = getIntent();

        // TODO: lam may cai nay

        String _idString = intent.getStringExtra("_id");
        ObjectId prescriptionID = new ObjectId(_idString);
        Prescription prescription = SenoDB.getPrescription(prescriptionID);
        Doctor doctor = SenoDB.getDoctorByEmail(prescription.getDoctor());
        Patient patient = SenoDB.getPatientByEmail(prescription.getPatient());

        ((TextView) findViewById(R.id.doc_name)).setText(doctor.getName());
        ((TextView) findViewById(R.id.doc_loc)).setText(doctor.getLoc());

        ((TextView) findViewById(R.id.pat_name)).setText(patient.getName());
        ((TextView) findViewById(R.id.pat_birth)).setText(patient.getBirth());
        ((TextView) findViewById(R.id.pat_sex)).setText(patient.getSex());
        ((TextView) findViewById(R.id.pat_address)).setText(patient.getAddress());
        ((TextView) findViewById(R.id.pat_phone)).setText(patient.getPhone());

        ((TextView) findViewById(R.id.pres_date)).setText(prescription.getTime());
        ((TextView) findViewById(R.id.doc_sig)).setText(doctor.getName());

        RecyclerView drugRecyclerView = findViewById(R.id.drug_rec);
        

    }
}