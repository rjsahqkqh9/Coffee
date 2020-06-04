/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class ShopActivity extends AppCompatActivity {
    private ShopData shop;
    private String cart;
    TextView title;

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

    }
}