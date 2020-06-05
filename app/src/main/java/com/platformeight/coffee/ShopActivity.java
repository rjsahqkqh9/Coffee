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
        bundle.putString(Constant.menu, shop.getMenu());
        //bundle.putString(Constant.menu, "menu");
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