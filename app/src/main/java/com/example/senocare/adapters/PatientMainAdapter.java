package com.example.senocare.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.senocare.fragments.patient.PatientChatFragment;
import com.example.senocare.fragments.patient.PatientHomeFragment;
import com.example.senocare.fragments.patient.PatientProfileFragment;

public class PatientMainAdapter extends FragmentStateAdapter {
    public PatientMainAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new PatientHomeFragment(); break;
            case 1: fragment = new PatientChatFragment(); break;
            default: fragment = new PatientProfileFragment(); break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
