package com.example.senocare.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Patient extends RealmObject implements Parcelable {
    @PrimaryKey
    private String _id;

    @Required
    private String address;

    @Required
    private String birth;

    @Required
    private String email1;

    @Required
    private String name;

    @Required
    private String phone;

    @Required
    private String sex;

    // Standard getters & setters
    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBirth() { return birth; }
    public void setBirth(String birth) { this.birth = birth; }

    public String getEmail() { return email1; }
    public void setEmail(String email) { this.email1 = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    // Constructor
    public Patient() {
        this.address = "123 st. bolsa";
        this.birth = "01/01/2000";
        this.email1 = "patient@gmail.com";
        this.name = "patient";
        this.phone = "0123456789";
        this.sex = "Male";
    }

    public Patient(String email, String name, String sex, String birth, String phone, String address) {
        this.address = address;
        this.birth = birth;
        this.email1 = email;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
    }

    public void set(Patient p) {
        this.address = p.address;
        this.birth = p.birth;
        this.email1 = p.email1;
        this.name = p.name;
        this.phone = p.phone;
        this.sex = p.sex;
    }

    public Patient(Parcel source) {
        _id = source.readString();
        email1 = source.readString();
        name = source.readString();
        sex = source.readString();
        birth = source.readString();
        phone = source.readString();
        address = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(email1);
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeString(birth);
        dest.writeString(phone);
        dest.writeString(address);
    }

    public static final Parcelable.Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel source) {
            return new Patient(source);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}