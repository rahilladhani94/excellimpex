package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryMain {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;



    @SerializedName("categorylist")
    @Expose

    private List<CategoryList> categorylist = null;

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
    public List<CategoryList> getCategorylist() {
        return categorylist;
    }

    public void setCategorylist(List<CategoryList> categorylist) {
        this.categorylist = categorylist;
    }

}
