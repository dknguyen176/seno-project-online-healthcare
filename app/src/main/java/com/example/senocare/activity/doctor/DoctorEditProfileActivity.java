package com.example.senocare.activity.doctor;

import static com.example.senocare.helper.SenoDB.getDoctor;
import static com.example.senocare.helper.SenoDB.modifyDoctor;
import static com.example.senocare.helper.SenoDB.user;
import static com.example.senocare.helper.ViewSupporter.setDateEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.senocare.R;
import com.example.senocare.activity.patient.PatientEditProfileActivity;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.helper.ViewSupporter;
import com.example.senocare.model.Doctor;

import java.lang.reflect.Array;

public class DoctorEditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_doctor);

        setResult(Activity.RESULT_CANCELED, new Intent());

        createToolbar();

        Doctor doctor = getDoctor();

        EditText first_view = findViewById(R.id.first);
        EditText last_view = findViewById(R.id.last);
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);
        EditText birthday_view = findViewById(R.id.birthday);
        Spinner spec_view = findViewById(R.id.spec);
        EditText exper_view = findViewById(R.id.exper);
        EditText loc_view = findViewById(R.id.address);
        EditText bio_view = findViewById(R.id.bio);
        Button discard_btn = findViewById(R.id.btn_discard);
        Button save_btn = findViewById(R.id.btn_save);

        first_view.setText(doctor.getFirst());
        last_view.setText(doctor.getLast());
        if (doctor.getSex().equals("Male")) male.setChecked(true);
        else female.setChecked(true);
        birthday_view.setText(TimeConverter.toString(doctor.getBirth(), "dd/MM/yyyy"));
        setDateEditText(birthday_view, "dd/MM/yyyy", DoctorEditProfileActivity.this, true, false);
        setSpinner(spec_view, doctor.getSpec());
        exper_view.setText(String.valueOf(doctor.getExper()));
        loc_view.setText(doctor.getLoc());
        bio_view.setText(doctor.getBio());
        discard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = first_view.getText().toString();
                String last = last_view.getText().toString();
                String sex = "";
                String birthday = birthday_view.getText().toString();
                String spec = spec_view.getSelectedItem().toString();
                int exper = Integer.parseInt(exper_view.getText().toString());
                String loc = loc_view.getText().toString();
                String bio = bio_view.getText().toString();

                if (male.isChecked()) sex = "Male";
                if (female.isChecked()) sex = "Female";

                if (first.isEmpty()) { Toast.makeText(DoctorEditProfileActivity.this, "First name cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (last.isEmpty()) { Toast.makeText(DoctorEditProfileActivity.this, "Last name cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (sex.isEmpty()) { Toast.makeText(DoctorEditProfileActivity.this, "You haven't set your gender", Toast.LENGTH_LONG).show(); return; }
                if (birthday.isEmpty()) { Toast.makeText(DoctorEditProfileActivity.this, "Birthday cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (spec.isEmpty()) { Toast.makeText(DoctorEditProfileActivity.this, "Speciality cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (exper < 0) { Toast.makeText(DoctorEditProfileActivity.this, "Experience must be non-negative", Toast.LENGTH_LONG).show(); return; }
                if (loc.isEmpty()) { Toast.makeText(DoctorEditProfileActivity.this, "Workplace cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (bio.isEmpty()) { Toast.makeText(DoctorEditProfileActivity.this, "Biography cannot be empty", Toast.LENGTH_LONG).show(); return; }

                ((Button) v).setEnabled(false);
                Doctor newDoctor = new Doctor(user.getProfile().getEmail(), first, last, sex,
                        TimeConverter.toDate(birthday, "dd/MM/yyyy"), spec, exper, loc, bio, 5);
                newDoctor.set_id(doctor.get_id());
                byte[] img = doctor.getImg();
                newDoctor.setImg(img);
                modifyDoctor(newDoctor);

                Intent intent = new Intent();
                intent.putExtra("doctor", newDoctor);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setSpinner(Spinner spec_view, String spec) {
        ArrayAdapter<CharSequence> specAdapter = ArrayAdapter.createFromResource(this,
                R.array.SpecialtiesList, android.R.layout.simple_spinner_item);
        specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spec_view.setAdapter(specAdapter);
        spec_view.setOnItemSelectedListener(this);
        spec_view.setSelection(specAdapter.getPosition(spec));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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