/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import java.time.LocalDateTime;

// 주문 정보
public class OrderData {
    // 주문 번호
    private int no;

    //private int member_no;

    private int shop_no;

    private String shop_name;

    private String shop_address;

    private int total_price;

    private int order_price;

    private int order_point;

    private int order_amount;

    private String detail; //json 주문내역

    private LocalDateTime order_time;

    private int state;

    public OrderData(int no, int shop_no, String shop_name, String shop_address, int total_price, int order_price, int order_point, int order_amount, String detail, String order_time, int state) {
        this.no = no;
        this.shop_no = shop_no;
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.total_price = total_price;
        this.order_price = order_price;
        this.order_point = order_point;
        this.order_amount = order_amount;
        this.detail = detail;
        this.order_time = LocalDateTime.parse(order_time);
        this.state = state;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getShop_no() {
        return shop_no;
    }

    public void setShop_no(int shop_no) {
        this.shop_no = shop_no;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getOrder_price() {
        return order_price;
    }

    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }

    public int getOrder_point() {
        return order_point;
    }

    public void setOrder_point(int order_point) {
        this.order_point = order_point;
    }

    public int getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(int order_amount) {
        this.order_amount = order_amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDateTime getOrder_time() {
        return order_time;
    }

    public void setOrder_time(LocalDateTime order_time) {
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
        //this.member_no = param.getMember_no();
        this.shop_no = param.getShop_no();
        this.detail = param.getDetail();
        this.order_time = param.getOrder_time();
        this.state = param.getState();
    }
}