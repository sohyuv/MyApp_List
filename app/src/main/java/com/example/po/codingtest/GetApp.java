package com.example.po.codingtest;

import android.graphics.drawable.Drawable;

public class GetApp {

    public Drawable image;
    public String appName;
    public String appPackage;

    public Drawable getImage() {
        return image;
    }

    public void setimage(Drawable image) {
        this.image = image;
    }

    public String getappName() {
        return appName;
    }

    public void setappName(String appName) {
        this.appName = appName;
    }

    public String getappPackage() {
        return appPackage;
    }

    public void setappPackage(String appPackage) {
        this.appPackage = appPackage;
    }
}