package com.example.senocare.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.senocare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


// Instances of this class are fragments representing a single
// object in our collection.
public class RegistrationObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int position = args.getInt("position");

        int layoutId = 0;

        switch (position) {
            case 1:
            case 4:
                layoutId = R.layout.registration_checkboxes;
                break;
            case 2:
                layoutId = R.layout.registration_email_password;
                break;
            case 10:
                layoutId = R.layout.registration_specialty;
                break;
            default:
                layoutId = R.layout.registration_text;
                break;
        }

        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int position = args.getInt("position");

        CheckBox ckbox1 = view.findViewById(R.id.ckbox1);
        CheckBox ckbox2 = view.findViewById(R.id.ckbox2);
        TextView textView = view.findViewById(R.id.reg_text);
        TextView noteText = view.findViewById(R.id.reg_note);
        EditText editText = view.findViewById(R.id.reg_input);
        Spinner spinner = view.findViewById(R.id.spinner);

        if (position == 1 || position == 4){
            makeUniqueCheckBoxes(ckbox1, ckbox2);
        }

        switch (position) {
            case 1:
                inputPatientDoctor(ckbox1, ckbox2, textView, noteText);
                break;
            case 3:
                inputName(textView, editText);
                break;
            case 4:
                inputMaleFemale(ckbox1, ckbox2, textView, noteText);
                break;
            case 5:
                inputBirthday(textView, editText);
                break;
            case 6:
                inputPhone(textView, editText);
                break;
            case 7:
                inputHomeAddress(textView, editText);
                break;
            case 8:
                inputBio(textView, editText);
                break;
            case 9:
                inputLocation(textView, editText);
                break;
            case 10:
                inputSpecialty(textView, spinner);
                break;
        }
    }

    private void makeUniqueCheckBoxes(CheckBox ckbox1, CheckBox ckbox2) {
        ckbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ckbox1.isChecked()){
                    ckbox2.setChecked(false);
                }
            }
        });

        ckbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ckbox2.isChecked()){
                    ckbox1.setChecked(false);
                }
            }
        });
    }

    private void inputSpecialty(TextView textView, Spinner spinner) {
        textView.setText("Choose your specialty from this list.");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.SpecialtiesList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void inputLocation(TextView textView, EditText editText) {
        textView.setText("Where do you work?");
        editText.setHint("Work Address");
    }

    private void inputBio(TextView textView, EditText editText) {
        textView.setText("Say something about yourself.");
        editText.setHint("Personal Bio");
        editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(100) });
    }

    private void inputHomeAddress(TextView textView, EditText editText) {
        textView.setText("Where do you live?");
        editText.setHint("Home Address");
    }

    private void inputPhone(TextView textView, EditText editText) {
        textView.setText("What is your phone number?");
        editText.setHint("Phone Number");
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        editText.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
    }

    private void inputMaleFemale(CheckBox ckbox1, CheckBox ckbox2, TextView textView, TextView noteText) {
        textView.setText("Are you male or female?");
        ckbox1.setText("Male");
        ckbox2.setText("Female");
        noteText.setVisibility(View.VISIBLE);
    }

    private void inputPatientDoctor(CheckBox ckbox1, CheckBox ckbox2, TextView textView, TextView noteText) {
        textView.setText("Are you a patient or a doctor?");
        ckbox1.setText("Patient");
        ckbox2.setText("Doctor");
        noteText.setVisibility(View.INVISIBLE);
    }

    private void inputName(TextView textView, EditText editText) {
        textView.setText("What is your name?");
        editText.setHint("Full Name");
    }

    private void inputBirthday(TextView textView, EditText editText) {
        textView.setText("When is your birthday?");
        editText.setHint("Birthday");
        editText.setFocusable(false);
        editText.setClickable(true);
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                String myFormat="dd/MM/yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                editText.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getView().getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }
}