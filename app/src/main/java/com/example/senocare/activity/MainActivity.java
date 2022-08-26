package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setResult(Activity.RESULT_CANCELED, new Intent());

        Button logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });

        Button showDrBtn = findViewById(R.id.showDoctor);
        showDrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowDoctorList.class);
                startActivity(intent);
            }
        });

        TextView textView = findViewById(R.id.user_type);
        if (IS_PATIENT) {
            textView.setText("Patient");
        } else {
            textView.setText("Doctor");
        }
    }
}