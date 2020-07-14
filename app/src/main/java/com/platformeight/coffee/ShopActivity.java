/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.platformeight.coffee.Constant.CALL_PERMISSION_REQUEST_CODE;
import static com.platformeight.coffee.Constant.CART_ITEMS;
import static com.platformeight.coffee.Constant.CART_CODE;
import static com.platformeight.coffee.Constant.MENU;
import static com.platformeight.coffee.Constant.RESULT_CART;
import static com.platformeight.coffee.Constant.RESULT_ORDER;
import static com.platformeight.coffee.Constant.SHOP_DATA;
import static com.platformeight.coffee.Constant.SHOP_NAME;

public class ShopActivity extends AppCompatActivity implements MenuFragment.OnListFragmentInteractionListener{

    private final String TAG = "ShopActivity";

    private ShopData shop;
    private String cart_list;

    private TextView title;
    private TextView cart_count;
    private ImageView shop_image;
    private TextView shop_address;
    private Button btn_call;
    private Button btn_map;

    private FragmentManager fragmentManager;
    private MenuFragment menuFragment;
    private FragmentTransaction transaction;

    private FloatingActionButton fab_main;
    private Animation fab_store;


    private boolean pPermissionDenied = false;
    private boolean pCallPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initialView();
        initialData();
    }
    private void addFragment(String name, String menu){
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(2);
        bundle.putString("name", name);
        bundle.putString(MENU, menu);
        //bundle.putString(menu, shopMenuSample());
        menuFragment = new MenuFragment();
        menuFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.menu_category_group, menuFragment).commit();
    }
    private void initialView() {
        title = findViewById(R.id.shop_title);
        shop_image = findViewById(R.id.shop_image);
        shop_address = findViewById(R.id.shop_address);
        btn_call = findViewById(R.id.shop_btn_call);
        btn_map = findViewById(R.id.shop_btn_map);
        fab_store = AnimationUtils.loadAnimation(this, R.anim.fab_store);
        cart_count = findViewById(R.id.badge_notification_1);
        cart_count.setVisibility(View.GONE);
        fab_main = (FloatingActionButton) findViewById(R.id.shop_fab_cart);
        fab_main.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                //toggleFab();
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.putExtra(SHOP_DATA, shop);
                intent.putExtra(CART_ITEMS, cart_list);
                startActivityForResult(intent, RESULT_CART);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }
    private void initialData() {
        shop = (ShopData) getIntent().getSerializableExtra(Constant.SHOP_DATA);
        cart_list = new JSONArray().toString();
        title.setText(shop.getName());
        shop_address.setText(shop.getAddress());

        btn_call.setOnClickListener(v -> {
            //권한요구
            enablePhoneCall();
        });
        btn_map.setOnClickListener(v -> {
            //현재 위치및 가게 위치 전달 지도 프래그먼트 호출
            Intent intent = new Intent(this, CustomLocationTrackingActivity.class);
            intent.putExtra(SHOP_DATA, shop);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        //TODO: shop으로 메뉴 및 정보 호출
        /*
        // 이전 단일 메뉴 프래그먼트
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(1);
        bundle.putString(MENU, shop.getMenu());
        //bundle.putString(menu, shopMenuSample());
        menuFragment = new MenuFragment();
        menuFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_list, menuFragment).commitAllowingStateLoss();
         */
        String menu_json = shop.getMenu();
        //menu_json = "[{\"no\":1, \"name\":\"음료\", \"data\":[{\"name\":\"아메리카노\",\"image\":\"이미지주소값\",\"bnum\":2,\"onum\":2,\"base\":[{\"HOT\":4300,\"ICE\":5000}],\"opt\":[{\"샷추가\":500,\"휘핑크림\":300}]}]},{\"no\":2, \"name\":\"기타\", \"data\":[{\"name\":\"아메리카노\",\"image\":\"이미지주소값\",\"bnum\":2,\"onum\":2,\"base\":[{\"HOT\":4300,\"ICE\":5000}],\"opt\":[{\"샷추가\":500}]}]}]";
        Log.d(TAG, "menu category : "+ menu_json);
        try {
            JSONArray ja = new JSONArray(menu_json);
            for (int i=0;i<ja.length();i++){
                Log.d(TAG, "menu ja : "+ ja.getJSONObject(i).getString("name"));
                Log.d(TAG, "menu ja : "+ ja.get(i));
                addFragment(ja.getJSONObject(i).getString("name"),ja.getJSONObject(i).getString("data"));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }
    private void enablePhoneCall() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("", "enablePhoneCall: "+shop.getPhone());
            Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + shop.getPhone()));
            startActivity(tt);
            pCallPermissionGranted = true;
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, CALL_PERMISSION_REQUEST_CODE,
                    Manifest.permission.CALL_PHONE, true);
        }
        // [END maps_check_location_permission]
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.CALL_PHONE)) {
                // Enable the my location layer if the permission has been granted.
                enablePhoneCall();
            } else {
                // Permission was denied. Display an error message
                // [START_EXCLUDE]
                // Display the missing permission error dialog when the fragments resume.
                pPermissionDenied = true;
                // [END_EXCLUDE]
            }
        }
    }
    @Override
    public void onListFragmentInteraction(JSONObject item) {

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(SHOP_NAME, shop.getName());
        intent.putExtra(MENU, item.toString());
        intent.putExtra(CART_ITEMS, cart_list);
        startActivityForResult(intent, RESULT_ORDER);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else { //fab_cart animation
                    toggleFab();
                }
            }
        } else if (requestCode == RESULT_CART && resultCode == RESULT_OK) {
            if (data.hasExtra(CART_ITEMS)) {
                this.cart_list = data.getStringExtra(CART_ITEMS);
                toggleFab();
            }
        }
    }
    private void toggleFab() {
        fab_main.startAnimation(fab_store);
        //TODO::cart count 계산
        try {
            JSONArray ja = new JSONArray(cart_list);
            if (ja.length()>0){
                cart_count.setText(""+ja.length());
                cart_count.setVisibility(View.VISIBLE);
            } else {
                cart_count.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}