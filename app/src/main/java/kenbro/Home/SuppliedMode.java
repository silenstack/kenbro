package com.example.kenbro.Home;

public class SuppliedMode {
    String sup_id = null;
    String pur_id = null;
    String category = null;
    String type = null;
    String quantity = null;
    String price = null;
    String description = null;
    String image = null;
    String supplier = null;
    String status = null;
    String disburse = null;
    String reg_date = null;

    public SuppliedMode(String sup_id, String pur_id, String category, String type, String quantity, String price, String description, String image, String supplier, String status, String disburse, String reg_date) {
        this.sup_id = sup_id;
        this.pur_id = pur_id;
        this.category = category;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.image = image;
        this.supplier = supplier;
        this.status = status;
        this.disburse = disburse;
        this.reg_date = reg_date;
    }

    public String getSup_id() {
        return sup_id;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public String getPur_id() {
        return pur_id;
    }

    public void setPur_id(String pur_id) {
        this.pur_id = pur_id;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisburse() {
        return disburse;
    }

    public void setDisburse(String disburse) {
        this.disburse = disburse;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return sup_id + " " + pur_id + " " + category + " " + type + " " + quantity + " " + price + " " + description + " " + image + " " + supplier + " " + status + " " + disburse + " " + reg_date;
    }
}

