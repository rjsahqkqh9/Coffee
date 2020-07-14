/*
 *  Create by platform eight on 2020. 7. 13.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.platformeight.coffee.iamportsdk.InicisWebViewClient;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PgpaymentActivity extends AppCompatActivity {

    private WebView mainWebView;
    private static final String APP_SCHEME = "iamporttest://";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgpayment);

        mainWebView = (WebView) findViewById(R.id.mainWebView);
        mainWebView.setWebViewClient(new InicisWebViewClient(this));
        WebSettings settings = mainWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(mainWebView, true);
        }

        Intent intent = getIntent();
        Uri intentData = intent.getData();

        if ( intentData == null ) {
            mainWebView.loadUrl("http://www.iamport.kr/demo");
            //mainWebView.loadUrl("http://210.89.189.186/payments/pgtest.html?data=");
            try {
                //String post = "menu =" + URLEncoder.encode(getIntent().getStringExtra("menu"), "UTF-8");
                String postData = "pg=" + URLEncoder.encode("html5_inicis", "UTF-8")+"&pay_method=" + URLEncoder.encode("card", "UTF-8")+"&name=" + URLEncoder.encode("결제", "UTF-8");
                //String data = "name=" + URLEncoder.encode("결제", "UTF-8");
                String request_pay = "pg=html5_inicis&pay_method=phone&name=결제";
                //mainWebView.postUrl("http://210.89.189.186/payments/pgtest.html",postData.getBytes());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if ( url.startsWith(APP_SCHEME) ) {
                String redirectURL = url.substring(APP_SCHEME.length()+3);
                mainWebView.loadUrl(redirectURL);
            }
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        String url = intent.toString();
        if (url.startsWith(APP_SCHEME)) {
            String redirectURL = url.substring(APP_SCHEME.length() + 3);
            mainWebView.loadUrl(redirectURL);
        }
        super.onNewIntent(intent);
    }
}