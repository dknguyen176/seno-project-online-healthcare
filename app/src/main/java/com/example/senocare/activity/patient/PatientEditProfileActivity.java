package com.example.senocare.activity.patient;

import static com.example.senocare.helper.SenoDB.getPatient;
import static com.example.senocare.helper.ViewSupporter.setDateEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.senocare.R;
import com.example.senocare.activity.doctor.PrescriptionMakeActivity;
import com.example.senocare.model.Patient;

public class PatientEditProfileActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_patient);

        createToolbar();

        Patient patient = getPatient();

        EditText name_view = findViewById(R.id.name);
        name_view.setText(patient.getName());

        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);
        if (patient.getSex().compareTo("Male") == 0) male.setChecked(true);
        else female.setChecked(true);

        EditText birthday_view = findViewById(R.id.birthday);
        birthday_view.setText(patient.getBirth());
        setDateEditText(birthday_view, "dd/MM/yyyy", PatientEditProfileActivity.this, true, false);

        EditText phone_view = findViewById(R.id.phone);
        phone_view.setText(patient.getPhone());

        EditText address_view = findViewById(R.id.address);
        address_view.setText(patient.getAddress());

        Button discard_btn = findViewById(R.id.btn_discard);
        discard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button save_btn = findViewById(R.id.btn_save);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_view.getText().toString();
                String birthday = birthday_view.getText().toString();
                String phone = phone_view.getText().toString();
                String address = address_view.getText().toString();
                String sex = "";

                if (male.isChecked()) sex = "Male";
                if (female.isChecked()) sex = "Female";

                // TODO: check input co sus ko + update patient

                finish();
            }
        });
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}