package com.excelimpex.Model.LoginManager;


import com.excelimpex.Model.Userdata;
import com.excelimpex.Model.UserdataManager;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahil on 08-01-2016.
 */
public class LoginManager {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("userdata")
    @Expose
    private List<UserdataManager> userdata = null;

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

    public List<UserdataManager> getUserdata() {
        return userdata;
    }

    public void setUserdata(List<UserdataManager> userdata) {
        this.userdata = userdata;
    }

}
