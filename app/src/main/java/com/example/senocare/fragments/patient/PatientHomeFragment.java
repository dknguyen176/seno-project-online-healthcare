package com.example.senocare.fragments.patient;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.activity.ShowDoctorList;
import com.example.senocare.activity.patient.PrescriptionListActivity;
import com.example.senocare.activity.patient.ScheduleMakeActivity;
import com.example.senocare.activity.patient.ScheduleShowActivity;
import com.example.senocare.adapters.AppointmentAdapter;
import com.example.senocare.adapters.DoctorAdapter;
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Specialty;
import com.example.senocare.model.Message;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Prescription;
import com.example.senocare.model.Schedule;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

public class PatientHomeFragment extends Fragment {

    DoctorAdapter doctorAdapter;
    AppointmentAdapter appointmentAdapter;

    RecyclerView doctorRecyclerView, appointmentRecyclerView;

    public PatientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_patient, container, false);

        CardView makeAppointment = root.findViewById(R.id.make_appointment_btn);
        makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScheduleMakeActivity.class));
            }
        });


        CardView seePrescription = root.findViewById(R.id.see_prescription_btn);
        seePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PrescriptionListActivity.class));
            }
        });

        TextView doctor_btn = root.findViewById(R.id.doc_see_all);
        doctor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShowDoctorList.class));
            }
        });

        TextView app_btn = root.findViewById(R.id.app_see_all);
        app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScheduleShowActivity.class));
            }
        });

        createDoctorRecyclerView(root);

        createAppointmentRecyclerView(root);

        return root;
    }

    private void createAppointmentRecyclerView(View root) {
        appointmentRecyclerView = root.findViewById(R.id.app_rec);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new AppointmentAdapter(getContext(), SenoDB.getUpcomingScheduleList(3), R.layout.appointment_upcoming_doctor);
        appointmentRecyclerView.setAdapter(appointmentAdapter);
    }

    private void createDoctorRecyclerView(View root) {
        doctorRecyclerView = root.findViewById(R.id.doc_rec);
        doctorRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        // TODO: getPopularDoctorList (khoang 10 cai sort desc theo rating)
        doctorAdapter = new DoctorAdapter(getContext(), SenoDB.getTopDoctorList(10), R.layout.doctor_large);
        doctorRecyclerView.setAdapter(doctorAdapter);
    }

    void test() {
        RealmResults<Patient> patients = SenoDB.getPatientList();
        RealmResults<Doctor> doctors = SenoDB.getDoctorList();
        RealmResults<Doctor> doctors1 = SenoDB.getDoctorList("Heart");
        RealmResults<Doctor> doctors2 = SenoDB.getTopDoctorList(1);
        RealmResults<Prescription> prescriptions = SenoDB.getPrescriptionList();
        RealmResults<Message> messages = SenoDB.getLatestMessageList();
        //RealmResults<Message> messages1 = SenoDB.getMessageList(messages.get(0).getConservation());
        RealmResults<Schedule> schedules = SenoDB.getScheduleList();

        Log.v("TEST", "Patient list: " + patients.size() + '\n' + patients);
        Log.v("TEST", "Doctor list: " + doctors.size() + '\n' + doctors);
        Log.v("TEST", "Doctor list by speciality \"Heart\": " + doctors1.size() + '\n' + doctors1);
        Log.v("TEST", "Top doctor list: " + doctors2.size() + '\n' + doctors2);
        Log.v("TEST", "Prescription list: " + prescriptions.size() + '\n' + prescriptions);
        Log.v("TEST", "Message list: " + messages.size() + '\n' + messages);
        Log.v("TEST", "Schedule list: " + schedules.size() + '\n' + schedules);
    }
}