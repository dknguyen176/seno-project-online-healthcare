package com.example.senocare.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.senocare.model.Account;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Message;
import com.example.senocare.model.Patient;
import com.example.senocare.model.Prescription;
import com.example.senocare.model.Schedule;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
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

    private static Realm realm = null;
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

    public static void loginInit() {
        accountSubscription();

        setAccount();

        if (IS_PATIENT)
            patientSubscription();
        else
            doctorSubscription();
    }

    public static void regPatientInit(Patient patient) {
        accountSubscription();

        insertAccount(true);

        patientSubscription();

        insertPatient(patient);
    }

    public static void regDoctorInit(Doctor doctor) {
        accountSubscription();

        insertAccount(false);

        doctorSubscription();

        insertDoctor(doctor);
    }

    public static void accountSubscription() {
        SyncConfiguration config = new SyncConfiguration.Builder(user)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        realm = Realm.getInstance(config);

        realm.getSubscriptions().update(new SubscriptionSet.UpdateCallback() {
            @Override
            public void update(MutableSubscriptionSet subscriptions) {
                // if subscription has been added, then skip
                Subscription subscription = subscriptions.find("account");
                if (subscription != null) return;

                // add a subscription with a name
                subscriptions.add(Subscription.create("account",
                        realm.where(Account.class)
                                .equalTo("_id", user.getId())
                ));
            }
        }).waitForSynchronization();

        realm.refresh();
    }

    public static void patientSubscription() {
        realm.getSubscriptions().update(new SubscriptionSet.UpdateCallback() {
            @Override
            public void update(MutableSubscriptionSet subscriptions) {
                // if subscription has been added, then skip
                Subscription subscription = subscriptions.find("patient");
                if (subscription != null) return;

                // add a subscription with a name
                subscriptions.add(Subscription.create("patient",
                        realm.where(Patient.class)
                                .equalTo("_id", user.getId())
                ));
            }
        }).waitForSynchronization();

        realm.refresh();

        realm.getSubscriptions().update(new SubscriptionSet.UpdateCallback() {
            @Override
            public void update(MutableSubscriptionSet subscriptions) {
                // if subscription has been added, then skip
                Subscription subscription = subscriptions.find("doctor");
                if (subscription != null) return;

                subscriptions.add(Subscription.create("doctor",
                        realm.where(Doctor.class)
                ));
                subscriptions.add(Subscription.create("prescription",
                        realm.where(Prescription.class)
                                .equalTo("email1", user.getProfile().getEmail())
                ));
                subscriptions.add(Subscription.create("schedule",
                        realm.where(Schedule.class)
                                .equalTo("email1", user.getProfile().getEmail())
                ));
                subscriptions.add(Subscription.create("message",
                        realm.where(Message.class)
                                .equalTo("email1", user.getProfile().getEmail())
                                .or().equalTo("email2", user.getProfile().getEmail())
                ));
            }
        });
    }

    public static void doctorSubscription() {
        realm.getSubscriptions().update(new SubscriptionSet.UpdateCallback() {
            @Override
            public void update(MutableSubscriptionSet subscriptions) {
                // if subscription has been added, then skip
                Subscription subscription = subscriptions.find("doctor");
                if (subscription != null) return;

                subscriptions.add(Subscription.create("doctor",
                        realm.where(Doctor.class)
                                .equalTo("_id", user.getId())
                ));
            }
        }).waitForSynchronization();

        realm.refresh();

        realm.getSubscriptions().update(new SubscriptionSet.UpdateCallback() {
            @Override
            public void update(MutableSubscriptionSet subscriptions) {
                // if subscription has been added, then skip
                Subscription subscription = subscriptions.find("patient");
                if (subscription != null) return;

                // add a subscription with a name
                subscriptions.add(Subscription.create("patient",
                        realm.where(Patient.class)
                ));
                subscriptions.add(Subscription.create("prescription",
                        realm.where(Prescription.class)
                                .equalTo("email2", user.getProfile().getEmail())
                ));
                subscriptions.add(Subscription.create("schedule",
                        realm.where(Schedule.class)
                                .equalTo("email2", user.getProfile().getEmail())
                ));
                subscriptions.add(Subscription.create("message",
                        realm.where(Message.class)
                                .equalTo("email1", user.getProfile().getEmail())
                                .or().equalTo("email2", user.getProfile().getEmail())
                ));
            }
        });
    }

    public static void setAccount() {
        IS_PATIENT = realm.where(Account.class).findFirst().getType().equals("patient");
    }

    public static void insertAccount(boolean is_patient) {
        IS_PATIENT = is_patient;
        realm.executeTransaction(r -> {
            Account account = r.createObject(Account.class, user.getId());
            if (IS_PATIENT)
                account.setType("patient");
            else
                account.setType("doctor");
        });
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

    public static RealmResults<Doctor> getTopDoctorList(int limit) {
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

    public static RealmResults<Schedule> getPendingScheduleList() {
        return realm.where(Schedule.class)
                .equalTo("status", "Pending")
                .sort("time", Sort.ASCENDING)
                .findAll();
    }

    public static RealmResults<Schedule> getUpcomingScheduleList() {
        return realm.where(Schedule.class)
                .equalTo("status", "Accepted")
                .sort("time", Sort.ASCENDING)
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

    public static String getNameByEmail(String email) {
        if (IS_PATIENT)
            return realm.where(Doctor.class).equalTo("email1", email).findFirst().getName();
        else
            return realm.where(Patient.class).equalTo("email1", email).findFirst().getName();
    }

    public static Prescription getPrescription(ObjectId _id) {
        return realm.where(Prescription.class).equalTo("_id", _id).findFirst();
    }

    public static void setDateEditText(EditText editText,  String format, Context context, boolean maxDate, boolean minDate) {
        editText.setHint(format.toLowerCase());
        editText.setFocusable(false);
        editText.setClickable(true);
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
                editText.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                if (maxDate) datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                if (minDate) datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }
}
