package com.example.kenbro.Home;

public class BenaMode {
    //id,payid,driver_id,driver_name,driver_phone,driver_email,cust_id,cust_name,cust_phone
//location,landmark,house,drive,custom,entry_date,update_date
    String id = null;
    String payid = null;
    String driver_id = null;
    String driver_name = null;
    String driver_phone = null;
    String driver_email = null;
    String license = null;
    String cust_id = null;
    String cust_name = null;
    String cust_phone = null;
    String location = null;
    String landmark = null;
    String house = null;
    String drive = null;
    String custom = null;
    String entry_date = null;
    String update_date = null;

    public BenaMode(String id, String payid, String driver_id, String driver_name, String driver_phone, String driver_email, String license, String cust_id, String cust_name, String cust_phone, String location, String landmark, String house, String drive, String custom, String entry_date, String update_date) {
        this.id = id;
        this.payid = payid;
        this.driver_id = driver_id;
        this.driver_name = driver_name;
        this.driver_phone = driver_phone;
        this.driver_email = driver_email;
        this.license = license;
        this.cust_id = cust_id;
        this.cust_name = cust_name;
        this.cust_phone = cust_phone;
        this.location = location;
        this.landmark = landmark;
        this.house = house;
        this.drive = drive;
        this.custom = custom;
        this.entry_date = entry_date;
        this.update_date = update_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public String getDriver_email() {
        return driver_email;
    }

    public void setDriver_email(String driver_email) {
        this.driver_email = driver_email;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
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

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    @Override
    public String toString() {
        return id + " " + payid + " " + license + " " + driver_id + " " + driver_name + " " + driver_phone + " " + driver_email + " " + cust_id + " " + cust_name + " " + cust_phone + " " + location + " " + landmark + " " + house + " " + drive + " " + custom + " " + entry_date + " " + update_date;
    }
}
