/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

// 주문 정보
public class OrderData {

    // 주문 번호
    private int no;

    private int member_no;

    private int shop_no;

    private String detail; //json 주문내역

    private String order_time;

    private int state;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public int getShop_no() {
        return shop_no;
    }

    public void setShop_no(int shop_no) {
        this.shop_no = shop_no;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    // Shop 모델 복사
    public void CopyData(OrderData param)
    {
        this.no = param.getNo();
        this.member_no = param.getMember_no();
        this.shop_no = param.getShop_no();
        this.detail = param.getDetail();
        this.order_time = param.getOrder_time();
        this.state = param.getState();
    }
}