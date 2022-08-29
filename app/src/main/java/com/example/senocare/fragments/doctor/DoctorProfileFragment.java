package com.example.senocare.fragments.doctor;

import static com.example.senocare.helper.SenoDB.getDoctor;
import static com.example.senocare.helper.SenoDB.getPatient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.senocare.R;
import com.example.senocare.activity.LoginActivity;
import com.example.senocare.activity.MainActivity;
import com.example.senocare.activity.doctor.DoctorEditProfileActivity;
import com.example.senocare.activity.patient.PatientEditProfileActivity;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;

public class DoctorProfileFragment extends Fragment {

    private static final int LAUNCH_EDIT = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setViewContent(view, getDoctor());

        TextView editText = view.findViewById(R.id.edit_text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), DoctorEditProfileActivity.class), LAUNCH_EDIT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                Doctor doctor = (Doctor) data.getParcelableExtra("doctor");
                setViewContent(getView(), doctor);
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    private void setViewContent(@NonNull View view, Doctor doctor) {

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