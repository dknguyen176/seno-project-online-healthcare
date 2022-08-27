package com.example.senocare.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.senocare.fragments.RegistrationObjectFragment;

public class RegistrationAdapter extends FragmentStateAdapter {
    public RegistrationAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new RegistrationObjectFragment();
        Bundle args = new Bundle();

        args.putInt("position", position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
