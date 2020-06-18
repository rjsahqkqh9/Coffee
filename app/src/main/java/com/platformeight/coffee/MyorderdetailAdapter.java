/*
 *  Create by platform eight on 2020. 6. 16.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyorderdetailAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    JSONArray sample;

    public MyorderdetailAdapter(Context context, JSONArray data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public JSONObject getItem(int position) {
        try {
            return sample.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.fragment_detail, null);

        TextView name = (TextView)view.findViewById(R.id.order_detail_menu_name);
        TextView quan = (TextView)view.findViewById(R.id.order_detail_menu_quantity);
        TextView price = (TextView)view.findViewById(R.id.order_detail_menu_price);
        try {
            JSONObject js = sample.getJSONObject(position);
            name.setText(String.format("%s %s", js.getString("base"), js.getString("name")));
            quan.setText(js.getString("amount"));
            price.setText(Constant.format.format(js.getInt("price")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
