package com.example.kenbro.Home;

public class PayModel {
    String payid = null;
    String entry = null;
    String mpesa = null;
    String amount = null;
    String orders = null;
    String ship = null;
    String cust_id = null;
    String name = null;
    String phone = null;
    String location = null;
    String landmark = null;
    String house = null;
    String status = null;
    String shipping = null;
    String comment = null;
    String reg_date = null;

    public PayModel(String payid, String entry, String mpesa, String amount, String orders, String ship, String cust_id, String name, String phone, String location, String landmark, String house, String status, String shipping, String comment, String reg_date) {
        this.payid = payid;
        this.entry = entry;
        this.mpesa = mpesa;
        this.amount = amount;
        this.orders = orders;
        this.ship = ship;
        this.cust_id = cust_id;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.landmark = landmark;
        this.house = house;
        this.status = status;
        this.shipping = shipping;
        this.comment = comment;
        this.reg_date = reg_date;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
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

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return payid + " " + entry + " " + mpesa + " " + amount + " " + orders + " " + ship + " " + cust_id + " " + name + " " + phone + " " + landmark + " " + location + " " + status + " " + shipping + " " + comment + " " + reg_date;
    }
}

