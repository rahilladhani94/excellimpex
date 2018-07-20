package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expanses {


    @SerializedName("nSalesPersonId")
    @Expose
    private String nSalesPersonId;
    @SerializedName("sTravelPlace")
    @Expose
    private String sTravelPlace;
    @SerializedName("sTravelFrom")
    @Expose
    private String sTravelFrom;
    @SerializedName("sTravelTo")
    @Expose
    private String sTravelTo;
    @SerializedName("sVisitType")
    @Expose
    private String sVisitType;
    @SerializedName("sDailyAllowance")
    @Expose
    private String sDailyAllowance;
    @SerializedName("sFare")
    @Expose
    private String sFare;
    @SerializedName("sFareImage")
    @Expose
    private String sFareImage;
    @SerializedName("sPost")
    @Expose
    private Object sPost;
    @SerializedName("sPostImage")
    @Expose
    private String sPostImage;
    @SerializedName("sXerox")
    @Expose
    private Object sXerox;
    @SerializedName("sXeroxImage")
    @Expose
    private String sXeroxImage;
    @SerializedName("sStationery")
    @Expose
    private Object sStationery;
    @SerializedName("sStationeryImage")
    @Expose
    private String sStationeryImage;
    @SerializedName("sBill")
    @Expose
    private String sBill;
    @SerializedName("sBillImage")
    @Expose
    private String sBillImage;
    @SerializedName("sTotal")
    @Expose
    private String sTotal;
    @SerializedName("dDate")
    @Expose
    private String dDate;

    public String getNSalesPersonId() {
        return nSalesPersonId;
    }

    public void setNSalesPersonId(String nSalesPersonId) {
        this.nSalesPersonId = nSalesPersonId;
    }

    public String getSTravelPlace() {
        return sTravelPlace;
    }

    public void setSTravelPlace(String sTravelPlace) {
        this.sTravelPlace = sTravelPlace;
    }

    public String getSTravelFrom() {
        return sTravelFrom;
    }

    public void setSTravelFrom(String sTravelFrom) {
        this.sTravelFrom = sTravelFrom;
    }

    public String getSTravelTo() {
        return sTravelTo;
    }

    public void setSTravelTo(String sTravelTo) {
        this.sTravelTo = sTravelTo;
    }

    public String getSVisitType() {
        return sVisitType;
    }

    public void setSVisitType(String sVisitType) {
        this.sVisitType = sVisitType;
    }

    public String getSDailyAllowance() {
        return sDailyAllowance;
    }

    public void setSDailyAllowance(String sDailyAllowance) {
        this.sDailyAllowance = sDailyAllowance;
    }

    public String getSFare() {
        return sFare;
    }

    public void setSFare(String sFare) {
        this.sFare = sFare;
    }

    public String getSFareImage() {
        return sFareImage;
    }

    public void setSFareImage(String sFareImage) {
        this.sFareImage = sFareImage;
    }

    public Object getSPost() {
        return sPost;
    }

    public void setSPost(Object sPost) {
        this.sPost = sPost;
    }

    public String getSPostImage() {
        return sPostImage;
    }

    public void setSPostImage(String sPostImage) {
        this.sPostImage = sPostImage;
    }

    public Object getSXerox() {
        return sXerox;
    }

    public void setSXerox(Object sXerox) {
        this.sXerox = sXerox;
    }

    public String getSXeroxImage() {
        return sXeroxImage;
    }

    public void setSXeroxImage(String sXeroxImage) {
        this.sXeroxImage = sXeroxImage;
    }

    public Object getSStationery() {
        return sStationery;
    }

    public void setSStationery(Object sStationery) {
        this.sStationery = sStationery;
    }

    public String getSStationeryImage() {
        return sStationeryImage;
    }

    public void setSStationeryImage(String sStationeryImage) {
        this.sStationeryImage = sStationeryImage;
    }

    public String getSBill() {
        return sBill;
    }

    public void setSBill(String sBill) {
        this.sBill = sBill;
    }

    public String getSBillImage() {
        return sBillImage;
    }

    public void setSBillImage(String sBillImage) {
        this.sBillImage = sBillImage;
    }

    public String getSTotal() {
        return sTotal;
    }

    public void setSTotal(String sTotal) {
        this.sTotal = sTotal;
    }

    public String getDDate() {
        return dDate;
    }

    public void setDDate(String dDate) {
        this.dDate = dDate;
    }
}