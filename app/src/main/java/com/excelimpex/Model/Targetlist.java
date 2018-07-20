package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 3/28/2018.
 */

public class Targetlist {

    @SerializedName("nSalesPersonId")
    @Expose
    private String nSalesPersonId;
    @SerializedName("nProductId")
    @Expose
    private String nProductId;
    @SerializedName("sTarget")
    @Expose
    private String sTarget;


    @SerializedName("sQty")
    @Expose

    private String sQty;
    @SerializedName("sMonth")
    @Expose
    private String sMonth;
    @SerializedName("sProductName")
    @Expose
    private String sProductName;

    @SerializedName("nCatId")
    @Expose
    private String nCatId;


    public String getNSalesPersonId() {
        return nSalesPersonId;
    }

    public void setNSalesPersonId(String nSalesPersonId) {
        this.nSalesPersonId = nSalesPersonId;
    }

    public String getNProductId() {
        return nProductId;
    }

    public void setNProductId(String nProductId) {
        this.nProductId = nProductId;
    }

    public String getSTarget() {
        return sTarget;
    }

    public void setSTarget(String sTarget) {
        this.sTarget = sTarget;
    }

    public String getSQty() {
        return sQty;
    }

    public void setSQty(String sQty) {
        this.sQty = sQty;
    }

    public String getSMonth() {
        return sMonth;
    }

    public void setSMonth(String sMonth) {
        this.sMonth = sMonth;
    }
    public String getSProductName() {
        return sProductName;
    }

    public void setSProductName(String sProductName) {
        this.sProductName = sProductName;
    }

    public String getnCatId() {
        return nCatId;
    }

    public void setnCatId(String nCatId) {
        this.nCatId = nCatId;
    }

}
