package com.example.senocare.model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Schedule extends RealmObject {
    @PrimaryKey
    private ObjectId _id;

    @Required
    private String email2;

    @Required
    private String note;

    @Required
    private String email1;

    @Required
    private String status;

    @Required
    private String time;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }

    public String getDoctor() { return email2; }
    public void setDoctor(String doctor) { this.email2 = doctor; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPatient() { return email1; }
    public void setPatient(String patient) { this.email1 = patient; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    // Constructor
    public Schedule() { }

    public Schedule(String doctor, String patient, String time, String note, String status) {
        this.email2 = doctor;
        this.note = note;
        this.email1 = patient;
        this.status = status;
        this.time = time;
    }

    public void set(Schedule s) {
        this.email2 = s.email2;
        this.note = s.note;
        this.email1 = s.email1;
        this.status = s.status;
        this.time = s.time;
    }
}