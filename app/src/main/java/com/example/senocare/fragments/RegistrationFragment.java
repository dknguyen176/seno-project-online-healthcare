package com.example.senocare.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senocare.R;
import com.example.senocare.activity.RegistrationActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegistrationFragment extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();
    EditText birthday;
    View mainView;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.patient_or_doctor, container, false);

        Button patient_button = mainView.findViewById(R.id.button_patient);
        Button doctor_button = mainView.findViewById(R.id.button_doctor);

        patient_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewLayout(R.layout.patient_registration);
                patientRegistration();
            }
        });

        doctor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewLayout(R.layout.doctor_registration);
                doctorRegistration();
            }
        });

        return mainView;
    }

    private void patientRegistration(){
        setBirthdayText();
        EditText phoneText = getView().findViewById(R.id.phone_number);
        phoneText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        Button button_finish = getView().findViewById(R.id.finishReg);
        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) getView().findViewById(R.id.email)).getText().toString();
                String password = ((EditText) getView().findViewById(R.id.password)).getText().toString();
                String name = ((EditText) getView().findViewById(R.id.name)).getText().toString();
                String birthday = ((EditText) getView().findViewById(R.id.birthday)).getText().toString();
                String phone = ((EditText) getView().findViewById(R.id.phone_number)).getText().toString();
                String address = ((EditText) getView().findViewById(R.id.home_address)).getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(birthday)) {
                    Toast.makeText(getContext(), "Birthday cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getContext(), "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getContext(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent login = new Intent();
                login.putExtra("userEmail", email);
                login.putExtra("userPassword", password);
                getActivity().setResult(Activity.RESULT_OK, login);
                getActivity().finish();
            }
        });
    }

    private void doctorRegistration(){
        setBirthdayText();
        Spinner specialize = getView().findViewById(R.id.specialize);
        List<String> specializeList = new ArrayList<>();;
        specializeList.add("lmao");
        specializeList.add("sus");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                specializeList.toArray(new String[0]));
        specialize.setAdapter(adapter);

        specialize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // do something
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do something
            }
        });

        Button button_finish = getView().findViewById(R.id.finishReg);
        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) getView().findViewById(R.id.email)).getText().toString();
                String password = ((EditText) getView().findViewById(R.id.password)).getText().toString();
                String name = ((EditText) getView().findViewById(R.id.name)).getText().toString();
                String birthday = ((EditText) getView().findViewById(R.id.birthday)).getText().toString();
                String yearsExp = ((EditText) getView().findViewById(R.id.yearsExp)).getText().toString();
                String specialize = ((Spinner) getView().findViewById(R.id.specialize)).getSelectedItem().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(birthday)) {
                    Toast.makeText(getContext(), "Birthday cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(yearsExp)) {
                    Toast.makeText(getContext(), "Years of Experience cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(specialize)) {
                    Toast.makeText(getContext(), "Specialization cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent login = new Intent();
                login.putExtra("userEmail", email);
                login.putExtra("userPassword", password);
                getActivity().setResult(Activity.RESULT_OK, login);
                getActivity().finish();
            }
        });
    }

    private void setViewLayout(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        mainView = inflater.inflate(id, rootView, false);
        rootView.addView(mainView);
    }

    private void setBirthdayText(){
        birthday = getView().findViewById(R.id.birthday);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getView().getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        birthday.setText(dateFormat.format(myCalendar.getTime()));
    }
}