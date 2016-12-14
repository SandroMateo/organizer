package com.example.guest.organizer.models;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by Guest on 12/13/16.
 */

@Parcel
public class Task {
    String detail;
    String type;
    List<String> signifier;
    String status;
    String date;
    String collection;
    String pushId;

    public Task(){}

    public Task(String detail, String type, String date) {
        this.detail = detail;
        this.type = type;
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public List<String> getSignifier() {
        return signifier;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getPushId() {
        return pushId;
    }

    public String getCollection() {
        return collection;
    }

    public void addSignifier(String signifier) {
        this.signifier.add(signifier);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}

