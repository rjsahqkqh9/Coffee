/*
 *  Create by platform eight on 2020. 6. 9.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.platformeight.coffee.Constant.CART_ITEMS;
import static com.platformeight.coffee.Constant.DECIMAL_FORMAT;

public class LicenseActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private TextView name;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        initialView();
        initialData();
    }

    private void initialView() {
        name = findViewById(R.id.license_titlebar);
    }

    private void initialData() {
        this.context = this;
        fragmentManager = getSupportFragmentManager();
        LicenseFragment licenseFragment = new LicenseFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.license_list, licenseFragment).commitAllowingStateLoss();
    }
}