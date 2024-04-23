package com.example.kenbro.Home;

public class ForgiveMode {
    String mpesa = null;
    String supplier = null;
    String fullname = null;
    String amount = null;
    String phone = null;
    String date = null;

    public ForgiveMode(String mpesa, String supplier, String fullname, String amount, String phone, String date) {
        this.mpesa = mpesa;
        this.supplier = supplier;
        this.fullname = fullname;
        this.amount = amount;
        this.phone = phone;
        this.date = date;
    }

    public String getMpesa() {
        return mpesa;
    }

    public void setMpesa(String mpesa) {
        this.mpesa = mpesa;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return supplier + " " + fullname + " " + mpesa + " " + amount + " " + date + " " + phone;
    }
}