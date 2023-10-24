package com.rechargeapp.activity.aepsnew.finoaepspayout;

public class NewAepsPayoutBeneModel {
    private String str_id;
    private String str_benId;
    private String str_name;
    private String str_account_no;
    private String str_bank_name;
    private String str_ifsc;
    private String str_is_verified;
    private String str_verify_url;
    private String str_date;

    public NewAepsPayoutBeneModel(String str_id, String str_benId, String str_name, String str_account_no, String str_bank_name, String str_ifsc, String str_is_verified, String str_verify_url, String str_date) {
        this.str_id = str_id;
        this.str_benId = str_benId;
        this.str_name = str_name;
        this.str_account_no = str_account_no;
        this.str_bank_name = str_bank_name;
        this.str_ifsc = str_ifsc;
        this.str_is_verified = str_is_verified;
        this.str_verify_url = str_verify_url;
        this.str_date = str_date;
    }

    public String getStr_benId() {
        return str_benId;
    }

    public void setStr_benId(String str_benId) {
        this.str_benId = str_benId;
    }

    public String getStr_name() {
        return str_name;
    }

    public void setStr_name(String str_name) {
        this.str_name = str_name;
    }

    public String getStr_account_no() {
        return str_account_no;
    }

    public void setStr_account_no(String str_account_no) {
        this.str_account_no = str_account_no;
    }

    public String getStr_bank_name() {
        return str_bank_name;
    }

    public void setStr_bank_name(String str_bank_name) {
        this.str_bank_name = str_bank_name;
    }

    public String getStr_ifsc() {
        return str_ifsc;
    }

    public void setStr_ifsc(String str_ifsc) {
        this.str_ifsc = str_ifsc;
    }

    public String getStr_is_verified() {
        return str_is_verified;
    }

    public void setStr_is_verified(String str_is_verified) {
        this.str_is_verified = str_is_verified;
    }

    public String getStr_verify_url() {
        return str_verify_url;
    }

    public void setStr_verify_url(String str_verify_url) {
        this.str_verify_url = str_verify_url;
    }

    public String getStr_date() {
        return str_date;
    }

    public void setStr_date(String str_date) {
        this.str_date = str_date;
    }

}