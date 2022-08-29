package com.example.senocare.fragments.patient;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;
import static com.example.senocare.helper.SenoDB.getPatient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.activity.LoginActivity;
import com.example.senocare.activity.MainActivity;
import com.example.senocare.activity.ShowDoctorList;
import com.example.senocare.activity.patient.PatientEditProfileActivity;
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
        setViewContent(view);

        TextView editText = view.findViewById(R.id.edit_text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PatientEditProfileActivity.class));
                setViewContent(getView());
            }
        });
    }

    private void setViewContent(@NonNull View view) {
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

        TextView address = view.findViewById(R.id.address_content);
        address.setText(patient.getAddress());
    }
}