/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import java.io.Serializable;

// 공급자 정보
public class ShopData implements Serializable {

    // 공급 번호
    private int no;

    private String name;

    private String address;

    private String phone;

    private String menu;

    private int state;

    public ShopData(String no, String content, String details) {
        this.no = Integer.parseInt(no);
        this.name = content;
        this.address = details;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    // Shop 모델 복사
    public void CopyData(ShopData param)
    {
        this.no = param.getNo();
        this.name = param.getName();
        this.address = param.getAddress();
        this.phone = param.getPhone();
        this.menu = param.getMenu();
        this.state = param.getState();
    }
}