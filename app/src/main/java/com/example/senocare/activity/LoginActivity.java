package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;
import static com.example.senocare.helper.SenoDB.app;
import static com.example.senocare.helper.SenoDB.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;

import io.realm.Realm;
import io.realm.mongodb.Credentials;

public class LoginActivity extends AppCompatActivity {

    final private int LAUNCH_MAIN_ACTIVITY = 1;
    final private int LAUNCH_REGISTER_ACTIVITY = 2;

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
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivityForResult(intent, LAUNCH_REGISTER_ACTIVITY);
            }
        });
    }

    private void createSignInBtn() {
        Button signInButton = findViewById(R.id.btn_signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.email)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();

                ((Button) v).setEnabled(false);
                Log.v("LOGIN", "Button pressed.");

                login(email, password);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_REGISTER_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityForResult(intent, LAUNCH_MAIN_ACTIVITY);
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        } else if (requestCode == LAUNCH_MAIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
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

    /* ============================================================================================ */

    private void anonymousLogin(boolean isPatient) {
        // not login actually, just skip the login
        // *** DON'T USE DB WITH THIS LOGIN ***
        IS_PATIENT = true;

        Log.v("AUTH", "Anonymous login");

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivityForResult(intent, LAUNCH_MAIN_ACTIVITY);
    }

    private void login() {
        user = app.currentUser();
        if (user == null) return;

        SenoDB.loginInit();

        Log.v("AUTH", "Remember patient login: " + user.getProfile().getEmail());

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivityForResult(intent, LAUNCH_MAIN_ACTIVITY);
    }

    private void login(String email, String password) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                user = app.currentUser();

                SenoDB.loginInit();

                Log.v("AUTH", "Successfully authenticated using an email and password.");

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityForResult(intent, LAUNCH_MAIN_ACTIVITY);
            } else {
                Log.e("AUTH", it.getError().toString() + " " + email + " " + password);
                Toast.makeText(this, it.getError().toString(), Toast.LENGTH_LONG).show();
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