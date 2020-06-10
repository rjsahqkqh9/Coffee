/*
 *  Create by platform eight on 2020. 6. 9.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.platformeight.coffee.Constant.cart;

public class CartActivity extends AppCompatActivity implements CartFragment.OnListFragmentInteractionListener{
    private String cart_list;
    private FragmentManager fragmentManager;
    private CartFragment CartFragment;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initialView();
        initialData();
    }

    private void initialView() {
        Button btn_pay = findViewById(R.id.cart_pay);
        btn_pay.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                CartFragment.getCart();
            }
        });
    }

    private void initialData() {
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(1);
        bundle.putString(cart, DummyCart());
        CartFragment = new CartFragment();
        CartFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.cart_list, CartFragment).commitAllowingStateLoss();

    }

    private String DummyCart(){
        JSONArray js_carts = new JSONArray();
        try {
            JSONObject js_cart = new JSONObject();
            js_cart.put("no",1);
            js_cart.put("name","아메리카노");
            js_cart.put("onum",2);
            js_cart.put("amount",2);
            js_cart.put("price",5100);
            js_cart.put("base","HOT");
            js_cart.put("HOT",4300);
            JSONArray ja = new JSONArray();
            JSONObject js1 = new JSONObject();
            js1.put("샷추가",500);
            js1.put("휘핑크림",300);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);

            js_cart = new JSONObject();
            js_cart.put("no",2);
            js_cart.put("name","카페라떼");
            js_cart.put("onum",1);
            js_cart.put("amount",1);
            js_cart.put("price",5800);
            js_cart.put("base","ICE");
            js_cart.put("ICE",5300);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);

            js_cart = new JSONObject();
            js_cart.put("no",3);
            js_cart.put("name","카페라떼");
            js_cart.put("onum",1);
            js_cart.put("amount",1);
            js_cart.put("price",5800);
            js_cart.put("base","ICE");
            js_cart.put("ICE",5300);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);

            js_cart = new JSONObject();
            js_cart.put("no",4);
            js_cart.put("name","카페라떼");
            js_cart.put("onum",1);
            js_cart.put("amount",1);
            js_cart.put("price",5800);
            js_cart.put("base","ICE");
            js_cart.put("ICE",5300);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);

            js_cart = new JSONObject();
            js_cart.put("no",5);
            js_cart.put("name","카페라떼");
            js_cart.put("onum",1);
            js_cart.put("amount",1);
            js_cart.put("price",5800);
            js_cart.put("base","ICE");
            js_cart.put("ICE",5300);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);

            js_cart = new JSONObject();
            js_cart.put("no",6);
            js_cart.put("name","카페라떼");
            js_cart.put("onum",1);
            js_cart.put("amount",1);
            js_cart.put("price",5800);
            js_cart.put("base","ICE");
            js_cart.put("ICE",5300);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);

            js_cart = new JSONObject();
            js_cart.put("no",7);
            js_cart.put("name","카페라떼");
            js_cart.put("onum",1);
            js_cart.put("amount",1);
            js_cart.put("price",5800);
            js_cart.put("base","ICE");
            js_cart.put("ICE",5300);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);

            js_cart = new JSONObject();
            js_cart.put("no",8);
            js_cart.put("name","카페라떼");
            js_cart.put("onum",1);
            js_cart.put("amount",1);
            js_cart.put("price",5800);
            js_cart.put("base","ICE");
            js_cart.put("ICE",5300);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_cart.put("opt",ja);
            js_carts.put(js_cart);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js_carts.toString();
    }
    @Override
    public void onListFragmentInteraction(JSONObject item) {

    }
}