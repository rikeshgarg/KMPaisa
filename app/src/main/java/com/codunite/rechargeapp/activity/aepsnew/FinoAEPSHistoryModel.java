package com.codunite.rechargeapp.activity.aepsnew;

public class FinoAEPSHistoryModel {
    private String memberDeatil, service, txnID, aadhar_no, str_status,
            amount, mobile, datetime, bank_name;

    public FinoAEPSHistoryModel(String memberDeatil, String service, String txnID,
                                String aadhar_no, String str_status, String amount,
                                String mobile, String datetime, String bank_name) {
        this.memberDeatil = memberDeatil;
        this.service = service;
        this.txnID = txnID;
        this.aadhar_no = aadhar_no;
        this.str_status = str_status;
        this.amount = amount;
        this.mobile = mobile;
        this.datetime = datetime;
        this.bank_name = bank_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getMemberDeatil() {
        return memberDeatil;
    }

    public void setMemberDeatil(String memberDeatil) {
        this.memberDeatil = memberDeatil;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTxnID() {
        return txnID;
    }

    public void setTxnID(String txnID) {
        this.txnID = txnID;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public String getStr_status() {
        return str_status;
    }

    public void setStr_status(String str_status) {
        this.str_status = str_status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
