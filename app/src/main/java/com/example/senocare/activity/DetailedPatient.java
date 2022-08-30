package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.getPatient;
import static com.example.senocare.helper.SenoDB.user;
import static com.example.senocare.helper.ViewSupporter.putByteArrayToImageView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.activity.doctor.PrescriptionMakeActivity;
import com.example.senocare.activity.patient.ScheduleMakeActivity;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;

public class DetailedPatient extends AppCompatActivity {

    private boolean chat_opened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_patient);

        Intent intent = getIntent();
        String pat_id = intent.getStringExtra("_id");
        String chat_status = intent.getStringExtra("chat_status");
        if (chat_status != null && chat_status.equals("opened"))
            chat_opened = true;

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
        prescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedPatient.this, PrescriptionMakeActivity.class);
                intent.putExtra("pat_email", patient.getEmail());
                startActivity(intent);
            }
        });
    }
}