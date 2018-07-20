package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 3/28/2018.
 */

public class UserdataManager {

    @SerializedName("nManagerId")
    @Expose
    private String nManagerId;
    @SerializedName("sFullName")
    @Expose
    private String sFullName;
    @SerializedName("sEmail")
    @Expose
    private String sEmail;

    public String getNManagerId() {
        return nManagerId;
    }

    public void setNManagerId(String nManagerId) {
        this.nManagerId = nManagerId;
    }

    public String getSFullName() {
        return sFullName;
    }

    public void setSFullName(String sFullName) {
        this.sFullName = sFullName;
    }

    public String getSEmail() {
        return sEmail;
    }

    public void setSEmail(String sEmail) {
        this.sEmail = sEmail;
    }
}
