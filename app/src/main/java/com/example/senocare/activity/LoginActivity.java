package com.example.senocare.activity;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.senocare.R;

public class LoginActivity extends AppCompatActivity {

    final private int LAUNCH_MAIN_ACTIVITY = 1;
    final private int LAUNCH_REGISTER_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signUpText = findViewById(R.id.text_signup);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivityForResult(intent, LAUNCH_REGISTER_ACTIVITY);
            }
        });
    }

    private void login(String userEmail, String userPassword) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_REGISTER_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String userEmail = data.getStringExtra("userEmail");
                String userPassword = data.getStringExtra("userPassword");
                login(userEmail, userPassword);
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        } else if (requestCode == LAUNCH_MAIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

            } else if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            }
        }
    }

}