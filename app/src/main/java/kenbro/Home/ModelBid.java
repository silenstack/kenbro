package com.example.kenbro.Home;

public class ModelBid {
    String id = null;
    String category = null;
    String type = null;
    String price = null;
    String qty = null;
    String quantity = null;
    String reg_date = null;

    public ModelBid(String id, String category, String type, String price, String qty, String quantity, String reg_date) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.price = price;
        this.qty = qty;
        this.quantity = quantity;
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return id + " " + category + " " + qty + " " + quantity + " " + reg_date + " " + type + " " + price;
    }
}
