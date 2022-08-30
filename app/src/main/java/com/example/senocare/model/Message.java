package com.example.senocare.model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Message extends RealmObject {
    @PrimaryKey
    private ObjectId _id;

    @Required
    private String conservation;

    @Required
    private String email2;

    @Required
    private String email1;

    @Required
    private String text;

    @Required
    private String time;

    @Required
    private String status;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }

    public String getConservation() { return conservation; }
    public void setConservation(String conservation) { this.conservation = conservation; }

    public String getReceiver() { return email2; }
    public void setReceiver(String receiver) { this.email2 = receiver; }

    public String getSender() { return email1; }
    public void setSender(String sender) { this.email1 = sender; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getStatus()  { return status; }
    public void setStatus(String status) { this.status = status; }

    // Constructor
    public Message() { }

    public Message(String conservation, String sender, String receiver, String text, String time, String status) {
        this.conservation = conservation;
        this.email2 = receiver;
        this.email1 = sender;
        this.text = text;
        this.time = time;
        this.status = status;
    }

    public void set(Message m) {
        this.conservation = m.conservation;
        this.email2 = m.email2;
        this.email1 = m.email1;
        this.text = m.text;
        this.time = m.time;
        this.status = m.status;
    }
}

