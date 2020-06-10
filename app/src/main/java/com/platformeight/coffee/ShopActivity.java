/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.platformeight.coffee.Constant.cart;
import static com.platformeight.coffee.Constant.cart_code;
import static com.platformeight.coffee.Constant.menu;
import static com.platformeight.coffee.Constant.result_cart;

public class ShopActivity extends AppCompatActivity implements MenuFragment.OnListFragmentInteractionListener{
    private ShopData shop;
    private String cart_list;
    private TextView title;
    private FragmentManager fragmentManager;
    private MenuFragment menuFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initialView();
        initialData();
    }

    private void initialView() {
        title = findViewById(R.id.text);

    }

    private void initialData() {
        shop = (ShopData) getIntent().getSerializableExtra(Constant.shopdata);
        title.setText(shop.getName());

        //TODO: shop_no로 메뉴 및 정보 호출
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(1);
        //bundle.putString(Constant.menu, shop.getMenu());

        JSONArray js_menus = new JSONArray();
        try {
            JSONObject js_menu = new JSONObject();
            JSONArray ja = new JSONArray();
            JSONObject js1 = new JSONObject();
            js_menu.put("name","아메리카노");
            js_menu.put("image", "이미지주소값");
            js_menu.put("bnum",2);
            js_menu.put("onum",2);
            //js_menu.put("hot",4300);
            //js_menu.put("ice",5000);
            //js_menu.put("basic",4300);
            js1.put("HOT",4300);
            js1.put("ICE",5000);
            //js1.put("basic",4300);
            ja.put(js1);
            js_menu.put("base",ja);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            js1.put("휘핑크림",300);
            ja.put(js1);
            js_menu.put("opt",ja);
            js_menus.put(js_menu);

            js_menu = new JSONObject();
            ja = new JSONArray();
            js1 = new JSONObject();
            js_menu.put("name","카페라떼");
            js_menu.put("image", "이미지주소값");
            js_menu.put("bnum",2);
            js_menu.put("onum",1);
            //js_menu.put("hot",4300);
            //js_menu.put("ice",5000);
            //js_menu.put("basic",4300);
            js1.put("HOT",4500);
            js1.put("ICE",5300);
            //js1.put("basic",4300);
            ja.put(js1);
            js_menu.put("base",ja);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_menu.put("opt",ja);
            js_menus.put(js_menu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bundle.putString(menu, js_menus.toString());
        menuFragment = new MenuFragment();
        menuFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_list, menuFragment).commitAllowingStateLoss();

    }
    @Override
    public void onListFragmentInteraction(JSONObject item) {

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(menu, item.toString());
        intent.putExtra(cart, cart_list);
        //startActivity(intent);
        startActivityForResult(intent,result_cart);
        //Toast.makeText(this, "event "+item.content, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == result_cart && resultCode == RESULT_OK) { //장바구니 담아두기
            if (data.hasExtra(cart) && data.hasExtra(cart_code)) {
                this.cart_list = data.getStringExtra(cart);
                if (data.getBooleanExtra(cart_code,false)){//장바구니 실행
                    Intent intent = new Intent(this, CartActivity.class);
                    intent.putExtra(Constant.cart, cart_list);
                    startActivity(intent);
                }
            }
        }
    }
}