package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.getPatient;
import static com.example.senocare.helper.ViewSupporter.putByteArrayToImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.senocare.R;
import com.example.senocare.activity.doctor.PrescriptionMakeActivity;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Patient;

public class DetailedPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_patient);

        createToolbar();

        Intent intent = getIntent();
        String pat_id = intent.getStringExtra("_id");

        Patient patient = getPatient(pat_id);

        Log.v("DETAIL DOCTOR", "" + patient);

        TextView email = findViewById(R.id.email_content);
        email.setText(patient.getEmail());

        TextView name = findViewById(R.id.name_content);
        name.setText(patient.getName());

        TextView sex = findViewById(R.id.sex_content);
        sex.setText(patient.getSex());

        TextView birthday = findViewById(R.id.birthday_content);
        birthday.setText(TimeConverter.toString(patient.getBirth(), "dd/MM/yyyy"));

        TextView address = findViewById(R.id.address_content);
        address.setText(patient.getAddress());

        ImageView profile_pic = findViewById(R.id.profile_pic);
        putByteArrayToImageView(patient.getImg(), profile_pic, patient.getSex());

        createWritePrescription(patient);
    }

    private void createWritePrescription(Patient patient) {
        Button prescBtn = findViewById(R.id.presc_btn);
        prescBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DetailedPatient.this, PrescriptionMakeActivity.class);
            intent.putExtra("pat_email", patient.getEmail());
            startActivity(intent);
        });
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}