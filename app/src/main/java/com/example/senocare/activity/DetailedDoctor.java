package com.example.senocare.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

        // put doctor's info into the layout
        Log.v("DETAIL DOCTOR", "" + doctor);
    }

    private Doctor getDoctorFromID(String doc_id) {
        return SenoDB.getDoctor(doc_id);
    }
}