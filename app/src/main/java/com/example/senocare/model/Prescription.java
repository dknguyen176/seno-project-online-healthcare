package com.example.senocare.model;

import org.bson.types.ObjectId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Prescription extends RealmObject {
    @PrimaryKey
    private ObjectId _id;

    private RealmList<Drugs> Drugs;

    @Required
    private String diagnostic;

    @Required
    private String doctor;

    @Required
    private String note;

    @Required
    private String patient;

    @Required
    private String time;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }

    public RealmList<Drugs> getDrugs() { return Drugs; }
    public void setDrugs(RealmList<Drugs> Drugs) { this.Drugs = Drugs; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    // Constructor
    public Prescription() { }

    public Prescription(String doctor, String patient, String time, String diagnostic, RealmList<Drugs> drugs, String note) {
        Drugs = drugs;
        this.diagnostic = diagnostic;
        this.doctor = doctor;
        this.note = note;
        this.patient = patient;
        this.time = time;
    }

    public void set(Prescription p) {
        Drugs = p.getDrugs();
        this.diagnostic = p.diagnostic;
        this.doctor = p.doctor;
        this.note = p.note;
        this.patient = p.patient;
        this.time = p.time;
    }
}
