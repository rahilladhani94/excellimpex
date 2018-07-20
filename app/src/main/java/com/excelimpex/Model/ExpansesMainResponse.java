package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android on 4/14/2018.
 */

public class ExpansesMainResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("expancelist")
    @Expose
    private List<Expanses> expancelist = null;

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

    public List<Expanses> getExpancelist() {
        return expancelist;
    }

    public void setExpancelist(List<Expanses> expancelist) {
        this.expancelist = expancelist;
    }

}
