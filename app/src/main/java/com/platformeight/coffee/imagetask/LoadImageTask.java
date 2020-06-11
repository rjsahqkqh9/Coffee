/*
 * Create by platform eight on 2019. 12. 6.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee.imagetask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;


public class LoadImageTask extends AsyncTask<String, String, Bitmap> {
    private String url;
    private JSONObject val;
    private Bitmap mBitmap;


    public LoadImageTask(String url, JSONObject values) {

        this.url = url;
        this.val = values;
    }

    public LoadImageTask() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... args) {
        try {
            mBitmap = BitmapFactory
                    .decodeStream((InputStream) new URL(args[0])
                            .getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        super.onPostExecute(s);
        //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
        //tv_outPut.setText(s);
    }
}