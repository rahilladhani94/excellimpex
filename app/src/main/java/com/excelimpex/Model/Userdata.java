package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 3/28/2018.
 */

public class Userdata {

    @SerializedName("nSalesPersonId")
    @Expose
    private String nSalesPersonId;
    @SerializedName("sFirstName")
    @Expose
    private String sFirstName;
    @SerializedName("sLastName")
    @Expose
    private String sLastName;
    @SerializedName("sEmail")
    @Expose
    private String sEmail;
    @SerializedName("nPhone")
    @Expose
    private String nPhone;
    @SerializedName("sImage")
    @Expose
    private String sImage;

    public String getNSalesPersonId() {
        return nSalesPersonId;
    }

    public void setNSalesPersonId(String nSalesPersonId) {
        this.nSalesPersonId = nSalesPersonId;
    }

    public String getSFirstName() {
        return sFirstName;
    }

    public void setSFirstName(String sFirstName) {
        this.sFirstName = sFirstName;
    }

    public String getSLastName() {
        return sLastName;
    }

    public void setSLastName(String sLastName) {
        this.sLastName = sLastName;
    }

    public String getSEmail() {
        return sEmail;
    }

    public void setSEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getNPhone() {
        return nPhone;
    }

    public void setNPhone(String nPhone) {
        this.nPhone = nPhone;
    }

    public String getSImage() {
        return sImage;
    }

    public void setSImage(String sImage) {
        this.sImage = sImage;
    }
}
