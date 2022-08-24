package com.example.senocare.model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Patient extends RealmObject {
    @PrimaryKey
    private ObjectId _id;
    @Required
    private String name;
    private int age;

    public Patient() {
    }

    public Patient(String name, int age) {
        this._id = new ObjectId();
        this.name = name;
        this.age = age;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
