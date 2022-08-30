package com.example.senocare.fragments;

import static com.example.senocare.helper.SenoDB.app;
import static com.example.senocare.helper.SenoDB.regDoctorInit;
import static com.example.senocare.helper.SenoDB.regPatientInit;
import static com.example.senocare.helper.SenoDB.user;
import static com.example.senocare.helper.TimeConverter.toDate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.senocare.R;
import com.example.senocare.adapters.RegistrationAdapter;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;

import io.realm.mongodb.Credentials;

public class RegistrationFragment extends Fragment {
    // When requested, this adapter returns a PagerObjectFragment,
    // representing an object in the collection.
    RegistrationAdapter registrationAdapter;
    ViewPager2 viewPager;
    Button nextBtn;

    int position = 1;
    public static boolean is_patient = true;

    String email, password, name, sex, birth, phone, address, bio, loc, spec;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        registrationAdapter = new RegistrationAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(registrationAdapter);
        viewPager.setUserInputEnabled(false);

        nextBtn = view.findViewById(R.id.reg_nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 1){
                    CheckBox ckbox_patient = view.findViewById(R.id.ckbox1);
                    CheckBox ckbox_doctor = view.findViewById(R.id.ckbox2);

                    if (!ckbox_patient.isChecked() && !ckbox_doctor.isChecked()){
                        Toast.makeText(getContext(), "Please choose one of the choices above", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if (ckbox_doctor.isChecked()) is_patient = false;
                    else is_patient = true;
                }
                else if (position == 2){
                    email = ((EditText) view.findViewById(R.id.email)).getText().toString();
                    password = ((EditText) view.findViewById(R.id.password)).getText().toString();
                    if (TextUtils.isEmpty(email)) { Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_LONG).show(); return;}
                    if (TextUtils.isEmpty(password)) { Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_LONG).show(); return;}
                }
                else if (position == 3){
                    name = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
                    if (TextUtils.isEmpty(name)) { Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_LONG).show(); return;}
                }
                else if (position == 4){
                    CheckBox ckbox_male = view.findViewById(R.id.ckbox1);
                    CheckBox ckbox_female = view.findViewById(R.id.ckbox2);

                    if (!ckbox_male.isChecked() && !ckbox_female.isChecked()){
                        Toast.makeText(getContext(), "Please choose one of the choices above", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if (ckbox_male.isChecked()) sex = "Male";
                    else sex = "Female";
                }
                else if (position == 5){
                    birth = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
                    if (TextUtils.isEmpty(birth)) { Toast.makeText(getContext(), "Birthday cannot be empty", Toast.LENGTH_LONG).show(); return;}
                }
                else if (position == 6){
                    phone = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
                    if (TextUtils.isEmpty(phone)) { Toast.makeText(getContext(), "Phone number cannot be empty", Toast.LENGTH_LONG).show(); return;}
                }
                else if (position == 7){
                    address = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
                    if (TextUtils.isEmpty(address)) { Toast.makeText(getContext(), "Home address cannot be empty", Toast.LENGTH_LONG).show(); return;}
                }
                else if (position == 8){
                    bio = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
                    if (TextUtils.isEmpty(bio)) { Toast.makeText(getContext(), "Personal bio cannot be empty", Toast.LENGTH_LONG).show(); return;}
                }
                else if (position == 9){
                    loc = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
                    if (TextUtils.isEmpty(loc)) { Toast.makeText(getContext(), "Work location cannot be empty", Toast.LENGTH_LONG).show(); return;}
                }
                else if (position == 10){
                    spec = ((Spinner) view.findViewById(R.id.spinner)).getSelectedItem().toString();
                }

                if (is_patient) {
                    if (position < 7) {
                        position++;
                        viewPager.setCurrentItem(position - 1);
                        if (position == 7) nextBtn.setText("Finish Registration");
                    } else {
                        ((Button) v).setEnabled(false);
                        Log.v("REGISTER", "Button pressed.");

                        Patient patient = new Patient(email, name, sex, toDate(birth, "dd/MM/yyyy"), phone, address);
                        register(patient.getEmail(), password, patient);
                    }
                }
                else{
                    if (position == 5){
                        position = 7;
                    }
                    if (position < 10) {
                        position++;
                        viewPager.setCurrentItem(position - 1);
                        if (position == 10) nextBtn.setText("Finish Registration");
                    } else {
                        ((Button) v).setEnabled(false);
                        Log.v("REGISTER", "Button pressed.");

                        Doctor doctor = new Doctor(email, name, sex, toDate(birth, "dd/MM/yyyy"), bio, loc, spec);
                        register(doctor.getEmail(), password, doctor);
                    }
                }
            }
        });
    }

    private void register(String email, String password, Patient patient) {
        app.getEmailPassword().registerUserAsync(email, password, it -> {
            if (it.isSuccess()) {
                login(email, password, patient);

                Log.i("EXAMPLE", "Successfully registered user.");
            } else {
                nextBtn.setEnabled(true);
                Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                Toast.makeText(getContext(), "Failed to register user: " + it.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void register(String email, String password, Doctor doctor) {
        app.getEmailPassword().registerUserAsync(email, password, it -> {
            if (it.isSuccess()) {
                login(email, password, doctor);

                Log.i("EXAMPLE", "Successfully registered user.");
            } else {
                nextBtn.setEnabled(true);
                Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                Toast.makeText(getContext(), "Failed to register user: " + it.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login(String email, String password, Patient patient) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                user = app.currentUser();

                regPatientInit(patient);

                Log.v("AUTH", "Successfully authenticated using an email and password.");

                getActivity().setResult(Activity.RESULT_OK, new Intent());
                getActivity().finish();
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });
    }

    private void login(String email, String password, Doctor doctor) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                user = app.currentUser();

                regDoctorInit(doctor);

                Log.v("AUTH", "Successfully authenticated using an email and password.");

                getActivity().setResult(Activity.RESULT_OK, new Intent());
                getActivity().finish();
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });
    }
}
