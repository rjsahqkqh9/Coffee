/*
 *  Create by platform eight on 2020. 6. 9.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.platformeight.coffee.servertask.ServerHandle;
import com.platformeight.coffee.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.platformeight.coffee.Constant.CART_ITEMS;
import static com.platformeight.coffee.Constant.DECIMAL_FORMAT;
import static com.platformeight.coffee.MyApplication.Main;
import static com.platformeight.coffee.MyApplication.mLoginForm;
import static com.platformeight.coffee.MyApplication.user;

public class CartActivity extends AppCompatActivity implements CartFragment.OnListFragmentInteractionListener{

    private String cart_list;
    private ShopData shop;

    private FragmentManager fragmentManager;
    private CartFragment CartFragment;
    private FragmentTransaction transaction;

    private TextView name;
    private Button btn_pay;
    private int price;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initialView();
        initialData();
    }

    private void initialView() {
        name = findViewById(R.id.cart_name);
        btn_pay = findViewById(R.id.cart_pay);
        btn_pay.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (user.getNo()<1){
                    Toast.makeText(context, "로그인 혹은 회원가입을 해주세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Main, LoginActivity.class);
                    startActivity(intent);
                    return;
                } else {
                    mLoginForm=false;
                    Main.changeLogin();
                }
                cart_list = CartFragment.getCart();
                try {
                    JSONObject js_menu = new JSONObject();
                    JSONArray ja = new JSONArray(cart_list);
                    js_menu.put("shop_no", shop.getNo());
                    js_menu.put("member_no", user.getNo());
                    js_menu.put("order_amount", ja.length());
                    js_menu.put("order_price", price);
                    //TODO:마일리지포인트 사용 처리
                    js_menu.put("order_point", 0);
                    js_menu.put("total_price", price);// price-point
                    //js_menu.put("order_time", "2020/05/16/15:38"); //db상 처리
                    js_menu.put("detail", ja.toString());
                    // TODO:결제페이지로 토스
                    //test
                    Log.d("", "sendOrder: "+js_menu.toString());
                    if ( new ServerHandle().sendOrder(js_menu) ){
                        new ServerHandle().sendFCM(shop.getNo(),"coffee_shops","주문왔습니다.");
                        finishAffinity(); //주문처리과정 일괄정리
                    } else {
                        Toast.makeText(CartActivity.this, "connection error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initialData() {
        this.context = this;
        price=0;
        cart_list = getIntent().getStringExtra(CART_ITEMS);
        shop = (ShopData) getIntent().getSerializableExtra(Constant.SHOP_DATA);
        name.setText(shop.getName());
        try {
            JSONArray ja = new JSONArray(cart_list);
            for(int i=0;i<ja.length();i++){
                JSONObject js = (JSONObject) ja.get(i);
                price += js.getInt("amount")*js.getInt("price");
            }
            btn_pay.setText(String.format("%s원 결제하기", DECIMAL_FORMAT.format(price)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(1);
        bundle.putString(CART_ITEMS, cart_list);
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
        result.putExtra(CART_ITEMS,cart_list);
        setResult(RESULT_OK,result);
        super.onBackPressed();
    }

    @Override
    public void onListFragmentInteraction(int price) {
        this.price = price;
        btn_pay.setText(String.format("%s원 결제하기", DECIMAL_FORMAT.format(price)));
    }
}