package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryList {
    @SerializedName("nCatId")
    @Expose
    private String nCatId;
    @SerializedName("sName")
    @Expose
    private String sName;
    @SerializedName("nBasePrice")
    @Expose
    private String nBasePrice;
    @SerializedName("nSalePrice")
    @Expose
    private String nSalePrice;

    public String getNCatId() {
        return nCatId;
    }

    public void setNCatId(String nCatId) {
        this.nCatId = nCatId;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }
    public String getNBasePrice() {
        return nBasePrice;
    }

    public void setNBasePrice(String nBasePrice) {
        this.nBasePrice = nBasePrice;
    }

    public String getNSalePrice() {
        return nSalePrice;
    }

    public void setNSalePrice(String nSalePrice) {
        this.nSalePrice = nSalePrice;
    }
}
