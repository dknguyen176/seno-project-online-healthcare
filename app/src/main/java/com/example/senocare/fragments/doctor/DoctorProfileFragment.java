package com.example.senocare.fragments.doctor;

import static com.example.senocare.helper.SenoDB.getDoctor;
import static com.example.senocare.helper.SenoDB.getPatient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.senocare.R;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;

public class DoctorProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Doctor doctor = getDoctor();

        TextView email = view.findViewById(R.id.email_content);
        email.setText(doctor.getEmail());

        TextView name = view.findViewById(R.id.name_content);
        name.setText(doctor.getName());

        TextView sex = view.findViewById(R.id.sex_content);
        sex.setText(doctor.getSex());

        TextView birthday = view.findViewById(R.id.birthday_content);
        birthday.setText(doctor.getBirth());

        TextView spec = view.findViewById(R.id.spec_content);
        spec.setText(doctor.getSpec());

        TextView location = view.findViewById(R.id.location_content);
        location.setText(doctor.getLoc());

        TextView bio = view.findViewById(R.id.bio_content);
        bio.setText(doctor.getBio());

        TextView exper = view.findViewById(R.id.exper_content);
        exper.setText(String.valueOf(doctor.getExper()));
    }
}