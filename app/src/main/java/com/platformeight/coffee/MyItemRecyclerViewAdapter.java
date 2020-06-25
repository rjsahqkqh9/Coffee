/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.platformeight.coffee.dummy.DummyContent.DummyItem;
import com.platformeight.coffee.ItemFragment.OnListFragmentInteractionListener;
import com.platformeight.coffee.imagetask.LoadImageTask;
import com.platformeight.coffee.mylocation.distance;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 *
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<ShopData> mValues;
    private final OnListFragmentInteractionListener mListner;

    public MyItemRecyclerViewAdapter(List<ShopData> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListner = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //getstate 및 open close에 따라 회색처리
        LoadImageTask loadRegistrationTask = new LoadImageTask();
        try {
            Bitmap originalBm = loadRegistrationTask.execute(Constant.server_name+ holder.mItem.getImage()).get();
            holder.mImageView.setImageBitmap(originalBm);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //holder.mDistanceView.setText(String.format("%skm", distance.distance(holder.mItem.getMapy(), holder.mItem.getMapx(), 35.798838, 128.583052, "kilometer")));
        holder.mDistanceView.setText(String.format("약 %5.2f km",holder.mItem.getDistance()));
        holder.mIdView.setText(String.valueOf(mValues.get(position).getNo()));
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mListner.onListFragmentInteraction(holder.mItem);
            }
        });
        //LocalTime testTime = LocalTime.of(7,0);
        //if (holder.mItem.getShopOpen().isAfter(LocalTime.now()) || holder.mItem.getShopClose().isBefore(LocalTime.now())||holder.mItem.getState()==0){
        //if (holder.mItem.getShopOpen().isAfter(testTime) || holder.mItem.getShopClose().isBefore(testTime)||holder.mItem.getState()==0){
        if (holder.mItem.getState()==0){
            holder.mIdView.setEnabled(false);
            holder.mNameView.setEnabled(false);
            holder.mDistanceView.setEnabled(false);
            holder.mView.setEnabled(false);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if (mValues==null) return 0;
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;
        public final TextView mDistanceView;

        public final ImageView mImageView;

        public ShopData mItem; //data 클래스 shopData

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mNameView = (TextView) view.findViewById(R.id.item_name);
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            mDistanceView = (TextView) view.findViewById(R.id.item_distance);;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}