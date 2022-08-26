package com.example.senocare.model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Schedule extends RealmObject {
    @PrimaryKey
    private ObjectId _id;

    @Required
    private String doctor;

    @Required
    private String note;

    @Required
    private String patient;

    @Required
    private String status;

    @Required
    private String time;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}