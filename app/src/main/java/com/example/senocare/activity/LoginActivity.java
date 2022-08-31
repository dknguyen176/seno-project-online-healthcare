package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.app;
import static com.example.senocare.helper.SenoDB.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;

import io.realm.mongodb.Credentials;

public class LoginActivity extends AppCompatActivity {

    final private int LAUNCH_MAIN_ACTIVITY = 1;
    final private int LAUNCH_REGISTER_ACTIVITY = 2;

    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SenoDB.init(this);

        login();

        createSignInBtn();

        createSignUpBtn();
    }

    private void createSignUpBtn() {
        TextView signUpText = findViewById(R.id.text_signup);
        signUpText.setOnClickListener(v -> startRegister());
    }

    private void createSignInBtn() {
        signInButton = findViewById(R.id.btn_signin);
        signInButton.setOnClickListener(v -> {
            String email = ((EditText)findViewById(R.id.email)).getText().toString();
            String password = ((EditText)findViewById(R.id.password)).getText().toString();

            if (email.isEmpty()) { Toast.makeText(LoginActivity.this, "Email cannot be empty", Toast.LENGTH_LONG).show(); return; }
            if (password.isEmpty()) { Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_LONG).show(); return; }

            v.setEnabled(false);
            Log.v("LOGIN", "Button pressed.");

            login(email, password);
        });
    }

    private void startLoading(Patient patient, Doctor doctor) {
        Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
        if (patient != null) intent.putExtra("patient", patient);
        if (doctor != null) intent.putExtra("doctor", doctor);
        startActivityForResult(intent, LAUNCH_MAIN_ACTIVITY);
    }

    private void startRegister() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivityForResult(intent, LAUNCH_REGISTER_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_REGISTER_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                signInButton.setEnabled(false);

                String email = data.getStringExtra("email");
                String password = data.getStringExtra("password");
                Patient patient = data.getParcelableExtra("patient");
                Doctor doctor = data.getParcelableExtra("doctor");

                if (patient != null) login(email, password, patient);
                else login(email, password, doctor);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                signInButton.setEnabled(true);
            }
        } else if (requestCode == LAUNCH_MAIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                signInButton.setEnabled(true);
                logout();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SenoDB.close();
    }

    private void login() {
        user = app.currentUser(); if (user == null) return;
        Log.v("AUTH", "Remember patient login: " + user.getProfile().getEmail());
        startLoading(null, null);
    }

    private void login(String email, String password) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated using an email and password.");
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                user = app.currentUser();
                startLoading(null, null);
            } else {
                signInButton.setEnabled(true);
                Toast.makeText(this, it.getError().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login(String email, String password, Patient patient) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated using an email and password.");
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                user = app.currentUser();
                startLoading(patient, null);
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });
    }

    private void login(String email, String password, Doctor doctor) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated using an email and password.");
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                user = app.currentUser();
                startLoading(null, doctor);
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });
    }

    private void logout() {
        user.logOutAsync( result -> {
            if (result.isSuccess()) {
                SenoDB.close();
                Log.v("AUTH", "Successfully logged out.");
            } else {
                Log.e("AUTH", result.getError().toString());
            }
        });
    }
}