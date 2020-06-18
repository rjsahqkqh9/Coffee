/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalTime;

// 공급자 정보
public class ShopData implements Serializable {

    // 점포번호
    private int no;

    // 점포사진
    private String image;

    // 점포명
    private String name;

    // 주소
    private String address;

    // 전화번호 : 고객측 이용x
    private String phone;

    // 메뉴
    private String menu;

    // 영업상태
    private int state;

    // 좌표x
    private Double mapx;

    // 좌표y
    private Double mapy;

    private Double distance;

    // 영업시작시간
    private LocalTime shopOpen;

    // 영업종료시간
    private LocalTime shopClose;

    // 등급
    private int promotion;

    private String rest_date;

    public ShopData(String no, String image, String name, int state, String shopOpen, String shopClose) { //itemfragment 리스트용
        //리스트에 출력
        this.no = Integer.parseInt(no);
        this.image = image;
        this.name = name;

        //영업중인지 확인
        this.state = state;
        this.shopOpen = LocalTime.parse(shopOpen);
        this.shopClose = LocalTime.parse(shopClose);
    }
    public ShopData(String no, String image, String name, String menu, int state, String shopOpen, String shopClose) { //itemfragment 리스트용
        //리스트에 출력
        this.no = Integer.parseInt(no);
        this.image = image;
        this.name = name;

        //영업중인지 확인
        this.state = state;
        this.shopOpen = LocalTime.parse(shopOpen);
        this.shopClose = LocalTime.parse(shopClose);
    }
    public ShopData(String no, String image, String name, String menu, int state, String shopOpen, String shopClose, double mapx, double mapy) { //itemfragment 리스트용
        //리스트에 출력
        this.no = Integer.parseInt(no);
        this.image = image;
        this.name = name;
        this.menu = menu;

        //영업중인지 확인
        this.state = state;
        this.shopOpen = LocalTime.parse(shopOpen);
        this.shopClose = LocalTime.parse(shopClose);
        this.mapx = mapx;
        this.mapy = mapy;
    }
    public ShopData(String no, String image, String name, String menu, int state, String shopOpen, String shopClose, double mapx, double mapy, double distance) { //itemfragment 리스트용
        //리스트에 출력
        this.no = Integer.parseInt(no);
        this.image = image;
        this.name = name;
        this.menu = menu;

        //영업중인지 확인
        this.state = state;
        this.shopOpen = LocalTime.parse(shopOpen);
        this.shopClose = LocalTime.parse(shopClose);
        this.mapx = mapx;
        this.mapy = mapy;
        this.distance = distance;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getMapx() {
        return mapx;
    }

    public void setMapx(Double mapx) {
        this.mapx = mapx;
    }

    public Double getMapy() {
        return mapy;
    }

    public void setMapy(Double mapy) {
        this.mapy = mapy;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalTime getShopOpen() {
        return shopOpen;
    }

    public void setShopOpen(LocalTime shopOpen) {
        this.shopOpen = shopOpen;
    }

    public LocalTime getShopClose() {
        return shopClose;
    }

    public void setShopClose(LocalTime shopClose) {
        this.shopClose = shopClose;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("no",no);
            obj.put("iamge",image);
            obj.put("name",name);
            obj.put("menu",menu);
            obj.put("state",state);
            obj.put("shopOpen",shopOpen);
            obj.put("shopClose",shopClose);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    // Shop 모델 복사
    public void CopyData(ShopData param)
    {
        this.no = param.getNo();
        this.name = param.getName();
        this.address = param.getAddress();
        this.phone = param.getPhone();
        this.menu = param.getMenu();
        this.mapx = param.getMapx();
        this.mapy = param.getMapy();
        this.shopOpen = param.getShopOpen();
        this.shopClose = param.getShopClose();
        //this.promotion = param.getPromotion();
        this.image = param.getImage();
        this.state = param.getState();
    }
}