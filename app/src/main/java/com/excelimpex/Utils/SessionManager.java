package com.excelimpex.Utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    public static final String PREFERENCE_NAME = "com.hourstack";

    private Context context;
    private SharedPreferences sharedPreferences;


    private static final String KEY_USER_ID = "nUserId";
    private static final String KEY_FNAME = "sFirstName";
    private static final String KEY_LNAME = "sLastName";
    private static final String KEY_EMAIL = "sEmail";
    private static final String KEY_PHONE = "nPhone";
    private static final String KEY_IMAGE = "sImage";
    private static final String KEY_USERTYPE = "sUserType";
    private static final String KEY_LOGINWORK = "loginWork";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

    }


    public boolean isLogin() {
        return sharedPreferences.getString(KEY_USER_ID, "-1").equalsIgnoreCase("-1") ? false : true;
    }


    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, "");
    }

    public void setKeyUserId(String userId) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply();
    }


    public String getKeyFirstName() {
        return sharedPreferences.getString(KEY_FNAME, "");
    }
    public void setKeyFirstName(String userId) {
        sharedPreferences.edit().putString(KEY_FNAME, userId).apply();
    }





    public String getKeyLastName() {
        return sharedPreferences.getString(KEY_LNAME, "");
    }


    public void setKeyLastName(String userId) {
        sharedPreferences.edit().putString(KEY_LNAME, userId).apply();
    }



    public String getKeyEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }
    public void setKeyEmail(String userId) {
        sharedPreferences.edit().putString(KEY_EMAIL, userId).apply();
    }

    public String getKeyPhone() {
        return sharedPreferences.getString(KEY_PHONE, "");
    }
    public void setKeyPhone(String userId) {
        sharedPreferences.edit().putString(KEY_PHONE, userId).apply();
    }


    public String getKeyImage() {
        return sharedPreferences.getString(KEY_IMAGE, "");
    }
    public void setKeyImage(String image) {
        sharedPreferences.edit().putString(KEY_IMAGE, image).apply();
    }
    public String getKeyUsertype() {
        return sharedPreferences.getString(KEY_USERTYPE, "");
    }
    public void setKeyUsertype(String userId) {
        sharedPreferences.edit().putString(KEY_USERTYPE, userId).apply();
    }

    public String getKeyLoginwork() {
        return sharedPreferences.getString(KEY_LOGINWORK, "0");
    }
    public void setKeyLoginwork(String userId) {
        sharedPreferences.edit().putString(KEY_LOGINWORK, userId).apply();
    }
    public boolean removeUserDetail() {

        return sharedPreferences.edit().clear().commit();



    }




}
