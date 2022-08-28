package com.example.senocare.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.senocare.R;
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.model.Specialty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShowSpecialtyList extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView specialtyRecyclerView;
    SpecialtyAdapter specialtyAdapter;
    List<Specialty> specialtyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_specialty_list);

        createToolbar();

        createSpecialtyView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createSpecialtyView() {
        specialtyRecyclerView = findViewById(R.id.spec_rec);
        specialtyRecyclerView.setLayoutManager(new GridLayoutManager(ShowSpecialtyList.this,2));
        specialtyList = new ArrayList<>();

        String[] specialtyNames = getResources().getStringArray(R.array.SpecialtiesList);
        for (String name : specialtyNames){
            specialtyList.add(new Specialty(name, null));
        }

        specialtyAdapter = new SpecialtyAdapter(ShowSpecialtyList.this, specialtyList,R.layout.spec_large);
        specialtyRecyclerView.setAdapter(specialtyAdapter);
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