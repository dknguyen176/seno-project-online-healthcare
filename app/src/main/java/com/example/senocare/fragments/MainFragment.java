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
import com.example.senocare.adapters.DoctorMainAdapter;
import com.example.senocare.adapters.PatientMainAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {

    PatientMainAdapter patientMainAdapter;
    DoctorMainAdapter doctorMainAdapter;
    ViewPager2 viewPager;

    private final List<String> text = Arrays.asList("Home", "Messages", "Profile");
    private final List<Integer> ic = Arrays.asList(
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_person_24);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createPager(view);

        createTabLayout(view);
    }

    private void createPager(View view) {
        viewPager = view.findViewById(R.id.main_pager);
        viewPager.setUserInputEnabled(true);
        if (IS_PATIENT)
            viewPager.setAdapter(new PatientMainAdapter(this));
        else
            viewPager.setAdapter(new DoctorMainAdapter(this));
    }

    private void createTabLayout(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    tab.setText(text.get(position));
                    tab.setIcon(ic.get(position));
                }
        ).attach();
    }

}