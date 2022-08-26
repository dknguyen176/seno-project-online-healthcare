package com.example.senocare.model;

import java.io.Serializable;

public class SpecialtyModel implements Serializable {
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
}
