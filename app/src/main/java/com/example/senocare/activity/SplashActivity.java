package com.example.senocare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.senocare.R;

public class SplashActivity extends AppCompatActivity {
    private final int LAUNCH_MAIN_ACTIVITY = 1;
    private final String TAG = "SPLASH_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.v(TAG, "Created");

        Thread background = new Thread(() -> {
            try {
                // Thread will sleep for 5 seconds
                Thread.sleep(1000);

                Log.v(TAG, "Start main activity");
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtras(getIntent());
                startActivityForResult(intent, LAUNCH_MAIN_ACTIVITY);
            } catch (Exception ignored) { }
        });
        // start thread
        background.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_MAIN_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }
}