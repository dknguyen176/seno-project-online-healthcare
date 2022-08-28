package com.example.senocare.activity.doctor;

import static com.example.senocare.helper.SenoDB.getDoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.senocare.R;
import com.example.senocare.adapters.AppointmentAdapter;
import com.example.senocare.adapters.DrugMakeAdapter;
import com.example.senocare.model.Drugs;
import com.example.senocare.model.Prescription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.realm.OrderedRealmCollection;
import io.realm.RealmList;

public class PrescriptionMakeActivity extends AppCompatActivity {
    Toolbar toolbar;

    RecyclerView drugMakeRecyclerView;
    public static DrugMakeAdapter drugMakeAdapter;
    ArrayList<Drugs> drugMakeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_make);

        createToolbar();

        createDrugsRecyclerView();

        autofill();

        createFinishButton();
    }

    private void createFinishButton() {
        Button finish_btn = findViewById(R.id.finish_btn);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pat_email = ((EditText) findViewById(R.id.pat_email)).getText().toString();
                String doc_email = ((EditText) findViewById(R.id.doc_email)).getText().toString();
                String diagnostic = ((EditText) findViewById(R.id.diagnostic)).getText().toString();
                String time = ((EditText) findViewById(R.id.time)).getText().toString();
                RealmList<Drugs> drugsList = new RealmList<>();
                String note = ((EditText) findViewById(R.id.note)).getText().toString();

                // TODO: check input valid hay invalid
                for(int i = 0; i < drugMakeList.size(); ++i) {
                    DrugMakeAdapter.ViewHolder viewHolder = (DrugMakeAdapter.ViewHolder) drugMakeRecyclerView.findViewHolderForAdapterPosition(i);

                    String name = viewHolder.name.getText().toString();
                    if (TextUtils.isEmpty(name)) { Toast.makeText(PrescriptionMakeActivity.this, "Drug name cannot be empty", Toast.LENGTH_LONG).show(); return;}

                    String quantity = viewHolder.quantity.getText().toString();
                    if (TextUtils.isEmpty(quantity)) { Toast.makeText(PrescriptionMakeActivity.this, "Drug quantity cannot be empty", Toast.LENGTH_LONG).show(); return;}

                    String drug_note = viewHolder.note.getText().toString();

                    drugsList.add(new Drugs(name, Integer.valueOf(quantity), drug_note));
                }

                Prescription prescription = new Prescription(doc_email, pat_email, time, diagnostic, drugsList, note);
                // TODO: add new prescription

            }
        });
    }

    private void autofill() {
        Intent intent = getIntent();

        String pat_email = intent.getStringExtra("pat_email");
        if (pat_email != null) ((EditText) findViewById(R.id.pat_email)).setText(pat_email);

        ((EditText) findViewById(R.id.doc_email)).setText(getDoctor().getEmail());

        EditText time = findViewById(R.id.time);
        Calendar myCalendar = Calendar.getInstance();
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        time.setText(dateFormat.format(myCalendar.getTime()));
        time.setFocusable(false);
        time.setClickable(true);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                time.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PrescriptionMakeActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    private void createDrugsRecyclerView() {
        drugMakeRecyclerView = findViewById(R.id.drug_rec);
        drugMakeRecyclerView.setLayoutManager(new LinearLayoutManager(PrescriptionMakeActivity.this, LinearLayoutManager.VERTICAL, false));
        drugMakeList = new ArrayList<>();
        drugMakeAdapter = new DrugMakeAdapter(PrescriptionMakeActivity.this, drugMakeList);
        drugMakeRecyclerView.setAdapter(drugMakeAdapter);

        Button drug_btn = findViewById(R.id.add_drug_btn);
        drug_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drugMakeList.add(new Drugs());
                drugMakeAdapter.notifyItemInserted(drugMakeList.size() - 1);
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