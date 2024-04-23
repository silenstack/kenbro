package com.example.kenbro.Home;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class InventSession {
    private static final String preference = "Abashwili";
    private static final String kserial_no = "serial_no";
    private static final String kfname = "fname";
    private static final String klname = "lname";
    private static final String kemail = "email";
    private static final String kphone = "phone";
    private static final String krole = "role";
    private static final String kusername = "username";
    private static final String kstatus = "status";
    private static final String kreg_date = "reg_date";
    private static final String keyoff = "expires";
    private static final String keyempty = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    //serial_no, fname, lname, email, phone, role, username, status, date
    public InventSession(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(preference, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param fname
     * @param email
     */
    //entry_no, fname, lname, email, phone, residence, username, status, date;
    public void loginInvent(String serial_no, String fname, String lname, String email, String phone, String role, String username,
                            String status, String reg_date) {
        mEditor.putString(kserial_no, serial_no);
        mEditor.putString(kfname, fname);
        mEditor.putString(klname, lname);
        mEditor.putString(kemail, email);
        mEditor.putString(kphone, phone);
        mEditor.putString(krole, role);
        mEditor.putString(kusername, username);
        mEditor.putString(kstatus, status);
        mEditor.putString(kreg_date, reg_date);

        Date date = new Date();

        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(keyoff, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean loggedInvent() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(keyoff, 0);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);


        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public StaffModel getInventDetails() {

        if (!loggedInvent()) {
            return null;
        }
        StaffModel custModel = new StaffModel();
        custModel.setSerial_no(mPreferences.getString(kserial_no, keyempty));
        custModel.setFname(mPreferences.getString(kfname, keyempty));
        custModel.setLname(mPreferences.getString(klname, keyempty));
        custModel.setEmail(mPreferences.getString(kemail, keyempty));
        custModel.setPhone(mPreferences.getString(kphone, keyempty));
        custModel.setRole(mPreferences.getString(krole, keyempty));
        custModel.setUsername(mPreferences.getString(kusername, keyempty));
        custModel.setStatus(mPreferences.getString(kstatus, keyempty));
        custModel.setDate(mPreferences.getString(kreg_date, keyempty));
        //entry_no, fname, lname, email, phone, residence, username, status, date;
        return custModel;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutInvent() {
        mEditor.clear();
        mEditor.commit();
    }
}
