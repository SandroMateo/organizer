package com.example.guest.organizer.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 12/13/16.
 */

@Parcel
public class Task {
    String detail;
    String type;
    String signifier;
    String status;
    String day;
    String month;
    String collection;
    String year;
    String pushId;

    public Task(){}

    public Task(String detail, String type) {
        this.detail = detail;
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public String getSignifier() {
        return signifier;
    }

    public String getStatus() {
        return status;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getPushId() {
        return pushId;
    }

    public String getCollection() {
        return collection;
    }

    public void setSignifier(String signifier) {
        this.signifier = signifier;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}

