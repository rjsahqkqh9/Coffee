/*
 * Create by platform eight on 2019. 11. 1.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

// 공급자 정보
public class ShopData {

    // 공급 번호
    private int no;

    private String name;

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

    // MembersCompany 모델 복사
    public void CopyData(ShopData param)
    {
        this.no = param.getNo();
        this.name = param.getName();
    }
}