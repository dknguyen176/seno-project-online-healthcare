package com.example.senocare.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.senocare.helper.TimeConverter;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Doctor extends RealmObject implements Parcelable {

    @PrimaryKey
    private String _id;

    @Required
    private String bio;

    @Required
    private Date birth;

    @Required
    private String email1;

    private Integer exper;

    @Required
    private String loc;

    @Required
    private String first;

    @Required
    private String last;

    private Float rating;

    @Required
    private String sex;

    @Required
    private String spec;

    private byte[] img;

    // Standard getters & setters
    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public Date getBirth() { return birth; }
    public void setBirth(Date birth) { this.birth = birth; }

    public String getEmail() { return email1; }
    public void setEmail(String email) { this.email1 = email; }

    public Integer getExper() { return exper; }
    public void setExper(Integer exper) { this.exper = exper; }

    public String getLoc() { return loc; }
    public void setLoc(String loc) { this.loc = loc; }

    public String getName() { return first + " " + last; }
    public void setName(String first, String last) { this.first = first; this.last = last; }

    public String getFirst() { return first; }
    public void setFirst(String first) { this.first = first; }

    public String getLast() { return last; }
    public void setLast(String last) { this.last = last; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }

    public byte[] getImg() { return img; }
    public void setImg(byte[] img) { this.img = img; }

    // Constructor
    public Doctor() {
        this.bio = "Hi! I'm the doctor you need.";
        this.birth = TimeConverter.toDate("01/01/2000", "dd/MM/yyyy");
        this.email1 = "doctor@gmail.com";
        this.exper = 5;
        this.loc = "123 st. bolsa";
        this.first = "first";
        this.last = "Doctor";
        this.rating = 5.f;
        this.sex = "Male";
        this.spec = "Heart";
    }

    public Doctor(String email, String first, String last, String sex, Date birth, String bio, String loc, String spec) {
        this.bio = bio;
        this.birth = birth;
        this.email1 = email;
        this.loc = loc;
        this.first = first;
        this.last = last;
        this.sex = sex;
        this.spec = spec;
        this.exper = 5;
        this.rating = 5.f;
    }

    public Doctor(String email, String first, String last, String sex, Date birth, String spec, int exper, String loc, String bio, float rating) {
        this.bio = bio;
        this.birth = birth;
        this.email1 = email;
        this.exper = exper;
        this.loc = loc;
        this.first = first;
        this.last = last;
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
        this.first = d.first;
        this.last = d.last;
        this.rating = d.rating;
        this.sex = d.sex;
        this.spec = d.spec;
        this.img = d.img;
    }

    public Doctor(Parcel source) {
        _id = source.readString();
        email1 = source.readString();
        first = source.readString();
        last = source.readString();
        sex = source.readString();
        birth = new Date(source.readLong());
        spec = source.readString();
        exper = source.readInt();
        loc = source.readString();
        bio = source.readString();
        rating = source.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(email1);
        dest.writeString(first);
        dest.writeString(last);
        dest.writeString(sex);
        dest.writeLong(birth.getTime());
        dest.writeString(spec);
        dest.writeInt(exper);
        dest.writeString(loc);
        dest.writeString(bio);
        dest.writeFloat(rating);
    }

    public static final Parcelable.Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel source) {
            return new Doctor(source);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
}