/*
 * Create by platform eight on 2019. 10. 30.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    // 값 저장
    public static void setAttribute(Context context, String key, String value){
        SharedPreferences prefs = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // 값 읽기
    static String getAttribute(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences("MyPref", 0);
        return prefs.getString(key, null);
    }

    static boolean hasAttribute(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences("MyPref", 0);
        return prefs.contains(key);
    }

    // 데이터 삭제
    static void removeAttribute(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    // 모든 데이터 삭제
    static void removeAllAttribute(Context context){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

}