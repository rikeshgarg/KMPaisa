package com.codunite.rechargeapp.model;

public class SettingMenu {
    public String menuName;
    public int drawable;

    public SettingMenu(String menuName, int drawable) {
        this.menuName = menuName;
        this.drawable = drawable;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getDrawable() {
        return drawable;
    }
}
