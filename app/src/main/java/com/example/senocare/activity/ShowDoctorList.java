package com.example.senocare.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senocare.R;
import com.example.senocare.adapters.DoctorAdapter;
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.SpecialtyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowDoctorList extends AppCompatActivity {
    public static boolean active = false;

    Toolbar toolbar;
    TextView titleView;

    RecyclerView doctorRecyclerView;
    public static DoctorAdapter doctorAdapter;
    public static List<Doctor> doctorList;
    public static HashMap<String, Integer> doctorListPosition;

    String title = "Doctor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor_list);
        active = true;

        //Intent intent = getIntent();

        createToolbar();

        createTitle();

        createDoctorView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createTitle() {
        titleView = findViewById(R.id.title);
        titleView.setText(title);
    }

    private void createDoctorView() {
        doctorRecyclerView = findViewById(R.id.doc_rec);
        doctorRecyclerView.setLayoutManager(new GridLayoutManager(ShowDoctorList.this,2));
        doctorList = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(ShowDoctorList.this, SenoDB.getDoctorList(), R.layout.doctor_large);
        doctorRecyclerView.setAdapter(doctorAdapter);
        doctorListPosition = new HashMap<>();
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