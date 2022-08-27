package com.example.senocare.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SpecialtyModel implements Parcelable {
    String name;
    String img_url;

    public SpecialtyModel() {
    }

    public SpecialtyModel(String name, String img_url) {
        this.name = name;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(img_url);
    }

    public static final Parcelable.Creator<SpecialtyModel> CREATOR = new Creator<SpecialtyModel>() {
        @Override
        public SpecialtyModel createFromParcel(Parcel source) {
            return new SpecialtyModel(source);
        }

        @Override
        public SpecialtyModel[] newArray(int size) {
            return new SpecialtyModel[size];
        }
    };

    private SpecialtyModel(Parcel source) {
        name = source.readString();
        img_url = source.readString();
    }
}
