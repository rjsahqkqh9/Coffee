/*
 *  Create by platform eight on 2020. 6. 9.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.platformeight.coffee.Constant.cart_code;
import static com.platformeight.coffee.Constant.cart_items;
import static com.platformeight.coffee.Constant.format;

public class CartActivity extends AppCompatActivity implements CartFragment.OnListFragmentInteractionListener{

    private String cart_list;

    private FragmentManager fragmentManager;
    private CartFragment CartFragment;
    private FragmentTransaction transaction;

    private Button btn_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initialView();
        initialData();
    }

    private void initialView() {
        btn_pay = findViewById(R.id.cart_pay);
        btn_pay.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                cart_list = CartFragment.getCart();
            }
        });
    }

    private void initialData() {
        cart_list = getIntent().getStringExtra(cart_items);
        try {
            int price=0;
            JSONArray ja = new JSONArray(cart_list);
            for(int i=0;i<ja.length();i++){
                JSONObject js = (JSONObject) ja.get(i);
                price += js.getInt("amount")*js.getInt("price");
            }
            btn_pay.setText(String.format("%s원 결제하기", format.format(price)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(1);
        bundle.putString(cart_items, cart_list);
        //bundle.putString(cart_items, DummyCart());
        CartFragment = new CartFragment();
        CartFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.cart_list, CartFragment).commitAllowingStateLoss();
    }
    @Override
    public void onBackPressed() {
        cart_list = CartFragment.getCart();
        Intent result = new Intent();
        result.putExtra(cart_items,cart_list);
        setResult(RESULT_OK,result);
        super.onBackPressed();
    }

    @Override
    public void onListFragmentInteraction(int price) {
        btn_pay.setText(String.format("%s원 결제하기", format.format(price)));
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js_carts.toString();
    }
}