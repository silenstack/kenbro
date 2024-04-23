package com.example.kenbro.Home;

public class Monie {
    String amount = null;
    String orders = null;
    String service = null;
    String supplier = null;
    String balance = null;
    String regdate = null;
    String updated = null;

    public Monie(String amount, String orders, String service, String supplier, String balance, String regdate, String updated) {
        this.amount = amount;
        this.orders = orders;
        this.service = service;
        this.supplier = supplier;
        this.balance = balance;
        this.regdate = regdate;
        this.updated = updated;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
