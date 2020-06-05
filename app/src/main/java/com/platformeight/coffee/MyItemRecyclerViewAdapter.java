/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.platformeight.coffee.dummy.DummyContent.DummyItem;
import com.platformeight.coffee.ItemFragment.OnListFragmentInteractionListener;

import java.util.List;
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

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //getstate 및 open close에 따라 회색처리

        holder.mIdView.setText(String.valueOf(mValues.get(position).getNo()));
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mListner.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;

        public final ImageView mImageView;

        public ShopData mItem; //data 클래스 shopData

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mNameView = (TextView) view.findViewById(R.id.item_name);
            mImageView = (ImageView) view.findViewById(R.id.item_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}