package com.rechargeapp.model;

public class AadharKYCCommisionModel {
    private String strService, strCharge;

    public AadharKYCCommisionModel(String strService, String strCharge) {
        this.strService = strService;
        this.strCharge = strCharge;

    }

    public String getStrService() {
        return strService;
    }

    public void setStrService(String strService) {
        this.strService = strService;
    }

    public String getStrCharge() {
        return strCharge;
    }

    public void setStrCharge(String strCharge) {
        this.strCharge = strCharge;
    }
}
