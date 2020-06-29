/*
 *  Create by platform eight on 2020. 6. 16.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.platformeight.coffee.servertask.ServerHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.platformeight.coffee.Constant.DECIMAL_FORMAT;
import static com.platformeight.coffee.Constant.MYORDERS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OrderDetailFragment.OnListFragmentInteractionListener mListener;

    char isCheck;
    int order_no;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetailFragment newInstance(String param1, String param2) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        TextView name = view.findViewById(R.id.order_detail_name);
        TextView address = view.findViewById(R.id.order_detail_address);

        TextView total_quan = view.findViewById(R.id.order_detail_total_quantity);
        TextView total_price = view.findViewById(R.id.order_detail_total_price);

        TextView point = view.findViewById(R.id.order_detail_point);
        TextView price = view.findViewById(R.id.order_detail_price);

        Bundle bundle = getArguments();
        if (bundle != null && !bundle.isEmpty()) {
            String myorder = bundle.getString(MYORDERS);
            try {
                JSONObject order = new JSONObject(myorder);
                order_no = order.getInt("no");
                name.setText(order.getString("name"));
                address.setText(order.getString("address"));
                /*
                ListView listView = (ListView) view.findViewById(R.id.order_detail_list);
                final MyorderdetailAdapter myAdapter = new MyorderdetailAdapter(getContext(),new JSONArray(order.getString("detail")));
                listView.setAdapter(myAdapter);
                */
                JSONArray ja = new JSONArray(order.getString("detail"));
                LinearLayout layout = view.findViewById(R.id.order_detail_group);
                for (int i=0;i<ja.length();i++) {
                    layout.addView(setlist(ja,i));
                    if (i<ja.length()-1){
                        View v = new View(getContext());
                        v.setBackgroundColor(Color.BLACK);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
                        v.setLayoutParams(params);
                        layout.addView(v);
                    }
                }

                total_quan.setText(order.getString("order_amount"));
                total_price.setText(DECIMAL_FORMAT.format(order.getInt("order_price")));

                point.setText(DECIMAL_FORMAT.format(order.getInt("order_point")));
                price.setText(DECIMAL_FORMAT.format(order.getInt("total_price")));

                isCheck = order.getString("isCheck").charAt(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Button btn_check = view.findViewById(R.id.order_detail_btn_check);
        btn_check.setOnClickListener(v -> mListener.onListFragmentInteraction());
        Button btn_cancel = view.findViewById(R.id.order_detail_btn_cancel);
        if (isCheck=='Y'){
            btn_cancel.setEnabled(false);
            //btn_cancel.setTextColor(Color.GRAY);
        } else {
            btn_cancel.setOnClickListener(v -> {
                //TODO:: 고객 입장 주문대기상태의 주문취소 처리 및 pg 취소요청
                new ServerHandle().cancelOrder(order_no);
                mListener.onListFragmentInteraction();
            });
            view.findViewById(R.id.order_detail_caution).setVisibility(View.GONE);
        }

        return view;
    }
    private View setlist(JSONArray items, int position){
        LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
        View view = mLayoutInflater.inflate(R.layout.fragment_detail, null);
        TextView name = (TextView)view.findViewById(R.id.order_detail_menu_name);
        TextView quan = (TextView)view.findViewById(R.id.order_detail_menu_quantity);
        TextView price = (TextView)view.findViewById(R.id.order_detail_menu_price);

        try {
            JSONObject js = items.getJSONObject(position);
            String info = String.format("%s %s", js.getString("base"), js.getString("name"));
            JSONArray ja = new JSONArray(js.getString("opt"));
            JSONObject opt = ja.getJSONObject(0);
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            name.setText(info);
            quan.setText(js.getString("amount"));
            price.setText(DECIMAL_FORMAT.format(js.getInt("price")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderDetailFragment.OnListFragmentInteractionListener) {
            mListener = (OrderDetailFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction();
    }
}