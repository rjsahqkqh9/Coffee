/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.platformeight.coffee.dummy.DummyContent.DummyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymenuRecyclerViewAdapter extends RecyclerView.Adapter<MymenuRecyclerViewAdapter.ViewHolder> {

    private final List<JSONObject> mValues;
    private final MenuFragment.OnListFragmentInteractionListener mListner;

    public MymenuRecyclerViewAdapter(List<JSONObject> items, MenuFragment.OnListFragmentInteractionListener listener) {
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
        //holder.mMenuView.setText("menu "+position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        try {
            holder.mMenuView.setText(holder.mItem.getString("name"));
            JSONArray ja = new JSONArray(holder.mItem.getString("base"));
            DecimalFormat format = new DecimalFormat("###,###");
            JSONObject js = ja.getJSONObject(0);
            /*
             */
            for(Iterator<String> itr = js.keys(); itr.hasNext();){
                String str = itr.next();
                TextView tv = new TextView(holder.mView.getContext());
                tv.setText(String.format("- %s : ￦%s원", str, format.format(js.getInt(str))));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                holder.mTextGroup.addView(tv);
            }
            //holder.mMenuHotView.setText(String.format("- HOT : ￦%s원", format.format(js.getInt("hot"))));
            //holder.mMenuIceView.setText(String.format("- ICE : ￦%s원", format.format(js.getInt("ice"))));
            //holder.mMenuHotView.setText("HOT : " + js.getString("hot"));
            //holder.mMenuIceView.setText("ICE : " + js.getString("ice"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        public final TextView mMenuView;
        public final LinearLayout mTextGroup;

        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMenuView = (TextView) view.findViewById(R.id.menu);
            mTextGroup = (LinearLayout) view.findViewById(R.id.menu_base);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMenuView.getText() + "'";
        }
    }
}