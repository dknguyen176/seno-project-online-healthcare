package com.example.senocare.activity.patient;

import static com.example.senocare.helper.SenoDB.getPatient;
import static com.example.senocare.helper.SenoDB.user;
import static com.example.senocare.helper.ViewSupporter.setDateEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.senocare.R;
import com.example.senocare.activity.doctor.PrescriptionMakeActivity;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Patient;

public class PatientEditProfileActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_patient);

        setResult(Activity.RESULT_CANCELED, new Intent());

        createToolbar();

        Patient patient = getPatient();

        EditText name_view = findViewById(R.id.name);
        name_view.setText(patient.getName());

        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);
        if (patient.getSex().compareTo("Male") == 0) male.setChecked(true);
        else female.setChecked(true);

        EditText birthday_view = findViewById(R.id.birthday);
        birthday_view.setText(TimeConverter.toString(patient.getBirth(), "dd/MM/yyyy"));
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
                if (name.isEmpty()) { Toast.makeText(PatientEditProfileActivity.this, "Name cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (birthday.isEmpty()) { Toast.makeText(PatientEditProfileActivity.this, "Birthday cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (phone.isEmpty()) { Toast.makeText(PatientEditProfileActivity.this, "Phone cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (address.isEmpty()) { Toast.makeText(PatientEditProfileActivity.this, "Address cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (sex.isEmpty()) { Toast.makeText(PatientEditProfileActivity.this, "You haven't set your gender", Toast.LENGTH_LONG).show(); return; }

                ((Button) v).setEnabled(false);
                Patient newPatient = new Patient(user.getProfile().getEmail(), name, sex,
                        TimeConverter.toDate(birthday, "dd/MM/yyyy"), phone, address);
                newPatient.set_id(patient.get_id());
                SenoDB.modifyPatient(newPatient);

                Intent intent = new Intent();
                intent.putExtra("patient", newPatient);
                setResult(Activity.RESULT_OK, intent);
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