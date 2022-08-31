package com.example.senocare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.senocare.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setResult(Activity.RESULT_CANCELED, new Intent());

        TextView signInText = findViewById(R.id.text_signin);
        signInText.setOnClickListener(v -> finish());
    }
}