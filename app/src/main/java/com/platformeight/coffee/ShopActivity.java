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

import com.platformeight.coffee.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopActivity extends AppCompatActivity implements MenuFragment.OnListFragmentInteractionListener{
    private ShopData shop;
    private String cart;
    private TextView title;
    private FragmentManager fragmentManager;
    private MenuFragment menu;
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
        JSONObject js_menu = new JSONObject();
        try {
            js_menu.put("name","아메리카노");
            js_menu.put("hot",4300);
            js_menu.put("ice",5000);
            //js_menu.put("basic",4300);
            JSONArray ja = new JSONArray();
            JSONObject js1 = new JSONObject();
            js1.put("샷추가",500);
            js1.put("휘핑크림",300);
            ja.put(js1);
            js_menu.put("opt",ja);
            js_menus.put(js_menu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bundle.putString(Constant.menu, js_menus.toString());
        menu = new MenuFragment();
        menu.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_list, menu).commitAllowingStateLoss();

    }
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("menu_no", "1");
        startActivity(intent);
        //Toast.makeText(this, "event "+item.content, Toast.LENGTH_SHORT).show();
    }
}