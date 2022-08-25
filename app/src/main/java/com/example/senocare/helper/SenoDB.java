package com.example.senocare.helper;

import android.content.Context;
import android.util.Log;

import com.example.senocare.model.Doctor;
import com.example.senocare.model.Patient;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
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
    public static AtomicReference<User> user = new AtomicReference<User>();

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
        SyncConfiguration config = new SyncConfiguration.Builder(user.get())
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        realm = Realm.getInstance(config);
    }

    public static void patientSubscription() {
        SyncConfiguration config = new SyncConfiguration.Builder(user.get())
                .initialSubscriptions(new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                    @Override
                    public void configure(Realm realm, MutableSubscriptionSet subscriptions) {
                        // add a subscription with a name
                        subscriptions.add(Subscription.create("patientSubscription",
                                realm.where(Patient.class).equalTo("email", user.get().getProfile().getEmail())));
                        subscriptions.add(Subscription.create("doctorSubscription",
                                realm.where(Doctor.class)));
                    }
                })
                //.waitForInitialRemoteData(1000, TimeUnit.MILLISECONDS)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        realm = Realm.getInstance(config);
    }

    public static void doctorSubscription() {
        SyncConfiguration config = new SyncConfiguration.Builder(user.get())
                .initialSubscriptions(new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                    @Override
                    public void configure(Realm realm, MutableSubscriptionSet subscriptions) {
                        // add a subscription with a name
                        subscriptions.add(Subscription.create("patientSubscription",
                                realm.where(Patient.class)));
                        subscriptions.add(Subscription.create("doctorSubscription",
                                realm.where(Doctor.class).equalTo("email", user.get().getProfile().getEmail())));
                    }
                })
                //.waitForInitialRemoteData(1000, TimeUnit.MILLISECONDS)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        realm = Realm.getInstance(config);
    }

    public static void insertPatient(Patient patient) {
        realm.executeTransaction(r -> {
            Patient newPatient = r.createObject(Patient.class, patient.getEmail());
            newPatient.set(patient);
        });
    }

    public static void insertDoctor(Doctor doctor) {
        realm.executeTransaction(r -> {
            Doctor newDoctor = r.createObject(Doctor.class, doctor.getEmail());
            newDoctor.set(doctor);
        });
    }

    public static void setUserType() {
        Patient patient = realm.where(Patient.class).findFirst();
        IS_PATIENT = (patient != null && patient.get_id().equals(user.get().getProfile().getEmail()));
    }

    public static void setUserType(boolean bool) {
        IS_PATIENT = bool;
    }

    public static RealmResults<Patient> getPatientList() {
        return realm.where(Patient.class).findAll();
    }

    public static RealmResults<Doctor> getDoctorList() {
        return realm.where(Doctor.class).findAll();
    }

    public static Patient getPatient() {
        return realm.where(Patient.class).findFirst();
    }

    public static Doctor getDoctor() {
        return realm.where(Doctor.class).findFirst();
    }
}
