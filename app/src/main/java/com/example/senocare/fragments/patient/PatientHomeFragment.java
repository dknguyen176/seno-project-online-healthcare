package com.example.senocare.fragments.patient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senocare.R;
import com.example.senocare.adapters.DoctorAdapter;
import com.example.senocare.adapters.SpecialtyAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Specialty;
import com.example.senocare.model.Message;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Prescription;
import com.example.senocare.model.Schedule;

import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

public class PatientHomeFragment extends Fragment {

    // private ArrayList<???> userMessageList;
    private OrderedRealmCollection<Specialty> specialtiesList;
    private OrderedRealmCollection<Doctor> popularDoctorsList;

    private SpecialtyAdapter specialtyAdapter;
    private DoctorAdapter popularDoctorsAdapter;

    RecyclerView messagesRecyclerView, specialtiesRecyclerView, popularDoctorsRecyclerView;

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
        /*
        specialtiesRecyclerView = root.findViewById(R.id.rec_specialty);
        specialtiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        specialtyAdapter = new SpecialtyAdapter(getActivity(), specialtiesList, R.layout.specialties_list);
        specialtiesRecyclerView.setAdapter(specialtyAdapter);
        */
        // get SpecialtiesList from dbdocto
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