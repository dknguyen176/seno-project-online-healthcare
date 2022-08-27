package com.example.senocare.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.senocare.fragments.PagerFragment;
import com.example.senocare.fragments.PagerObjectFragment;

public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new PagerObjectFragment();
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
