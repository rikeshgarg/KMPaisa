package com.codunite.rechargeapp.model;

public class RecentRechargeHistoryModel {
    private String str_amount, str_datetime,str_status, mobile="", type,strIcon;



    public RecentRechargeHistoryModel(String type, String mobile, String str_amount, String str_status, String str_icon, String str_datetime) {
        this.str_amount = str_amount;
        this.str_datetime = str_datetime;
        this.str_status = str_status;
        this.mobile = mobile;
        this.type = type;
        this.strIcon=str_icon;
    }

    public String getStr_amount() {
        return str_amount;
    }

    public void setStr_amount(String str_amount) {
        this.str_amount = str_amount;
    }

    public String getStr_datetime() {
        return str_datetime;
    }

    public void setStr_datetime(String str_datetime) {
        this.str_datetime = str_datetime;
    }

    public String getStr_status() {
        return str_status;
    }

    public void setStr_status(String str_status) {
        this.str_status = str_status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStrIcon() {
        return strIcon;
    }

    public void setStrIcon(String strIcon) {
        this.strIcon = strIcon;
    }
}
