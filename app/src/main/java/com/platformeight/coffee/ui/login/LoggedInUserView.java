/*
 *  Create by platform eight on 2020. 6. 10.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private int point;
    private int no;
    private String id;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }
    LoggedInUserView(String id, String displayName, int point) {
        this.id = id;
        this.displayName = displayName;
        this.point = point;
    }

    String getDisplayName() {
        return displayName;
    }

    public int getPoint() {
        return point;
    }

    public String getId() {
        return id;
    }
}