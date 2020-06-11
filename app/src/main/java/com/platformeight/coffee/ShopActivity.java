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
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.platformeight.coffee.Constant.cart_items;
import static com.platformeight.coffee.Constant.cart_code;
import static com.platformeight.coffee.Constant.menu;
import static com.platformeight.coffee.Constant.result_cart;
import static com.platformeight.coffee.Constant.result_order;

public class ShopActivity extends AppCompatActivity implements MenuFragment.OnListFragmentInteractionListener{
    private ShopData shop;
    private String cart_list;
    private TextView title;
    private ImageView shop_image;
    private FragmentManager fragmentManager;
    private MenuFragment menuFragment;
    private FragmentTransaction transaction;

    private FloatingActionButton fab_main;
    private Animation fab_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initialView();
        initialData();
    }

    private void initialView() {
        title = findViewById(R.id.shop_title);
        shop_image = findViewById(R.id.shop_image);
        fab_store = AnimationUtils.loadAnimation(this, R.anim.fab_store);
        fab_main = (FloatingActionButton) findViewById(R.id.shop_fab_cart);
        fab_main.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                //toggleFab();
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.putExtra(cart_items, cart_list);
                startActivityForResult(intent, result_cart);
            }
        });
    }

    private void initialData() {
        shop = (ShopData) getIntent().getSerializableExtra(Constant.shopdata);
        cart_list = new JSONArray().toString();
        title.setText(shop.getName());

        //TODO: shop으로 메뉴 및 정보 호출
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(1);
        bundle.putString(menu, shop.getMenu());
        //bundle.putString(menu, shopMenuSample());
        menuFragment = new MenuFragment();
        menuFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_list, menuFragment).commitAllowingStateLoss();

    }

    @Override
    public void onListFragmentInteraction(JSONObject item) {

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(menu, item.toString());
        intent.putExtra(cart_items, cart_list);
        startActivityForResult(intent, result_order);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == result_order && resultCode == RESULT_OK) { //장바구니 담아두기
            if (data.hasExtra(cart_items) && data.hasExtra(cart_code)) {
                this.cart_list = data.getStringExtra(cart_items);
                Log.d("ShopActivity", "onActivityResult: " + this.cart_list);
                if (data.getBooleanExtra(cart_code, false)) {//장바구니 실행
                    Intent intent = new Intent(this, CartActivity.class);
                    intent.putExtra(cart_items, cart_list);
                    startActivityForResult(intent, result_cart);
                } else { //fab_cart animation
                    toggleFab();
                }
            }
        } else if (requestCode == result_cart && resultCode == RESULT_OK) {
            if (data.hasExtra(cart_items)) {
                this.cart_list = data.getStringExtra(cart_items);
            }
        }
    }
    private void toggleFab() {
        fab_main.startAnimation(fab_store);
    }
}