package com.example.kenbro.Home;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SupSession {
    private static final String preference = "Amina";
    private static final String kentry_no = "entry_no";
    private static final String kbusiness_no = "business_no";
    private static final String kfname = "fname";
    private static final String klname = "lname";
    private static final String kemail = "email";
    private static final String kphone = "phone";
    private static final String kaddress = "address";
    private static final String kusername = "username";
    private static final String kstatus = "status";
    private static final String kreg_date = "reg_date";
    private static final String keyoff = "expires";
    private static final String keyempty = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    //entry_no, license, fname, lname, email, phone, address, username, status, date;
    public SupSession(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(preference, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    //entry_no, fname, lname, email, phone, residence, username, status, date;
    public void loginSup(String entry_no, String business_no, String fname, String lname, String email, String phone, String address, String username,
                         String status, String reg_date) {
        mEditor.putString(kentry_no, entry_no);
        mEditor.putString(kbusiness_no, business_no);
        mEditor.putString(kfname, fname);
        mEditor.putString(klname, lname);
        mEditor.putString(kemail, email);
        mEditor.putString(kphone, phone);
        mEditor.putString(kaddress, address);
        mEditor.putString(kusername, username);
        mEditor.putString(kstatus, status);
        mEditor.putString(kreg_date, reg_date);

        Date date = new Date();

        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(keyoff, millis);
        mEditor.commit();
    }

    public boolean loggedSup() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(keyoff, 0);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);


        return currentDate.before(expiryDate);
    }

    public SupModel getSupDetails() {

        if (!loggedSup()) {
            return null;
        }
        SupModel custModel = new SupModel();
        custModel.setEntry_no(mPreferences.getString(kentry_no, keyempty));
        custModel.setBusiness_no(mPreferences.getString(kbusiness_no, keyempty));
        custModel.setFname(mPreferences.getString(kfname, keyempty));
        custModel.setLname(mPreferences.getString(klname, keyempty));
        custModel.setEmail(mPreferences.getString(kemail, keyempty));
        custModel.setPhone(mPreferences.getString(kphone, keyempty));
        custModel.setAddress(mPreferences.getString(kaddress, keyempty));
        custModel.setUsername(mPreferences.getString(kusername, keyempty));
        custModel.setStatus(mPreferences.getString(kstatus, keyempty));
        custModel.setDate(mPreferences.getString(kreg_date, keyempty));
        //entry_no, fname, lname, email, phone, residence, username, status, date;
        return custModel;
    }

    public void logoutSup() {
        mEditor.clear();
        mEditor.commit();
    }
}
