package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.getDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;

public class DetailedDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_doctor);

        Intent intent = getIntent();
        String doc_id = intent.getStringExtra("_id");

        Doctor doctor = getDoctorFromID(doc_id);

        Log.v("DETAIL DOCTOR", "" + doctor);

        TextView email = findViewById(R.id.email_content);
        email.setText(doctor.getEmail());

        TextView name = findViewById(R.id.name_content);
        name.setText(doctor.getName());

        TextView sex = findViewById(R.id.sex_content);
        sex.setText(doctor.getSex());

        TextView birthday = findViewById(R.id.birthday_content);
        birthday.setText(doctor.getBirth());

        TextView spec = findViewById(R.id.spec_content);
        spec.setText(doctor.getSpec());

        TextView location = findViewById(R.id.location_content);
        location.setText(doctor.getLoc());

        TextView bio = findViewById(R.id.bio_content);
        bio.setText(doctor.getBio());

        TextView exper = findViewById(R.id.exper_content);
        exper.setText(String.valueOf(doctor.getExper()));
    }

    private Doctor getDoctorFromID(String doc_id) {
        return SenoDB.getDoctor(doc_id);
    }
}