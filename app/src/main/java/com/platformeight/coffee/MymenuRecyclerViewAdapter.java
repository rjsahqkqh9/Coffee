/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.platformeight.coffee.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymenuRecyclerViewAdapter extends RecyclerView.Adapter<MymenuRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final MenuFragment.OnListFragmentInteractionListener mListner;

    public MymenuRecyclerViewAdapter(List<DummyItem> items, MenuFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListner = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        holder.mMenuView.setText("menu "+position);
        holder.mMenuHotView.setText(mValues.get(position).content);
        holder.mMenuIceView.setText(mValues.get(position).content);
        holder.mView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mListner.onListFragmentInteraction(holder.mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        //public final TextView mContentView;
        public final TextView mMenuView;
        public final TextView mMenuHotView;
        public final TextView mMenuIceView;

        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.item_number);
            //mContentView = (TextView) view.findViewById(R.id.content);
            mMenuView = (TextView) view.findViewById(R.id.menu);
            mMenuHotView = (TextView) view.findViewById(R.id.menu_hot);
            mMenuIceView = (TextView) view.findViewById(R.id.menu_ice);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMenuView.getText() + "'";
        }
    }
}