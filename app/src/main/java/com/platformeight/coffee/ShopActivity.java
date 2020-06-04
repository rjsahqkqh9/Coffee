/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.platformeight.coffee.dummy.DummyContent;

public class ShopActivity extends AppCompatActivity implements MenuFragment.OnListFragmentInteractionListener{
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
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(this, OrderActivity.class);
        //intent.putExtra(Constant.shopdata, item);
        startActivity(intent);

        //Toast.makeText(this, "event "+item.content, Toast.LENGTH_SHORT).show();
    }
}