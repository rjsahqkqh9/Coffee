/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import java.text.DecimalFormat;

public final class Constant {
    //서버주소 네이버클라우드 공인 ip searchcompany 폴더
    public static final String packageNmae = "com.platform8.coffee";
    //TODO: 앱배포전 도메인 주소들 변경
    public static final String server_name = "http://210.89.189.186/coffee/";
    //public static final String local_name = "http://192.168.10.107/coffee/";
    public static final String folder = "user/";

    //ServerHandle
    public static final String member_id_check = server_name +folder+ "member_id_check.php";
    public static final String member_pass_check = server_name +folder+ "member_pass_check.php";
    public static final String member_update = server_name +folder+ "member_update.php";
    public static final String member_login = server_name +folder+ "member_select.php";
    public static final String member_refresh = server_name +folder+ "member_refresh.php";
    public static final String member_register = server_name +folder+ "member_insert.php";
    public static final String shoplist_select = server_name +folder+ "shoplist_gps.php";
    public static final String order_insert = server_name +folder+ "order_insert.php";
    public static final String order_cancel = server_name +folder+ "order_cancel.php";
    public static final String orderlist_user = server_name +folder+ "orderlist_user.php";

    public static final String token_update = server_name + "token_update.php";
    public static final String fcm_basic = server_name + "fcm.php";

    //json name
    public static final String REGISTER_SUCCESS = "members_register success";
    public static final String REGISTER_FAILURE = "members_register failure";
    public static final String ORDER_INSERT_SUCCESS = "order_insert success";
    public static final String ORDER_INSERT_FAILURE = "order_insert failure";
    public static final String SHOPLIST_SELECT = "shoplist_select";
    public static final String ORDERLIST_USER = "orderlist_user";
    public static final String MEMBER_SELECT = "member_select";

    //avtivity result code
    public static final int RESULT_LOGIN = 1000;
    public static final int RESULT_ORDER = 1001;
    public static final int RESULT_CART = 1002;

    //permission
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int CALL_PERMISSION_REQUEST_CODE = 2;
    //intent data code
    public static final String CART_CODE = "cart_code";
    public static final String SHOP_DATA = "ShopData";
    public static final String SHOP_NAME = "shopname";
    public static final String MENU = "menu";
    public static final String MYORDERS = "myorders";
    public static final String CART_ITEMS = "cart";
    public static final String CART_ITEM = "cart_item";
    public static final String LOGIN_STATE = "state";

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###");
}
