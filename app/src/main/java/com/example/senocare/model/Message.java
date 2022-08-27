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
    private String receiver;

    @Required
    private String sender;

    @Required
    private String text;

    @Required
    private String time;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }

    public String getConservation() { return conservation; }
    public void setConservation(String conservation) { this.conservation = conservation; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    // Constructor
    public Message() { }

    public Message(String conservation, String sender, String receiver, String text, String time) {
        this.conservation = conservation;
        this.receiver = receiver;
        this.sender = sender;
        this.text = text;
        this.time = time;
    }

    public void set(Message m) {
        this.conservation = m.conservation;
        this.receiver = m.receiver;
        this.sender = m.sender;
        this.text = m.text;
        this.time = m.time;
    }
}

