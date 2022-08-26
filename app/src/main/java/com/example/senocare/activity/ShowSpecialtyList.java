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
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.model.SpecialtyModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowSpecialtyList extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView specialtyRecyclerView;
    SpecialtyAdapter specialtyAdapter;
    List<SpecialtyModel> specialtyModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_specialty_list);

        createToolbar();

        createCategoryView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createCategoryView() {
        specialtyRecyclerView = findViewById(R.id.all_spec_rec);
        specialtyRecyclerView.setLayoutManager(new GridLayoutManager(ShowSpecialtyList.this,2));
        specialtyModelList = new ArrayList<>();
        specialtyAdapter = new SpecialtyAdapter(ShowSpecialtyList.this,specialtyModelList,R.layout.spec_large);
        specialtyRecyclerView.setAdapter(specialtyAdapter);

        getSpecialtyList();
    }

    private void getSpecialtyList() {
        // get all specialties
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