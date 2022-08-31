package com.example.senocare.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.senocare.fragments.doctor.DoctorChatFragment;
import com.example.senocare.fragments.doctor.DoctorHomeFragment;
import com.example.senocare.fragments.doctor.DoctorProfileFragment;

public class DoctorMainAdapter extends FragmentStateAdapter {
    public DoctorMainAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position){
            case 0: fragment = new DoctorHomeFragment(); break;
            case 1: fragment = new DoctorChatFragment(); break;
            default: fragment = new DoctorProfileFragment(); break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
