package com.rechargeapp.activity.virtualaccount;

public class VirtualTransactionReportModel {

    private String virtualAccount, mode, utr, bankTxnID, amount, payerName, payerAccount, remark, dateTime;

    public VirtualTransactionReportModel(String virtualAccount, String mode, String utr, String bankTxnID, String amount, String payerName, String payerAccount, String remark, String dateTime) {
        this.virtualAccount = virtualAccount;
        this.mode = mode;
        this.utr = utr;
        this.bankTxnID = bankTxnID;
        this.amount = amount;
        this.payerName = payerName;
        this.payerAccount = payerAccount;
        this.remark = remark;
        this.dateTime = dateTime;
    }

    public String getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(String virtualAccount) {
        this.virtualAccount = virtualAccount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getBankTxnID() {
        return bankTxnID;
    }

    public void setBankTxnID(String bankTxnID) {
        this.bankTxnID = bankTxnID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}