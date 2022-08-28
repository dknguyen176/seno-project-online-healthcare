package com.example.senocare.fragments;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senocare.R;
import com.example.senocare.adapters.DoctorAdapter;
import com.example.senocare.adapters.DoctorMainAdapter;
import com.example.senocare.adapters.PatientMainAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainFragment extends Fragment {

    PatientMainAdapter patientMainAdapter;
    DoctorMainAdapter doctorMainAdapter;
    ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (IS_PATIENT) {
            patientMainAdapter = new PatientMainAdapter(this);
            viewPager = view.findViewById(R.id.main_pager);
            viewPager.setAdapter(patientMainAdapter);
            viewPager.setUserInputEnabled(true);
        } else {
            doctorMainAdapter = new DoctorMainAdapter(this);
            viewPager = view.findViewById(R.id.main_pager);
            viewPager.setAdapter(doctorMainAdapter);
            viewPager.setUserInputEnabled(true);
        }

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy = new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Home");
                        break;
                    case 1:
                        tab.setText("Chat");
                        break;
                    case 2:
                        tab.setText("Profile");
                        break;
                }
            }
        };


        new TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy).attach();


    }
}