package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.login;
import static com.example.senocare.helper.SenoDB.logout;
import static com.example.senocare.helper.SenoDB.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SenoDB.init(this);

        //register("dknguyen176", "1234abcd");
        //login("dknguyen176", "1234abcd");
        //logout();
    }
}