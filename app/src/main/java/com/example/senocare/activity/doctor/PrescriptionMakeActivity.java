package com.example.senocare.activity.doctor;

import static com.example.senocare.helper.SenoDB.getDoctor;
import static com.example.senocare.helper.ViewSupporter.setDateEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.adapters.DrugMakeAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Drugs;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Prescription;

import java.util.ArrayList;
import java.util.Date;

public class PrescriptionMakeActivity extends AppCompatActivity {
    private final String TAG = "MAKE_PRESCRIPTION";

    Toolbar toolbar;

    RecyclerView drugMakeRecyclerView;
    DrugMakeAdapter drugMakeAdapter;
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
        finish_btn.setOnClickListener(v -> {
            getCurrentFocus().clearFocus();

            String pat_email = ((EditText) findViewById(R.id.pat_email)).getText().toString();
            String doc_email = ((EditText) findViewById(R.id.doc_email)).getText().toString();
            String diagnostic = ((EditText) findViewById(R.id.diagnostic)).getText().toString();
            String time = ((EditText) findViewById(R.id.time)).getText().toString();
            String note = ((EditText) findViewById(R.id.note)).getText().toString();

            Patient patient = SenoDB.getPatientByEmail(pat_email);
            Doctor doctor = SenoDB.getDoctorByEmail(doc_email);

            Log.v(TAG, "Drug List: " + drugMakeList.size());

            if (patient == null) { Toast.makeText(PrescriptionMakeActivity.this, "Patient not found", Toast.LENGTH_LONG).show(); return; }
            if (doctor == null) { Toast.makeText(PrescriptionMakeActivity.this, "Doctor not found", Toast.LENGTH_LONG).show(); return; }
            if (diagnostic.isEmpty()) { Toast.makeText(PrescriptionMakeActivity.this, "Diagnostic is empty", Toast.LENGTH_LONG).show(); return; }
            if (time.isEmpty()) { Toast.makeText(PrescriptionMakeActivity.this, "Time have not been set", Toast.LENGTH_LONG).show(); return; }
            if (drugMakeList.isEmpty()) { Toast.makeText(PrescriptionMakeActivity.this, "Drug list cannot be empty", Toast.LENGTH_LONG).show(); return; }

            for(Drugs drug : drugMakeList) {
                String drugName = drug.getName();
                int drugQuantity = drug.getQuantity();

                if (drugName.isEmpty()) { Toast.makeText(PrescriptionMakeActivity.this, "Drug name cannot be empty", Toast.LENGTH_LONG).show(); return; }
                if (drugQuantity <= 0) { Toast.makeText(PrescriptionMakeActivity.this, "Quantity must be positive", Toast.LENGTH_LONG).show(); return;}

                Log.v(TAG, "Drug name: " + drugName + "; Quantity: " + drugQuantity);
            }

            Prescription prescription = new Prescription(doc_email, pat_email, TimeConverter.toDate(time, "dd/MM/yyyy"), diagnostic, note);
            SenoDB.insertPrescription(prescription, drugMakeList);
            finish();
        });
    }

    private void autofill() {
        Intent intent = getIntent();

        String pat_email = intent.getStringExtra("pat_email");
        if (pat_email != null) ((EditText) findViewById(R.id.pat_email)).setText(pat_email);

        ((EditText) findViewById(R.id.doc_email)).setText(getDoctor().getEmail());

        EditText time = findViewById(R.id.time);
        setDateEditText(time, "dd/MM/yyyy", PrescriptionMakeActivity.this, false, true);
        time.setText(TimeConverter.toString(new Date(), "dd/MM/yyyy"));
    }

    private void createDrugsRecyclerView() {
        drugMakeRecyclerView = findViewById(R.id.drug_rec);
        drugMakeRecyclerView.setLayoutManager(new LinearLayoutManager(PrescriptionMakeActivity.this, LinearLayoutManager.VERTICAL, false));
        drugMakeList = new ArrayList<>();
        drugMakeAdapter = new DrugMakeAdapter(PrescriptionMakeActivity.this, drugMakeList);
        drugMakeRecyclerView.setAdapter(drugMakeAdapter);

        Button drug_btn = findViewById(R.id.add_drug_btn);
        drug_btn.setOnClickListener(v -> {
            drugMakeList.add(new Drugs());
            drugMakeAdapter.notifyItemInserted(drugMakeList.size() - 1);
        });
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> finish());
    }
}