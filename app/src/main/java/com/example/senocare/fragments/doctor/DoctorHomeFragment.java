package com.example.senocare.fragments.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.activity.ScheduleMakeActivity;
import com.example.senocare.activity.ScheduleReviewActivity;
import com.example.senocare.activity.ScheduleShowActivity;
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

    RecyclerView messagesRecyclerView, specialtiesRecyclerView, popularDoctorsRecyclerView;

    public DoctorHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_doctor, container, false);

        ImageView reviewAppointment = root.findViewById(R.id.review_appointment_btn);
        reviewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScheduleReviewActivity.class));
            }
        });


        ImageView seeAppointment = root.findViewById(R.id.see_appointment_btn);
        seeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScheduleShowActivity.class));
            }
        });


        test();

        return root;
    }

    void test() {
        RealmResults<Patient> patients = SenoDB.getPatientList();
        RealmResults<Doctor> doctors = SenoDB.getDoctorList();
        RealmResults<Doctor> doctors1 = SenoDB.getDoctorList("Heart");
        RealmResults<Doctor> doctors2 = SenoDB.getTopDoctorList("Heart", 1);
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