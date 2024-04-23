package com.example.kenbro.Home;

public class ServMode {
    String ser_id = null;
    String category = null;
    String checker = null;
    String image = null;
    String description = null;
    String site_date = null;
    String price = null;
    String size = null;
    String cust_id = null;
    String fullname = null;
    String phone = null;
    String location = null;
    String landmark = null;
    String house = null;
    String reg_date = null;
    String status = null;
    String pay = null;
    String fina = null;
    String insta = null;
    String updated = null;

    public ServMode(String ser_id, String category, String checker, String image, String description, String site_date, String price, String size, String cust_id, String fullname, String phone, String location, String landmark, String house, String reg_date, String status, String pay, String fina, String insta, String updated) {
        this.ser_id = ser_id;
        this.category = category;
        this.checker = checker;
        this.image = image;
        this.description = description;
        this.site_date = site_date;
        this.price = price;
        this.size = size;
        this.cust_id = cust_id;
        this.fullname = fullname;
        this.phone = phone;
        this.location = location;
        this.landmark = landmark;
        this.house = house;
        this.reg_date = reg_date;
        this.status = status;
        this.pay = pay;
        this.fina = fina;
        this.insta = insta;
        this.updated = updated;
    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite_date() {
        return site_date;
    }

    public void setSite_date(String site_date) {
        this.site_date = site_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getFina() {
        return fina;
    }

    public void setFina(String fina) {
        this.fina = fina;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String toString() {
        return ser_id + " " + description + " " + category + " " + fullname + " " + site_date + " " + size + " " + cust_id + " " + pay + " " + phone + " " + landmark + " " + location + " " + status + " " + insta + " " + updated + " " + reg_date;
    }
}
