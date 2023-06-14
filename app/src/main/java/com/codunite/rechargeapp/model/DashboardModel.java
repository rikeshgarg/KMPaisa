package com.codunite.rechargeapp.model;

public class DashboardModel {
    private String name;
    private int drawble;
    private String imgUrl;
    private boolean isChecked = false;

    private int colorName;

    public DashboardModel(String name, int drawble, boolean isChecked,int colorName) {
        this.name = name;
        this.drawble = drawble;
        this.isChecked = isChecked;
        this.colorName = colorName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawble() {
        return drawble;
    }

    public void setDrawble(int drawble) {
        this.drawble = drawble;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getColorName() {
        return colorName;
    }

    public void setColorName(int colorName) {
        this.colorName = colorName;
    }
}
