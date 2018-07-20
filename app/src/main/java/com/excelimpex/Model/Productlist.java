package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 3/28/2018.
 */

public class Productlist {

    @SerializedName("nProductId")
    @Expose
    private String nProductId;
    @SerializedName("sProductName")
    @Expose
    private String sProductName;
    @SerializedName("nPrice")
    @Expose
    private String nPrice;
    @SerializedName("sImage")
    @Expose
    private String sImage;

    public String getNProductId() {
        return nProductId;
    }

    public void setNProductId(String nProductId) {
        this.nProductId = nProductId;
    }

    public String getSProductName() {
        return sProductName;
    }

    public void setSProductName(String sProductName) {
        this.sProductName = sProductName;
    }

    public String getNPrice() {
        return nPrice;
    }

    public void setNPrice(String nPrice) {
        this.nPrice = nPrice;
    }

    public String getSImage() {
        return sImage;
    }

    public void setSImage(String sImage) {
        this.sImage = sImage;
    }
}
