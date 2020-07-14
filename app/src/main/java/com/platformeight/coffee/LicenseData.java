/*
 *  Create by platform eight on 2020. 7. 3.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

public class LicenseData {

    private String titleStr ;
    private String descStr ;

    public LicenseData(String titleStr, String descStr) {
        this.titleStr = titleStr;
        this.descStr = descStr;
    }

    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public String getLicenseTitle() {
        return this.titleStr ;
    }
    public String getLicenseContent() {
        return this.descStr ;
    }
}