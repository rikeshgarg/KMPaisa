package com.rechargeapp.model;

public class RechargeCommisionReportHistoryModel {
    private String str_user_code, str_member_name,str_txn_id,str_datetime, str_commision_amount;

    public String getStr_user_code() {
        return str_user_code;
    }

    public void setStr_user_code(String str_user_code) {
        this.str_user_code = str_user_code;
    }

    public String getStr_member_name() {
        return str_member_name;
    }

    public void setStr_member_name(String str_member_name) {
        this.str_member_name = str_member_name;
    }

    public String getStr_txn_id() {
        return str_txn_id;
    }

    public void setStr_txn_id(String str_txn_id) {
        this.str_txn_id = str_txn_id;
    }

    public String getStr_datetime() {
        return str_datetime;
    }

    public void setStr_datetime(String str_datetime) {
        this.str_datetime = str_datetime;
    }

    public String getStr_commision_amount() {
        return str_commision_amount;
    }

    public void setStr_commision_amount(String str_commision_amount) {
        this.str_commision_amount = str_commision_amount;
    }

    public RechargeCommisionReportHistoryModel(String str_user_code, String str_member_name, String str_txn_id, String str_commision_amount, String str_datetime) {
        this.str_user_code = str_user_code;
        this.str_member_name = str_member_name;
        this.str_txn_id = str_txn_id;
        this.str_commision_amount = str_commision_amount;
        this.str_datetime = str_datetime;
    }


}
