package com.example.senocare.fragments.patient;

import static com.example.senocare.helper.SenoDB.getPatient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.activity.patient.PatientEditProfileActivity;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Patient;

public class PatientProfileFragment extends Fragment {
    private static final int LAUNCH_EDIT = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_patient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setViewContent(view, getPatient());

        TextView editText = view.findViewById(R.id.edit_text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), PatientEditProfileActivity.class), LAUNCH_EDIT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                Patient patient = (Patient) data.getParcelableExtra("patient");
                setViewContent(getView(), patient);
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    private void setViewContent(@NonNull View view, Patient patient) {

        TextView email = view.findViewById(R.id.email_content);
        email.setText(patient.getEmail());

        TextView name = view.findViewById(R.id.name_content);
        name.setText(patient.getName());

        TextView sex = view.findViewById(R.id.sex_content);
        sex.setText(patient.getSex());

        TextView birthday = view.findViewById(R.id.birthday_content);
        birthday.setText(TimeConverter.toString(patient.getBirth(), "dd/MM/yyyy"));

        TextView phone = view.findViewById(R.id.phone_content);
        phone.setText(patient.getPhone());

        TextView address = view.findViewById(R.id.address_content);
        address.setText(patient.getAddress());
    }
}