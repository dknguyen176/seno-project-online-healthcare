package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.getDoctor;
import static com.example.senocare.helper.SenoDB.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.activity.patient.ScheduleMakeActivity;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;

public class DetailedDoctor extends AppCompatActivity {

    private boolean chat_opened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_doctor);

        Intent intent = getIntent();
        String doc_id = intent.getStringExtra("_id");
        String chat_status = intent.getStringExtra("chat_status");
        if (chat_status != null && chat_status.equals("opened"))
            chat_opened = true;

        Doctor doctor = getDoctor(doc_id);

        Log.v("DETAIL DOCTOR", "" + doctor);

        TextView email = findViewById(R.id.email_content);
        email.setText(doctor.getEmail());

        TextView name = findViewById(R.id.name_content);
        name.setText(doctor.getName());

        TextView sex = findViewById(R.id.sex_content);
        sex.setText(doctor.getSex());

        TextView birthday = findViewById(R.id.birthday_content);
        birthday.setText(doctor.getBirth());

        TextView spec = findViewById(R.id.spec_content);
        spec.setText(doctor.getSpec());

        TextView location = findViewById(R.id.location_content);
        location.setText(doctor.getLoc());

        TextView bio = findViewById(R.id.bio_content);
        bio.setText(doctor.getBio());

        TextView exper = findViewById(R.id.exper_content);
        exper.setText(String.valueOf(doctor.getExper()));

        createChatBtn(doctor);

        createAppointmentBtn(doctor);
    }

    private void createAppointmentBtn(Doctor doctor) {
        Button appBtn = findViewById(R.id.app_btn);
        appBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedDoctor.this, ScheduleMakeActivity.class);
                intent.putExtra("doctor", doctor);
                startActivity(intent);
            }
        });
    }

    private void createChatBtn(Doctor doctor) {
        Button chatBtn = findViewById(R.id.chat_btn);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_opened) {
                    finish();
                } else {
                    String sender = user.getProfile().getEmail();
                    String receiver = doctor.getEmail();
                    String conservation;
                    if (sender.compareTo(receiver) < 0)
                        conservation = sender + receiver;
                    else
                        conservation = receiver + sender;

                    Intent intent = new Intent(DetailedDoctor.this, ChatBoxActivity.class);
                    intent.putExtra("conservation", conservation);
                    intent.putExtra("sender", sender);
                    intent.putExtra("receiver", receiver);

                    startActivity(intent);
                }
            }
        });
    }
}