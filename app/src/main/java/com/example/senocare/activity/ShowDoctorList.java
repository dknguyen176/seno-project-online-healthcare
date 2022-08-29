package com.example.senocare.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.adapters.DoctorAdapter;
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowDoctorList extends AppCompatActivity {
    Toolbar toolbar;

    RecyclerView doctorRecyclerView;
    public static DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor_list);

        //Intent intent = getIntent();

        createToolbar();

        createDoctorRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createDoctorRecyclerView() {
        doctorRecyclerView = findViewById(R.id.doc_rec);
        doctorRecyclerView.setLayoutManager(new GridLayoutManager(ShowDoctorList.this,2));
        doctorAdapter = new DoctorAdapter(ShowDoctorList.this, SenoDB.getDoctorList(), R.layout.doctor_large);
        doctorRecyclerView.setAdapter(doctorAdapter);
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("All Doctors");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}