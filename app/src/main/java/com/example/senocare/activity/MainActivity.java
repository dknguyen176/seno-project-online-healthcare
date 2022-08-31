package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeSenoDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setResult(Activity.RESULT_CANCELED, new Intent());
        
        setUserTypeTextView();
        
        setLogoutBtn();

        //clearAll();
    }

    private void initializeSenoDB() {
        Intent data = getIntent();
        Patient patient = data.getParcelableExtra("patient");
        Doctor doctor = data.getParcelableExtra("doctor");

        if (patient != null) SenoDB.regPatientInit(patient);
        else if (doctor != null) SenoDB.regDoctorInit(doctor);
        else SenoDB.loginInit();
    }

    private void setLogoutBtn() {
        ImageView logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(v -> {
            setResult(Activity.RESULT_OK, new Intent());
            finish();
        });
    }

    private void setUserTypeTextView() {
        TextView textView = findViewById(R.id.user_type);
        if (IS_PATIENT) {
            textView.setText("Patient");
        } else {
            textView.setText("Doctor");
        }
    }
}