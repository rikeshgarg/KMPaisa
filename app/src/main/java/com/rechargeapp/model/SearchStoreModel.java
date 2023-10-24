package com.rechargeapp.model;

public class SearchStoreModel {

    private String businessName, address, mobile, pincode, state, city, description, profile;

    public SearchStoreModel(String businessName, String address, String mobile, String pincode, String state, String city, String description, String profile) {
        this.businessName = businessName;
        this.address = address;
        this.mobile = mobile;
        this.pincode = pincode;
        this.state = state;
        this.city = city;
        this.description = description;
        this.profile = profile;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}