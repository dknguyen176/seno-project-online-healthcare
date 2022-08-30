package com.example.senocare.model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

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
    private String email2;

    @Required
    private String note;

    @Required
    private String email1;

    @Required
    private Date time;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }

    public RealmList<Drugs> getDrugs() { return Drugs; }
    public void setDrugs(RealmList<Drugs> Drugs) { this.Drugs = Drugs; }
    public void setDrugs(ArrayList<Drugs> Drugs) {
        this.Drugs = new RealmList<>();
        this.Drugs.addAll(Drugs);
    }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getDoctor() { return email2; }
    public void setDoctor(String doctor) { this.email2 = doctor; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPatient() { return email1; }
    public void setPatient(String patient) { this.email1 = patient; }

    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }

    // Constructor
    public Prescription() { }

    public Prescription(String doctor, String patient, Date time, String diagnostic, String note) {
        this.diagnostic = diagnostic;
        this.email2 = doctor;
        this.note = note;
        this.email1 = patient;
        this.time = time;
    }

    public void set(Prescription p) {
        this.diagnostic = p.diagnostic;
        this.email2 = p.email2;
        this.note = p.note;
        this.email1 = p.email1;
        this.time = p.time;
    }
}
