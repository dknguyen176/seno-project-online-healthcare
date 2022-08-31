package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.getDoctor;
import static com.example.senocare.helper.SenoDB.user;
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
import com.example.senocare.activity.patient.ScheduleMakeActivity;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Doctor;

public class DetailedDoctor extends AppCompatActivity {

    private boolean chat_opened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_doctor);

        createToolbar();

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
        birthday.setText(TimeConverter.toString(doctor.getBirth(), "dd/MM/yyyy"));

        TextView spec = findViewById(R.id.spec_content);
        spec.setText(doctor.getSpec());

        TextView location = findViewById(R.id.location_content);
        location.setText(doctor.getLoc());

        TextView bio = findViewById(R.id.bio_content);
        bio.setText(doctor.getBio());

        TextView exper = findViewById(R.id.exper_content);
        exper.setText(String.valueOf(doctor.getExper()));

        ImageView profile_pic = findViewById(R.id.profile_pic);
        putByteArrayToImageView(doctor.getImg(), profile_pic, doctor.getSex());

        createChatBtn(doctor);

        createAppointmentBtn(doctor);
    }

    private void createAppointmentBtn(Doctor doctor) {
        Button appBtn = findViewById(R.id.app_btn);
        appBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DetailedDoctor.this, ScheduleMakeActivity.class);
            intent.putExtra("doctor", doctor);
            startActivity(intent);
        });
    }

    private void createChatBtn(Doctor doctor) {
        Button chatBtn = findViewById(R.id.chat_btn);
        chatBtn.setOnClickListener(v -> {
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