package com.example.kenbro.Home;

public class ProjMode {
    String reg_id = null;
    String ser_id = null;
    String category = null;
    String location = null;
    String site_date = null;
    String cust_id = null;
    String art_name = null;
    String art_phone = null;
    String art_email = null;
    String art_serial = null;
    String status = null;
    String complete = null;
    String image = null;
    String install = null;
    String custom = null;
    String reg_date = null;
    String updated = null;

    public ProjMode(String reg_id, String ser_id, String category, String location, String site_date, String cust_id, String art_name, String art_phone, String art_email, String art_serial, String status, String complete, String image, String install, String custom, String reg_date, String updated) {
        this.reg_id = reg_id;
        this.ser_id = ser_id;
        this.category = category;
        this.location = location;
        this.site_date = site_date;
        this.cust_id = cust_id;
        this.art_name = art_name;
        this.art_phone = art_phone;
        this.art_email = art_email;
        this.art_serial = art_serial;
        this.status = status;
        this.complete = complete;
        this.image = image;
        this.install = install;
        this.custom = custom;
        this.reg_date = reg_date;
        this.updated = updated;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSite_date() {
        return site_date;
    }

    public void setSite_date(String site_date) {
        this.site_date = site_date;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getArt_name() {
        return art_name;
    }

    public void setArt_name(String art_name) {
        this.art_name = art_name;
    }

    public String getArt_phone() {
        return art_phone;
    }

    public void setArt_phone(String art_phone) {
        this.art_phone = art_phone;
    }

    public String getArt_email() {
        return art_email;
    }

    public void setArt_email(String art_email) {
        this.art_email = art_email;
    }

    public String getArt_serial() {
        return art_serial;
    }

    public void setArt_serial(String art_serial) {
        this.art_serial = art_serial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInstall() {
        return install;
    }

    public void setInstall(String install) {
        this.install = install;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String toString() {
        return ser_id + " " + reg_id + " " + category + " " + art_email + " " + site_date + " " + art_name + " " + cust_id + " " + art_phone + " " + art_serial + " " + complete + " " + location + " " + status + " " + install + " " + updated + " " + reg_date;
    }
}
