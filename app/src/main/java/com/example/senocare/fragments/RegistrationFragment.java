package com.example.senocare.fragments;

import static com.example.senocare.helper.SenoDB.app;
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

public class RegistrationFragment extends Fragment {
    // When requested, this adapter returns a PagerObjectFragment,
    // representing an object in the collection.
    RegistrationAdapter registrationAdapter;
    ViewPager2 viewPager;
    Button nextBtn;

    int position = 1;
    public static boolean is_patient = true;

    String email, password, name, first, last, sex, birth, phone, address, bio, loc, spec;

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
        nextBtn.setOnClickListener(v -> {
            boolean valid = false;
            switch (position) {
                case 1: valid = checkBoxUserType(view); break;
                case 2: valid = inputEmailPassword(view); break;
                case 3: valid = inputName(view); break;
                case 4: valid = checkBoxGender(view); break;
                case 5: valid = inputBirthday(view); break;
                case 6: valid = inputPhone(view); break;
                case 7: valid = inputAddress(view); break;
                case 8: valid = inputBio(view); break;
                case 9: valid = inputLoc(view); break;
                case 10: valid = spinnerSpec(view); break;
            }
            if (!valid) return;

            if (is_patient) {
                if (position < 7) {
                    position++;
                    viewPager.setCurrentItem(position - 1);
                    if (position == 7) nextBtn.setText("Finish Registration");
                    return;
                }

                v.setEnabled(false);
                Log.v("REGISTER", "Button pressed.");
                Patient patient = new Patient(email, name, sex, toDate(birth, "dd/MM/yyyy"), phone, address);
                register(patient.getEmail(), password, patient);
            }
            else{
                if (position == 5) position = 7;
                if (position < 10) {
                    position++;
                    viewPager.setCurrentItem(position - 1);
                    if (position == 10) nextBtn.setText("Finish Registration");
                    return;
                }

                v.setEnabled(false);
                Log.v("REGISTER", "Button pressed.");
                Doctor doctor = new Doctor(email, first, last, sex, toDate(birth, "dd/MM/yyyy"), bio, loc, spec);
                register(doctor.getEmail(), password, doctor);
            }
        });
    }

    private boolean checkBoxUserType(View view) {
        CheckBox ckbox_patient = view.findViewById(R.id.ckbox1);
        CheckBox ckbox_doctor = view.findViewById(R.id.ckbox2);

        if (!ckbox_patient.isChecked() && !ckbox_doctor.isChecked()){
            Toast.makeText(getContext(), "Please choose one of the choices above", Toast.LENGTH_LONG).show();
            return false;
        }
        else is_patient = !ckbox_doctor.isChecked();

        return true;
    }

    private boolean inputEmailPassword(View view) {
        email = ((EditText) view.findViewById(R.id.email)).getText().toString();
        password = ((EditText) view.findViewById(R.id.password)).getText().toString();
        if (TextUtils.isEmpty(email)) { Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_LONG).show(); return false;}
        if (TextUtils.isEmpty(password)) { Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_LONG).show(); return false;}
        return true;
    }

    private boolean inputName(View view) {
        first = ((EditText) view.findViewById(R.id.reg_first)).getText().toString();
        last = ((EditText) view.findViewById(R.id.reg_last)).getText().toString();
        name = first + " " + last;

        if (first.isEmpty()) { Toast.makeText(getContext(), "First name cannot be empty", Toast.LENGTH_LONG).show(); return false; }
        if (last.isEmpty()) { Toast.makeText(getContext(), "Last name cannot be empty", Toast.LENGTH_LONG).show(); return false; }
        return true;
    }

    private boolean checkBoxGender(View view) {
        CheckBox ckbox_male = view.findViewById(R.id.ckbox1);
        CheckBox ckbox_female = view.findViewById(R.id.ckbox2);

        if (!ckbox_male.isChecked() && !ckbox_female.isChecked()){
            Toast.makeText(getContext(), "Please choose one of the choices above", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (ckbox_male.isChecked()) sex = "Male";
        else sex = "Female";

        return true;
    }

    private boolean inputBirthday(View view) {
        birth = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
        if (TextUtils.isEmpty(birth)) { Toast.makeText(getContext(), "Birthday cannot be empty", Toast.LENGTH_LONG).show(); return false;}
        return true;
    }

    private boolean inputPhone(View view) {
        phone = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
        if (TextUtils.isEmpty(phone)) { Toast.makeText(getContext(), "Phone number cannot be empty", Toast.LENGTH_LONG).show(); return false;}
        return true;
    }

    private boolean inputAddress(View view) {
        address = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
        if (TextUtils.isEmpty(address)) { Toast.makeText(getContext(), "Home address cannot be empty", Toast.LENGTH_LONG).show(); return false;}
        return true;
    }

    private boolean inputBio(View view) {
        bio = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
        if (TextUtils.isEmpty(bio)) { Toast.makeText(getContext(), "Personal bio cannot be empty", Toast.LENGTH_LONG).show(); return false;}
        return true;
    }

    private boolean inputLoc(View view) {
        loc = ((EditText) view.findViewById(R.id.reg_input)).getText().toString();
        if (TextUtils.isEmpty(loc)) { Toast.makeText(getContext(), "Work location cannot be empty", Toast.LENGTH_LONG).show(); return false;}
        return true;
    }

    private boolean spinnerSpec(View view) {
        spec = ((Spinner) view.findViewById(R.id.spinner)).getSelectedItem().toString();
        return true;
    }

    private void register(String email, String password, Patient patient) {
        app.getEmailPassword().registerUserAsync(email, password, it -> {
            if (it.isSuccess()) {
                Log.i("EXAMPLE", "Successfully register user.");
                Toast.makeText(getContext(), "Registration success", Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("patient", patient);
                getActivity().setResult(Activity.RESULT_OK, intent);
            } else {
                Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                Toast.makeText(getContext(), "Failed to register user: " + it.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
            }
            getActivity().finish();
        });
    }

    private void register(String email, String password, Doctor doctor) {
        app.getEmailPassword().registerUserAsync(email, password, it -> {
            if (it.isSuccess()) {
                Log.i("EXAMPLE", "Successfully register user.");
                Toast.makeText(getContext(), "Registration success", Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("doctor", doctor);
                getActivity().setResult(Activity.RESULT_OK, intent);

            } else {
                Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                Toast.makeText(getContext(), "Failed to register user: " + it.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
            }
            getActivity().finish();
        });
    }
}
