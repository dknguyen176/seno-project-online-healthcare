package com.example.senocare.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Doctor  extends RealmObject {
    @PrimaryKey
    private String _id;

    @Required
    private String email;

    @Required
    private String name;

    @Required
    private String spec;

    @Required
    private String birth;

    private int exper;

    public Doctor() {
    }

    public Doctor(String email, String name, String spec, String birth, int exper) {
        this._id = email;
        this.email = email;
        this.name = name;
        this.spec = spec;
        this.birth = birth;
        this.exper = exper;
    }

    public String get_id() {
        return _id;
    }

    public void set(Doctor doctor) {
        setEmail(doctor.getEmail());
        setName(doctor.getName());
        setSpec(doctor.getSpec());
        setBirth(doctor.getBirth());
        setExper(doctor.getExper());
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getExper() {
        return exper;
    }

    public void setExper(int exper) {
        this.exper = exper;
    }
}
