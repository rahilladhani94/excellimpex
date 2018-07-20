package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android on 3/28/2018.
 */

public class ProductlistMain {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("productlist")
    @Expose
    private List<Productlist> productlist = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Productlist> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<Productlist> productlist) {
        this.productlist = productlist;
    }
}
