package com.example.senocare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senocare.R;
import com.example.senocare.adapters.DoctorAdapter;
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;
import com.example.senocare.model.SpecialtyModel;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;

public class HomeFragment extends Fragment {

    // private ArrayList<???> userMessageList;
    private OrderedRealmCollection<SpecialtyModel> specialtiesList;
    private OrderedRealmCollection<Doctor> popularDoctorsList;

    private SpecialtyAdapter specialtyAdapter;
    private DoctorAdapter popularDoctorsAdapter;

    RecyclerView messagesRecyclerView, specialtiesRecyclerView, popularDoctorsRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        getUserMessagesList(root);

        getMedicalSpecialtiesList(root);

        getPopularDoctorsList(root);

        return root;
    }

    private void getUserMessagesList(View root) {

    }

    private void getMedicalSpecialtiesList(View root) {
        /*
        specialtiesRecyclerView = root.findViewById(R.id.rec_specialty);
        specialtiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        specialtyAdapter = new SpecialtyAdapter(getActivity(), specialtiesList, R.layout.specialties_list);
        specialtiesRecyclerView.setAdapter(specialtyAdapter);
        */
        // get SpecialtiesList from dbdocto
    }

    private void getPopularDoctorsList(View root) {
        /*
        popularDoctorsRecyclerView = root.findViewById(R.id.popular_doctors_rec);
        popularDoctorsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularDoctorsAdapter = new DoctorAdapter(getActivity(), popularDoctorsList, R.layout.doctor_large);
        popularDoctorsRecyclerView.setAdapter(popularDoctorsAdapter);
        */
        // get popularDoctorList from db
    }

}