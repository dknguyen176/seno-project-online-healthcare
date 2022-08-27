package com.example.senocare.helper;

import android.content.Context;
import android.util.Log;

import com.example.senocare.model.Doctor;
import com.example.senocare.model.Message;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Prescription;
import com.example.senocare.model.Schedule;

import org.bson.types.ObjectId;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SubscriptionSet;
import io.realm.mongodb.sync.SyncConfiguration;

public final class SenoDB {
    private final static String appID = "senocare-uzrlz";

    public static Realm realm = null;
    public static App app;
    public static User user;

    public static boolean IS_PATIENT = true;

    public static void init(Context context) {
        Realm.init(context);

        app = new App(new AppConfiguration.Builder(appID)
                .build());
    }

    public static void close() {
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    public static void defaultSubscription() {
        SyncConfiguration config = new SyncConfiguration.Builder(user)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        realm = Realm.getInstance(config);
    }

    public static void patientSubscription() {
        SyncConfiguration config = new SyncConfiguration.Builder(user)
                .initialSubscriptions(new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                    @Override
                    public void configure(Realm realm, MutableSubscriptionSet subscriptions) {
                        // add a subscription with a name
                        subscriptions.add(Subscription.create("patientSubscription",
                                realm.where(Patient.class)
                                        .equalTo("email", user.getProfile().getEmail())
                        ));
                        subscriptions.add(Subscription.create("doctorSubscription",
                                realm.where(Doctor.class)
                        ));
                        subscriptions.add(Subscription.create("prescriptionSubscription",
                                realm.where(Prescription.class)
                                        .equalTo("patient", user.getProfile().getEmail())
                        ));
                        subscriptions.add(Subscription.create("scheduleSubscription",
                                realm.where(Schedule.class)
                                        .equalTo("patient", user.getProfile().getEmail())
                        ));
                        subscriptions.add(Subscription.create("messageSubscription",
                                realm.where(Message.class)
                                        .equalTo("sender", user.getProfile().getEmail())
                                        .or().equalTo("receiver", user.getProfile().getEmail())
                        ));
                    }
                })
                //.waitForInitialRemoteData(1000, TimeUnit.MILLISECONDS)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        realm = Realm.getInstance(config);
    }

    public static void doctorSubscription() {
        SyncConfiguration config = new SyncConfiguration.Builder(user)
                .initialSubscriptions(new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                    @Override
                    public void configure(Realm realm, MutableSubscriptionSet subscriptions) {
                        // add a subscription with a name
                        subscriptions.add(Subscription.create("patientSubscription",
                                realm.where(Patient.class)
                        ));
                        subscriptions.add(Subscription.create("doctorSubscription",
                                realm.where(Doctor.class)
                                        .equalTo("email", user.getProfile().getEmail())
                        ));
                        subscriptions.add(Subscription.create("prescriptionSubscription",
                                realm.where(Prescription.class)
                                        .equalTo("doctor", user.getProfile().getEmail())
                        ));
                        subscriptions.add(Subscription.create("scheduleSubscription",
                                realm.where(Schedule.class)
                                        .equalTo("doctor", user.getProfile().getEmail())
                        ));
                        subscriptions.add(Subscription.create("messageSubscription",
                                realm.where(Message.class)
                                        .equalTo("sender", user.getProfile().getEmail())
                                        .or().equalTo("receiver", user.getProfile().getEmail())
                        ));
                    }
                })
                //.waitForInitialRemoteData(1000, TimeUnit.MILLISECONDS)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        realm = Realm.getInstance(config);
    }

    public static void setUserType() {
        Patient patient = realm.where(Patient.class).findFirst();
        IS_PATIENT = (patient != null && patient.get_id().equals(user.getId()));
    }

    public static void setUserType(boolean bool) {
        IS_PATIENT = bool;
    }

    public static void insertPatient(Patient patient) {
        realm.executeTransaction(r -> {
            Patient newPatient = r.createObject(Patient.class, user.getId());
            newPatient.set(patient);
        });
    }

    public static void insertDoctor(Doctor doctor) {
        realm.executeTransaction(r -> {
            Doctor newDoctor = r.createObject(Doctor.class, user.getId());
            newDoctor.set(doctor);
        });
    }

    public static void insertPrescription(Prescription prescription) {
        realm.executeTransaction(r -> {
            Prescription newPrescription = r.createObject(Prescription.class, new ObjectId());
            newPrescription.set(prescription);
        });
    }

    public static void insertMessage(Message message) {
        realm.executeTransaction(r -> {
            Message newMessage = r.createObject(Message.class, new ObjectId());
            newMessage.set(message);
        });
    }

    public static void insertSchedule(Schedule schedule) {
        realm.executeTransaction(r -> {
            Schedule newSchedule = r.createObject(Schedule.class, new ObjectId());
            newSchedule.set(schedule);
        });
    }

    public static void modifyPatient(Patient p) {
        realm.executeTransaction(r -> {
            Patient patient = r.where(Patient.class).equalTo("_id", p.get_id()).findFirst();
            patient.set(p);
        });
    }

    public static void modifyDoctor(Doctor d) {
        realm.executeTransaction(r -> {
            Doctor doctor = r.where(Doctor.class).equalTo("_id", d.get_id()).findFirst();
            doctor.set(d);
        });
    }

    public static void modifySchedule(ObjectId _id, String status) {
        realm.executeTransaction(r -> {
            Schedule schedule = r.where(Schedule.class).equalTo("_id", _id).findFirst();
            schedule.setStatus(status);
        });
    }

    public static RealmResults<Patient> getPatientList() {
        return realm.where(Patient.class).findAll();
    }

    public static RealmResults<Doctor> getDoctorList() {
        return realm.where(Doctor.class)
                .sort("rating", Sort.DESCENDING)
                .findAll();
    }

    public static RealmResults<Doctor> getDoctorList(String spec) {
        return realm.where(Doctor.class)
                .equalTo("spec", spec)
                .sort("rating", Sort.DESCENDING)
                .findAll();
    }

    public static RealmResults<Doctor> getTopDoctorList(String spec, int limit) {
        return realm.where(Doctor.class)
                .sort("rating", Sort.DESCENDING)
                .limit(limit)
                .findAll();
    }

    public static RealmResults<Prescription> getPrescriptionList() {
        return realm.where(Prescription.class)
                .sort("time", Sort.DESCENDING)
                .findAll();
    }

    public static RealmResults<Message> getLatestMessageList() {
        return realm.where(Message.class)
                .sort("time", Sort.DESCENDING)
                .distinct("conservation")
                .findAll();
    }

    public static RealmResults<Message> getMessageList(String conservation) {
        return realm.where(Message.class)
                .equalTo("conservation", conservation)
                .sort("time", Sort.DESCENDING)
                .findAll();
    }

    public static RealmResults<Schedule> getScheduleList() {
        return realm.where(Schedule.class)
                .sort("time", Sort.DESCENDING)
                .findAll();
    }

    public static Patient getPatient() {
        return realm.where(Patient.class).findFirst();
    }

    public static Patient getPatient(String _id) {
        return realm.where(Patient.class).equalTo("_id", _id).findFirst();
    }

    public static Doctor getDoctor() {
        return realm.where(Doctor.class).findFirst();
    }

    public static Doctor getDoctor(String _id) {
        return realm.where(Doctor.class).equalTo("_id", _id).findFirst();
    }

    public static Prescription getPrescription(ObjectId _id) {
        return realm.where(Prescription.class).equalTo("_id", _id).findFirst();
    }
}
