package com.example.senocare.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.adapters.DoctorAdapter;
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Specialty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmList;

public class ShowDoctorList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;

    RecyclerView doctorRecyclerView;
    public static DoctorAdapter doctorAdapter;
    Spinner doc_spec;
    OrderedRealmCollection<Doctor> doctorList;
    ArrayAdapter<CharSequence> specAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor_list);

        //Intent intent = getIntent();

        createToolbar();

        createDoctorRecyclerView();

        setSpecSpinner();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setSpecSpinner() {
        specAdapter = ArrayAdapter.createFromResource(this,
                R.array.SpecialtiesList2, android.R.layout.simple_spinner_item);
        specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doc_spec = findViewById(R.id.doc_spec_spinner);
        doc_spec.setAdapter(specAdapter);
        doc_spec.setOnItemSelectedListener(this);
    }

    private void createDoctorRecyclerView() {
        doctorList = SenoDB.getDoctorList();
        doctorRecyclerView = findViewById(R.id.doc_rec);
        doctorRecyclerView.setLayoutManager(new GridLayoutManager(ShowDoctorList.this,2));
        doctorAdapter = new DoctorAdapter(ShowDoctorList.this, doctorList, R.layout.doctor_large);
        doctorRecyclerView.setAdapter(doctorAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spec = (String) parent.getItemAtPosition(position);
        if (spec.compareTo("All") == 0) doctorList = SenoDB.getDoctorList();
        else doctorList = SenoDB.getDoctorList(spec);
        doctorAdapter = new DoctorAdapter(ShowDoctorList.this, doctorList, R.layout.doctor_large);
        doctorRecyclerView.setAdapter(doctorAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.home_toolbar);
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