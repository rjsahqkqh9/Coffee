/*
 *  Create by platform eight on 2020. 6. 19.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.platformeight.coffee.SharedPreference;

public class AlarmReceiver extends BroadcastReceiver {

    private static String TAG = "Alarm Receiver";
    private final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";
    private final String suspend = "android.intent.action.MY_PACKAGE_SUSPENDED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals(BOOT_ACTION))
        {
            Log.d(TAG, "BOOT_ACTION : alarm service ON");
            context.startService(new Intent("com.google.firebase.MESSAGING_EVENT"));
        }else if(intent.getAction().equals(suspend)){
            Log.d(TAG, "suspend : alarm service ON");
            context.startService(new Intent("com.google.firebase.MESSAGING_EVENT"));
        }
    }
}
