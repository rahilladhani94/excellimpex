package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistributorList {
    @SerializedName("nSalesPersonId")
    @Expose
    private String nSalesPersonId;
    @SerializedName("nSuperId")
    @Expose
    private String nSuperId;
    @SerializedName("sFullName")
    @Expose
    private String sFullName;
    @SerializedName("nPhone")
    @Expose
    private String nPhone;
    @SerializedName("sAddress")
    @Expose
    private String sAddress;

    public String getNSalesPersonId() {
        return nSalesPersonId;
    }

    public void setNSalesPersonId(String nSalesPersonId) {
        this.nSalesPersonId = nSalesPersonId;
    }

    public String getNSuperId() {
        return nSuperId;
    }

    public void setNSuperId(String nSuperId) {
        this.nSuperId = nSuperId;
    }

    public String getSFullName() {
        return sFullName;
    }

    public void setSFullName(String sFullName) {
        this.sFullName = sFullName;
    }

    public String getNPhone() {
        return nPhone;
    }

    public void setNPhone(String nPhone) {
        this.nPhone = nPhone;
    }

    public String getSAddress() {
        return sAddress;
    }

    public void setSAddress(String sAddress) {
        this.sAddress = sAddress;
    }
}
