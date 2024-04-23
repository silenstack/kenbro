package com.example.kenbro.Home;

public class CashMode {
    String supplier = null;
    String amount = null;
    String paid = null;
    String fullname = null;
    String phone = null;

    public CashMode(String supplier, String amount, String paid, String fullname, String phone) {
        this.supplier = supplier;
        this.amount = amount;
        this.paid = paid;
        this.fullname = fullname;
        this.phone = phone;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
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

    public String toString() {
        return supplier + " " + amount + " " + paid + " " + phone + " " + fullname;
    }
}