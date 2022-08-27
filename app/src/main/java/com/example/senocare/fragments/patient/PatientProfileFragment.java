package com.example.senocare.fragments.patient;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;
import static com.example.senocare.helper.SenoDB.getPatient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.model.Patient;

public class PatientProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_patient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Patient patient = getPatient();

        TextView email = view.findViewById(R.id.email_content);
        email.setText(patient.getEmail());

        TextView name = view.findViewById(R.id.name_content);
        name.setText(patient.getName());

        TextView sex = view.findViewById(R.id.sex_content);
        sex.setText(patient.getSex());

        TextView birthday = view.findViewById(R.id.birthday_content);
        birthday.setText(patient.getBirth());

        TextView phone = view.findViewById(R.id.phone_content);
        phone.setText(patient.getPhone());
    }
}