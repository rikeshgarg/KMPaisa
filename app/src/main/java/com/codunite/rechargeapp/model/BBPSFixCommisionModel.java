package com.codunite.rechargeapp.model;

public class BBPSFixCommisionModel {
    private String strName, strCommission,strflat,strsurcharge;

    public BBPSFixCommisionModel(String strName, String strCommission, String strflat, String strsurcharge) {
        this.strName = strName;
        this.strCommission = strCommission;
        this.strflat = strflat;
        this.strsurcharge = strsurcharge;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrCommission() {
        return strCommission;
    }

    public void setStrCommission(String strCommission) {
        this.strCommission = strCommission;
    }

    public String getStrflat() {
        return strflat;
    }

    public void setStrflat(String strflat) {
        this.strflat = strflat;
    }

    public String getStrsurcharge() {
        return strsurcharge;
    }

    public void setStrsurcharge(String strsurcharge) {
        this.strsurcharge = strsurcharge;
    }
}
