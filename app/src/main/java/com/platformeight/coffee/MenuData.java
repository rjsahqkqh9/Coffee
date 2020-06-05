/*
 *  Create by platform eight on 2020. 6. 5.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

public class MenuData {
    private int no;

    private String name;//메뉴명

    private String basic;//hot ice etc 기본가격

    private String menu;//기본 추가선택 수량 json

    public MenuData(String no, String name) {
        this.no = Integer.parseInt(no);
        this.name = name;
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


}
