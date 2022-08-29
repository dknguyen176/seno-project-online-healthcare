package com.example.senocare.activity.patient;

import static com.example.senocare.helper.SenoDB.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senocare.R;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.helper.ViewSupporter;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Schedule;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final String TAG = "MAKE_SCHEDULE";

    EditText pat_name, pat_birth, pat_phone, reason;
    RadioButton male, female;
    Spinner doc_spec, doc_name;
    TextView date1, date2, date3, date4;
    Button make;

    String sex = null, doc_email, day_select;
    List<String> doctorList = new ArrayList<>();
    ArrayAdapter<CharSequence> specAdapter;
    ArrayAdapter<String> nameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_make);

        bindView();

        setBirthdayPicker();

        setGenderPick();

        setDocSpinner();

        setSchedulePicker();

        setMakeBtn();
    }

    private void setMakeBtn() {
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = pat_name.getText().toString();
                String birth = pat_birth.getText().toString();
                String phone = pat_phone.getText().toString();
                String note = reason.getText().toString();

                if (name.isEmpty()) { Toast.makeText(ScheduleMakeActivity.this, "Name cannot be empty", Toast.LENGTH_LONG).show(); return;}
                if (birth.isEmpty()) { Toast.makeText(ScheduleMakeActivity.this, "Birthday cannot be empty", Toast.LENGTH_LONG).show(); return;}
                if (phone.isEmpty()) { Toast.makeText(ScheduleMakeActivity.this, "Phone cannot be empty", Toast.LENGTH_LONG).show(); return;}
                if (sex == null) { Toast.makeText(ScheduleMakeActivity.this, "Sex cannot be empty", Toast.LENGTH_LONG).show(); return;}
                if (doc_email.isEmpty()) { Toast.makeText(ScheduleMakeActivity.this, "Doctor cannot be empty", Toast.LENGTH_LONG).show(); return;}
                if (day_select.isEmpty()) { Toast.makeText(ScheduleMakeActivity.this, "You have not selected a date", Toast.LENGTH_LONG).show(); return;}

                note = "Full name: " + name
                        + "\nSex: " + sex
                        + "\nBirthday: " + birth
                        + "\nPhone: " + phone
                        + "\nReason: " + note;
                Schedule schedule = new Schedule(
                        doc_email,
                        user.getProfile().getEmail(),
                        day_select,
                        note,
                        "Pending"
                );
                SenoDB.insertSchedule(schedule);

                Log.v(TAG, note);

                finish();
            }
        });
    }

    private void setSchedulePicker() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date d1 = c.getTime(); c.add(Calendar.DATE, 1);
        Date d2 = c.getTime(); c.add(Calendar.DATE, 1);
        Date d3 = c.getTime();

        setOnDateClick(date1, "MMM d, yyyy", "yyyy/MM/dd", d1);
        setOnDateClick(date2, "MMM d, yyyy", "yyyy/MM/dd", d2);
        setOnDateClick(date3, "MMM d, yyyy", "yyyy/MM/dd", d3);
        setOnDateClick(date4, "MMM d, yyyy", "yyyy/MM/dd");
    }

    private void setDocSpinner() {
        specAdapter = ArrayAdapter.createFromResource(this,
                R.array.SpecialtiesList, android.R.layout.simple_spinner_item);
        specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doc_spec.setAdapter(specAdapter);
        doc_spec.setOnItemSelectedListener(this);

        nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorList);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doc_name.setAdapter(nameAdapter);
        doc_name.setOnItemSelectedListener(this);
    }

    private void setGenderPick() {
        male.setOnClickListener(onGenderClicked());
        female.setOnClickListener(onGenderClicked());
    }

    private void setBirthdayPicker() {
        ViewSupporter.setDateEditText(pat_birth, "dd/MM/yyyy", ScheduleMakeActivity.this, true, false);
    }

    private void bindView() {
        pat_name = findViewById(R.id.pat_name);
        pat_birth = findViewById(R.id.pat_birth);
        pat_phone = findViewById(R.id.pat_phone);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        doc_spec = findViewById(R.id.doc_spec_spinner);
        doc_name = findViewById(R.id.doc_name_spinner);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        reason = findViewById(R.id.reason);
        make = findViewById(R.id.finish_btn);
    }

    View.OnClickListener onGenderClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                if (checked) {
                    sex = ((RadioButton) v).getText().toString();
                    Log.v(TAG, "Gender: " + sex);
                }
            }
        };
    }

    void setOnDateClick(TextView textView, String format, String rawFormat, Date dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        SimpleDateFormat rawDateFormat = new SimpleDateFormat(rawFormat, Locale.US);

        textView.setClickable(true);
        textView.setText(dateFormat.format(dt));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day_select = rawDateFormat.format(dt);
                date1.setBackgroundResource(android.R.color.transparent);
                date2.setBackgroundResource(android.R.color.transparent);
                date3.setBackgroundResource(android.R.color.transparent);
                date4.setBackgroundResource(android.R.color.transparent);
                v.setBackgroundResource(R.color.bg_message);

                Log.v(TAG, "Date: " + day_select);
            }
        });
    }

    void setOnDateClick(TextView textView, String format, String rawFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        SimpleDateFormat rawDateFormat = new SimpleDateFormat(rawFormat, Locale.US);

        textView.setClickable(true);

        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                textView.setTextSize(13);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setText(dateFormat.format(myCalendar.getTime()));
                day_select = rawDateFormat.format(myCalendar.getTime());

                date1.setBackgroundResource(android.R.color.transparent);
                date2.setBackgroundResource(android.R.color.transparent);
                date3.setBackgroundResource(android.R.color.transparent);
                date4.setBackgroundResource(R.color.bg_message);

                Log.v(TAG, "Date: " + day_select);
            }
        };
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleMakeActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.doc_spec_spinner) {
            String spec = (String) parent.getItemAtPosition(position);

            doctorList.clear();
            for(Doctor doctor : SenoDB.getDoctorList(spec)) {
                String name = doctor.getName() + " (" + doctor.getEmail() + ")";
                doctorList.add(name);
            }
            nameAdapter.notifyDataSetChanged();

            Log.v(TAG, "Speciality: " + spec);
            Log.v(TAG, "Doctor count: " + doctorList.size());
        } else {
            String name = (String) parent.getItemAtPosition(position);
            doc_email = name.substring(name.indexOf('(') + 1, name.length() - 1);
            Log.v(TAG, "Doctor email: " + doc_email);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}