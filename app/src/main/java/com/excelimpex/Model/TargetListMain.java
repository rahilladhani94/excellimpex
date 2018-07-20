package com.excelimpex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android on 3/28/2018.
 */

public class TargetListMain {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("nTotalTarget")
    @Expose
    private String nTotalTarget;
    @SerializedName("nTotalAchived")
    @Expose
    private String nTotalAchived;
    @SerializedName("targetlist")
    @Expose
    private List<Targetlist> targetlist = null;

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

    public List<Targetlist> getTargetlist() {
        return targetlist;
    }

    public void setTargetlist(List<Targetlist> targetlist) {
        this.targetlist = targetlist;
    }
    public String getNTotalTarget() {
        return nTotalTarget;
    }

    public void setNTotalTarget(String nTotalTarget) {
        this.nTotalTarget = nTotalTarget;
    }

    public String getNTotalAchived() {
        return nTotalAchived;
    }

    public void setNTotalAchived(String nTotalAchived) {
        this.nTotalAchived = nTotalAchived;
    }
}
