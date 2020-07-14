/*
 *  Create by platform eight on 2020. 6. 9.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.platformeight.coffee.iamportsdk.InicisWebViewClient;
import com.platformeight.coffee.servertask.ServerHandle;
import com.platformeight.coffee.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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

    @SuppressLint("NewApi") @Override
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
                    /* pg 결제데이터

                    Intent intent = new Intent(context, PgpaymentActivity.class);
                    JSONObject request_pay = new JSONObject();
                    request_pay.put("pg", "html6_inicis");                              //결제pg사
                    request_pay.put("pay_method", "phone");                             //결제선택
                    request_pay.put("merchant_uid", "merchant_" + new Date().getTime());    //서버상 자동생성
                    request_pay.put("name", "주문명:결제테스트");   //주문건 menu[0].name
                    request_pay.put("amount", 14000);               //결제금액
                    request_pay.put("buyer_email", "iamport@siot.do");  //공란
                    request_pay.put("buyer_name", "구매자이름");     //user.name
                    request_pay.put("buyer_tel", "010-1234-5678");  //user.phone
                    request_pay.put("buyer_addr", "서울특별시 강남구 삼성동"); //공란
                    request_pay.put("buyer_postcode", "123-456");   //공란
                    intent.putExtra("menu",js_menu.toString());
                    startActivity(intent);


                    Log.d("", "sendOrder: "+js_menu.toString());
                    if ( new ServerHandle().sendOrder(js_menu) ){
                        Main.refreshPoint();
                        new ServerHandle().sendFCM(shop.getNo(),"coffee_shops","주문왔습니다.");
                        finishAffinity(); //주문처리과정 일괄정리
                    } else {
                        Toast.makeText(CartActivity.this, "connection error", Toast.LENGTH_SHORT).show();
                    }
                     */
                    //test

                    Intent intent = new Intent(context, PgpaymentActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

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