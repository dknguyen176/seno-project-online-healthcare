package com.example.senocare.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;

public class DetailedPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_patient);

        Intent intent = getIntent();
        String pat_id = intent.getStringExtra("_id");

        Patient patient = getPatientFromID(pat_id);

        Log.v("DETAIL DOCTOR", "" + patient);

        TextView email = findViewById(R.id.email_content);
        email.setText(patient.getEmail());

        TextView name = findViewById(R.id.name_content);
        name.setText(patient.getName());

        TextView sex = findViewById(R.id.sex_content);
        sex.setText(patient.getSex());

        TextView birthday = findViewById(R.id.birthday_content);
        birthday.setText(patient.getBirth());

        TextView address = findViewById(R.id.address_content);
        address.setText(patient.getAddress());
    }

    private Patient getPatientFromID(String pat_id) {
        return SenoDB.getPatient(pat_id);
    }
}