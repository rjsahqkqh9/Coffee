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

import static com.platformeight.coffee.Constant.CART_ITEMS;
import static com.platformeight.coffee.Constant.CART_CODE;
import static com.platformeight.coffee.Constant.MENU;
import static com.platformeight.coffee.Constant.RESULT_CART;
import static com.platformeight.coffee.Constant.RESULT_ORDER;
import static com.platformeight.coffee.Constant.SHOP_DATA;

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
                intent.putExtra(SHOP_DATA, shop);
                intent.putExtra(CART_ITEMS, cart_list);
                startActivityForResult(intent, RESULT_CART);
            }
        });
    }

    private void initialData() {
        shop = (ShopData) getIntent().getSerializableExtra(Constant.SHOP_DATA);
        cart_list = new JSONArray().toString();
        title.setText(shop.getName());

        //TODO: shop으로 메뉴 및 정보 호출
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(1);
        bundle.putString(MENU, shop.getMenu());
        //bundle.putString(menu, shopMenuSample());
        menuFragment = new MenuFragment();
        menuFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_list, menuFragment).commitAllowingStateLoss();

    }

    @Override
    public void onListFragmentInteraction(JSONObject item) {

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(MENU, item.toString());
        intent.putExtra(CART_ITEMS, cart_list);
        startActivityForResult(intent, RESULT_ORDER);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_ORDER && resultCode == RESULT_OK) { //장바구니 담아두기
            if (data.hasExtra(CART_ITEMS) && data.hasExtra(CART_CODE)) {
                this.cart_list = data.getStringExtra(CART_ITEMS);
                Log.d("ShopActivity", "onActivityResult: " + this.cart_list);
                if (data.getBooleanExtra(CART_CODE, false)) {//장바구니 실행
                    Intent intent = new Intent(this, CartActivity.class);
                    intent.putExtra(SHOP_DATA,shop);
                    intent.putExtra(CART_ITEMS, cart_list);
                    startActivityForResult(intent, RESULT_CART);
                } else { //fab_cart animation
                    toggleFab();
                }
            }
        } else if (requestCode == RESULT_CART && resultCode == RESULT_OK) {
            if (data.hasExtra(CART_ITEMS)) {
                this.cart_list = data.getStringExtra(CART_ITEMS);
            }
        }
    }
    private void toggleFab() {
        fab_main.startAnimation(fab_store);
    }
}