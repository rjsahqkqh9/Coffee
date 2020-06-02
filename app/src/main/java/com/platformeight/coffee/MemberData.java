/*
 * Create by platform eight on 2019. 11. 1.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

public class MemberData {

    // 회원번호 members_login
    private int no;

    private String name;

    private String email;

    private String pass;

    private String phone;

    private int state;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    //  모델 복사
    public void CopyData(MemberData param)
    {
        this.no = param.getNo();
        this.name = param.getName();
        this.email = param.getEmail();
        this.pass = param.getPass();
        this.state = param.getState();
    }
}