/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.platformeight.coffee.dummy.DummyContent.DummyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import static com.platformeight.coffee.Constant.DECIMAL_FORMAT;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MycartRecyclerViewAdapter extends RecyclerView.Adapter<MycartRecyclerViewAdapter.ViewHolder> {

    private final List<JSONObject> mValues;
    private final CartFragment.OnListFragmentInteractionListener mListner;

    public MycartRecyclerViewAdapter(List<JSONObject> items, CartFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListner = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mMenuView.setText("menu "+position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        try {
            holder.mCartView.setText(holder.mItem.getString("name"));
            //JSONObject js = new JSONObject(holder.mItem.getString("base"));
            String base = holder.mItem.getString("base");
            String info = String.format("- 기본 : %s (%s원)", base, DECIMAL_FORMAT.format(holder.mItem.getInt(base)));
            JSONArray ja = new JSONArray(holder.mItem.getString("opt"));
            JSONObject opt = ja.getJSONObject(0);
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += "\n" + String.format("- "+str+" : +%s원", DECIMAL_FORMAT.format(opt.getInt(str)));
            }
            holder.mCartInfoView.setText(info);
            holder.count = holder.mItem.getInt("amount");
            holder.mCartQuanView.setText(String.format("%d 개", holder.count));
            holder.price = holder.mItem.getInt("price")*holder.count;
            holder.mCartPriceView.setText(String.format("%s원", DECIMAL_FORMAT.format(holder.price)));
            holder.mCartMinsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (holder.count<=1) return;
                        holder.mItem.put("amount",--holder.count);
                        holder.mCartQuanView.setText(String.format("%d 개", holder.count));
                        holder.price = holder.mItem.getInt("price")*holder.count;
                        holder.mCartPriceView.setText(String.format("%s원", DECIMAL_FORMAT.format(holder.price)));
                        calPrice();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.mCartplusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        holder.mItem.put("amount",++holder.count);
                        holder.mCartQuanView.setText(String.format("%d 개", holder.count));
                        holder.price = holder.mItem.getInt("price")*holder.count;
                        holder.mCartPriceView.setText(String.format("%s원", DECIMAL_FORMAT.format(holder.price)));
                        calPrice();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.mCartDelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Toast.makeText(v.getContext(), holder.mItem.getString("no")+" "+holder.mItem.getString("name")+"를 비웁니다.", Toast.LENGTH_SHORT).show();
                        mValues.remove(position);
                        calPrice();
                        notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            //holder.mMenuHotView.setText(String.format("- HOT : ￦%s원", format.format(js.getInt("hot"))));
            //holder.mMenuIceView.setText(String.format("- ICE : ￦%s원", format.format(js.getInt("ice"))));
            //holder.mMenuHotView.setText("HOT : " + js.getString("hot"));
            //holder.mMenuIceView.setText("ICE : " + js.getString("ice"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public String getJson(){
        JSONArray ja = new JSONArray();
        for(int i=0;i<mValues.size();i++){
            ja.put(mValues.get(i));
        }
        Log.d("MycartRecycler", "getJson: "+ja.toString());
        return ja.toString();
    }
    public void calPrice() throws JSONException {
        int sum=0;
        for (int i=0;i<mValues.size();i++){
            sum += mValues.get(i).getInt("amount")*mValues.get(i).getInt("price");
        }
        mListner.onListFragmentInteraction(sum);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //public final View mView;
        public final TextView mCartView;
        public final TextView mCartInfoView;
        public final TextView mCartPriceView;
        public final TextView mCartQuanView;
        public final TextView mCartDelView;
        public final Button mCartMinsView;
        public final Button mCartplusView;

        public JSONObject mItem;
        public int count;
        public int price;

        public ViewHolder(View view) {
            super(view);
            //mView = view;
            mCartView = (TextView) view.findViewById(R.id.cart_name);
            mCartInfoView = (TextView) view.findViewById(R.id.cart_info);
            mCartPriceView = (TextView) view.findViewById(R.id.cart_price);
            mCartQuanView = (TextView) view.findViewById(R.id.cart_quan);
            mCartDelView = (TextView) view.findViewById(R.id.cart_del);
            mCartMinsView = (Button) view.findViewById(R.id.btn_minus);
            mCartplusView = (Button) view.findViewById(R.id.btn_plus);
            count=0;
            price=0;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCartView.getText() + "'";
        }
    }
}