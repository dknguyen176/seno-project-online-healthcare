package com.example.senocare.fragments.patient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senocare.R;
import com.example.senocare.activity.ShowSpecialtyList;
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

    // private ArrayList<???> userMessageList;
    private ArrayList<Specialty> specialtyList;
    private OrderedRealmCollection<Doctor> popularDoctorsList;

    private SpecialtyAdapter specialtyAdapter;
    private DoctorAdapter popularDoctorsAdapter;

    RecyclerView messagesRecyclerView, specialtyRecyclerView, popularDoctorsRecyclerView;

    public PatientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        getUserMessagesList(root);

        getMedicalSpecialtiesList(root);

        getPopularDoctorsList(root);

        test();

        return root;
    }

    private void getUserMessagesList(View root) {

    }

    private void getMedicalSpecialtiesList(View root) {
        specialtyRecyclerView = root.findViewById(R.id.spec_rec);
        specialtyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        specialtyList = new ArrayList<>();

        String[] specialtyNames = getResources().getStringArray(R.array.SpecialtiesList);
        for (String name : specialtyNames){
            specialtyList.add(new Specialty(name, null));
        }

        specialtyAdapter = new SpecialtyAdapter(getContext(), specialtyList,R.layout.spec_small);
        specialtyRecyclerView.setAdapter(specialtyAdapter);
    }

    private void getPopularDoctorsList(View root) {
        /*
        popularDoctorsRecyclerView = root.findViewById(R.id.popular_doctors_rec);
        popularDoctorsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularDoctorsAdapter = new DoctorAdapter(getActivity(), popularDoctorsList, R.layout.doctor_large);
        popularDoctorsRecyclerView.setAdapter(popularDoctorsAdapter);
        */
        // get popularDoctorList from db
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