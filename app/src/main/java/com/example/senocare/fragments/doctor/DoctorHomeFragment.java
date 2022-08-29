package com.example.senocare.fragments.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.activity.doctor.PrescriptionMakeActivity;
import com.example.senocare.activity.doctor.ScheduleReviewActivity;
import com.example.senocare.activity.doctor.ScheduleUpcomingActivity;
import com.example.senocare.activity.patient.ScheduleShowActivity;
import com.example.senocare.adapters.AppointmentAdapter;
import com.example.senocare.adapters.DoctorAdapter;
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Message;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Prescription;
import com.example.senocare.model.Schedule;
import com.example.senocare.model.Specialty;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

public class DoctorHomeFragment extends Fragment {

    // private ArrayList<???> userMessageList;
    private OrderedRealmCollection<Specialty> specialtiesList;
    private OrderedRealmCollection<Doctor> popularDoctorsList;

    private SpecialtyAdapter specialtyAdapter;
    private DoctorAdapter popularDoctorsAdapter;
    private AppointmentAdapter appointmentAdapter;

    RecyclerView messagesRecyclerView, specialtiesRecyclerView, popularDoctorsRecyclerView, appointmentRecyclerView;

    public DoctorHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_doctor, container, false);

        CardView reviewAppointment = root.findViewById(R.id.review_appointment_btn);
        reviewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScheduleReviewActivity.class));
            }
        });


        CardView makePrescription = root.findViewById(R.id.make_prescription_btn);
        makePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PrescriptionMakeActivity.class));
            }
        });

        TextView app_btn = root.findViewById(R.id.app_see_all);
        app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScheduleUpcomingActivity.class));
            }
        });

        createAppointmentRecyclerView(root);
        //test();

        return root;
    }

    private void createAppointmentRecyclerView(View root) {
        appointmentRecyclerView = root.findViewById(R.id.app_rec);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new AppointmentAdapter(getContext(), SenoDB.getUpcomingScheduleList(3), R.layout.appointment_patient);
        appointmentRecyclerView.setAdapter(appointmentAdapter);
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