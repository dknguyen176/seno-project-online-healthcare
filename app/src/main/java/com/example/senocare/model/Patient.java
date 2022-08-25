package com.example.senocare.model;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Patient extends RealmObject {
    @PrimaryKey
    private String _id;

    @Required
    private String email;

    @Required
    private String name;

    @Required
    private String birth;

    @Required
    private String phone;

    @Required
    private String address;

    // Standard getters & setters
    public void set(Patient patient) {
        setEmail(patient.getEmail());
        setName(patient.getName());
        setBirth(patient.getBirth());
        setPhone(patient.getPhone());
        setAddress(patient.getAddress());
    }

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBirth() { return birth; }
    public void setBirth(String birth) { this.birth = birth; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Patient() {
    }

    public Patient(String email, String name, String birth, String phone, String address) {
        this._id = email;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
    }
}