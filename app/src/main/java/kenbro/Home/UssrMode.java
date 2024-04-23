package com.example.kenbro.Home;

public class UssrMode {
    String payid = null;
    String ser_id = null;
    String mpesa = null;
    String amount = null;
    String cust_id = null;
    String fullname = null;
    String phone = null;
    String location = null;
    String landmark = null;
    String house = null;
    String status = null;
    String reg_date = null;

    public UssrMode(String payid, String ser_id, String mpesa, String amount, String cust_id, String fullname, String phone, String location, String landmark, String house, String status, String reg_date) {
        this.payid = payid;
        this.ser_id = ser_id;
        this.mpesa = mpesa;
        this.amount = amount;
        this.cust_id = cust_id;
        this.fullname = fullname;
        this.phone = phone;
        this.location = location;
        this.landmark = landmark;
        this.house = house;
        this.status = status;
        this.reg_date = reg_date;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getMpesa() {
        return mpesa;
    }

    public void setMpesa(String mpesa) {
        this.mpesa = mpesa;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return payid + " " + ser_id + " " + mpesa + " " + amount + " " + cust_id + " " + fullname + " " + phone + " " + landmark + " " + location + " " + status + " " + reg_date;
    }
}
