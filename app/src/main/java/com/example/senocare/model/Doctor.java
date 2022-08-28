package com.example.senocare.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Doctor extends RealmObject {

    @PrimaryKey
    private String _id;

    @Required
    private String bio;

    @Required
    private String birth;

    @Required
    private String email1;

    private Integer exper;

    @Required
    private String loc;

    @Required
    private String name;

    private Float rating;

    @Required
    private String sex;

    @Required
    private String spec;

    // Standard getters & setters
    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getBirth() { return birth; }
    public void setBirth(String birth) { this.birth = birth; }

    public String getEmail() { return email1; }
    public void setEmail(String email) { this.email1 = email; }

    public Integer getExper() { return exper; }
    public void setExper(Integer exper) { this.exper = exper; }

    public String getLoc() { return loc; }
    public void setLoc(String loc) { this.loc = loc; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }

    // Constructor
    public Doctor() {
        this.bio = "Hi! I'm the doctor you need.";
        this.birth = "01/01/2000";
        this.email1 = "doctor@gmail.com";
        this.exper = 5;
        this.loc = "123 st. bolsa";
        this.name = "Doctor";
        this.rating = 5.f;
        this.sex = "Male";
        this.spec = "Heart";
    }

    public Doctor(String email, String name, String sex, String birth, String bio, String loc, String spec) {
        this.bio = bio;
        this.birth = birth;
        this.email1 = email;
        this.loc = loc;
        this.name = name;
        this.sex = sex;
        this.spec = spec;
        this.exper = 5;
        this.rating = 5.f;
    }

    public Doctor(String email, String name, String sex, String birth, String spec, int exper, String loc, String bio, float rating) {
        this.bio = bio;
        this.birth = birth;
        this.email1 = email;
        this.exper = exper;
        this.loc = loc;
        this.name = name;
        this.rating = rating;
        this.sex = sex;
        this.spec = spec;
    }

    public void set(Doctor d) {
        this.bio = d.bio;
        this.birth = d.birth;
        this.email1 = d.email1;
        this.exper = d.exper;
        this.loc = d.loc;
        this.name = d.name;
        this.rating = d.rating;
        this.sex = d.sex;
        this.spec = d.spec;
    }
}