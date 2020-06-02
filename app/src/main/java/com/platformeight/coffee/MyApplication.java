/*
 * Create by platform eight on 2019. 10. 22.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    public static MemberData user;
    public static int app_state = 1;  // 1 고객 2 기업  //화면리스트 분기할것 역경매방식

    @Override
    public void onCreate() {
        super.onCreate();
        initializeData();
    }

    private void initializeData() {
        //설정 읽어오기 user 자동로그인
        user = new MemberData();
        //autologin();
    }


    private static final String AUTOLOGIN = "autologin";
    /*
    private void autologin() {
        //휴대폰내 로그인정보 읽어오기
        //SharedPreference.removeAllAttribute(this);
        if (SharedPreference.hasAttribute(this, "ID")){
            String id = SharedPreference.getAttribute(this, "ID");
            Log.d(AUTOLOGIN, "member_id : "+id);
            String pass = SharedPreference.getAttribute(this, "PASS");
            if (id.length()>0&&pass.length()>0){
                MemberHandle member = new MemberHandle(this);
                boolean result = member.login(id,pass);
                if (!result) Log.e(AUTOLOGIN, " failure" );
            }
        } else Log.d(AUTOLOGIN, "login data empty");
    }

     */

}
