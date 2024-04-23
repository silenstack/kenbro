package com.example.kenbro.Home;

public class StagedAda {
    String reference = null;
    String user = null;
    String phone = null;
    String name = null;
    String message = null;
    String date = null;
    String time = null;

    public StagedAda(String reference, String user, String phone, String name, String message, String date, String time) {
        this.reference = reference;
        this.user = user;
        this.phone = phone;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return user + " " + phone + " " + time + " " + name + " " + message + " " + date;
    }
}

