package com.rechargeapp.model;

public class SearchMemberListModel {
    private String user_id, user_code, name, email, mobile, wallet_balance,e_wallet_balance;

    public SearchMemberListModel(String user_id, String user_code,String name,String email,String mobile, String wallet_balance, String e_wallet_balance) {
        this.user_id = user_id;
        this.user_code = user_code;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.wallet_balance = wallet_balance;
        this.e_wallet_balance = e_wallet_balance;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWallet_balance() {
        return wallet_balance;
    }

    public void setWallet_balance(String wallet_balance) {
        this.wallet_balance = wallet_balance;
    }

    public String getE_wallet_balance() {
        return e_wallet_balance;
    }

    public void setE_wallet_balance(String e_wallet_balance) {
        this.e_wallet_balance = e_wallet_balance;
    }
}
