/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.platformeight.coffee.servertask.ServerHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.platformeight.coffee.Constant.myorders;
import static com.platformeight.coffee.MyApplication.user;

public class MyOrdersActivity extends AppCompatActivity implements OrderFragment.OnListFragmentInteractionListener, OrderDetailFragment.OnListFragmentInteractionListener, View.OnClickListener {

    private TextView title;
    private Button btn_list1;
    private Button btn_list2;
    private int btn_state;
    private int flag_detail;
    private FragmentManager fragmentManager;
    private OrderFragment orderFragment1;
    private OrderFragment orderFragment2;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);

        initialView();
        initialData();
    }

    private void initialView() {
        title = findViewById(R.id.myorder_title);
        btn_state = 0;
        flag_detail = 0;
        btn_list1 = findViewById(R.id.myorder_btn_list1);
        btn_list2 = findViewById(R.id.myorder_btn_list2);
    }

    private void initialData() {
        //title.setText("");
        btn_list1.setOnClickListener(this);
        btn_list2.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        btn_list1.performClick();
    }

    @Override
    public void onClick(View v) {
        btn_state = v.getId();
        switch (v.getId()){
            case R.id.myorder_btn_list1:
                //TODO:주문내역 받아오기
                //TODO: 내 주문내역 coffe_orders member_no
                orderFragment1 = new OrderFragment();
                //bundle.putString(menu, shop.getMenu());
                setFragment(orderFragment1, new ServerHandle().getOrderList(user.getNo(),1));
                btn_list1.setTextColor(Color.BLACK);
                btn_list2.setTextColor(Color.GRAY);
                flag_detail=0;
                break;
            case R.id.myorder_btn_list2:
                orderFragment2 = new OrderFragment();
                setFragment(orderFragment2, new ServerHandle().getOrderList(user.getNo(),0));
                btn_list1.setTextColor(Color.GRAY);
                btn_list2.setTextColor(Color.BLACK);
                flag_detail=0;
                break;
            default:
        }
    }
    private void setFragment(OrderFragment orderFragment, String myorder){
        Bundle bundle = new Bundle(1);
        bundle.putString(myorders, myorder);
        orderFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.myorder_list, orderFragment).commitAllowingStateLoss();
    }
    private String orderSample(){
        JSONArray js_menus = new JSONArray();
        try {
            JSONObject js_menu = new JSONObject();
            JSONArray ja = new JSONArray();
            JSONObject js1 = new JSONObject();
            js_menu.put("name","카페1");
            js_menu.put("address", "대구");
            js_menu.put("total_price",20800);// price-point
            js_menu.put("order_price",20800);
            js_menu.put("order_point",0);
            js_menu.put("order_amount",2);
            js_menu.put("order_time","2020/05/16/15:38");
            //js1.put("order_price",7500);
            //js1.put("order_count",2);
            //js1.put("order_time","2020/05/16/15:38");
            //js1.put("basic",4300);
            //ja.put(js1);
            //js_menu.put("info","*상세정보내용*");
            js_menu.put("detail",DummyCart());
            js_menus.put(js_menu);

            js_menu = new JSONObject();
            ja = new JSONArray();
            js1 = new JSONObject();
            js_menu.put("name","카페2");
            js_menu.put("address", "서구");
            js_menu.put("total_price",19800);
            js_menu.put("order_price",20800);
            js_menu.put("order_point",1000);
            js_menu.put("order_amount",2);
            js_menu.put("order_time","2020/05/13/15:38");
            //ja.put(js1);
            js_menu.put("detail",DummyCart());
            js_menus.put(js_menu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js_menus.toString();
    }
    private JSONArray DummyCart(){
        JSONArray js_carts = new JSONArray();
        try {
            JSONObject js_cart = new JSONObject();
            JSONArray ja = new JSONArray();
            JSONObject js1 = new JSONObject();
            js_cart.put("no",1);
            js_cart.put("name","아메리카노");
            js_cart.put("onum",2);
            js_cart.put("amount",2);
            js_cart.put("price",5100);
            js_cart.put("base","HOT");
            js_cart.put("HOT",4300);
            js1.put("샷추가",500);
            js1.put("휘핑크림",300);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);

            js_cart = new JSONObject();
            ja = new JSONArray();
            js1 = new JSONObject();
            js_cart.put("no",2);
            js_cart.put("name","카페라떼");
            js_cart.put("onum",2);
            js_cart.put("amount",2);
            js_cart.put("price",5300);
            js_cart.put("base","HOT");
            js_cart.put("HOT",4500);
            js1.put("샷추가",500);
            js1.put("휘핑크림",300);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js_carts;
    }
    @Override
    public void onListFragmentInteraction(JSONObject item) {
        //상세정보출력
        //Intent intent = new Intent(this, OrderDetailActivity.class);
        //intent.putExtra(menu, item.toString());
        //intent.putExtra(cart_items, cart_list);
        //startActivity(intent);
        OrderDetailFragment order = new OrderDetailFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(myorders, item.toString());
        transaction = fragmentManager.beginTransaction();
        order.setArguments(bundle);
        transaction.replace(R.id.myorder_list, order).commitAllowingStateLoss();
        flag_detail = 1;
    }

    @Override
    public void onBackPressed() {
        if (flag_detail == 1) {
            this.onClick(findViewById(btn_state));
            flag_detail = 0;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onListFragmentInteraction() {
        onBackPressed();
    }
}