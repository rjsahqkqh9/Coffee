/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.platformeight.coffee.servertask.ServerHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //TODO:리스트 구성 3개 랜덤리스트 이후 거리순 open, close, state로 회색처리
            Bundle bundle = getArguments();
            String location = "";
            LatLng lat = null;
            if (bundle != null) {
                location = bundle.getString("location");
                lat = new LatLng(bundle.getDouble("latx"),bundle.getDouble("laty"));
            }
            //Log.d("itemFragment", "location : "+ location);
            //recyclerView.setAdapter(new MyItemRecyclerViewAdapter(ShopContent.ITEMS, mListener));
            //recyclerView.setAdapter(new MyItemRecyclerViewAdapter(shopListSample(location), mListener));
            setLocation(location, lat);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,LinearLayoutManager.VERTICAL);
            //dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.recyclerview_divider));
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        return view;
    }
    private List<ShopData> mValues;
    public void setLocation(String lo, LatLng latLng) { //임시, 좌표값
        //TODO: 현재지역 좌표값으로 데이터베이스 조회하여 리스트 생성
        // 구단위 최상위 1개 랜덤리스트 3개 그외일반
        mValues = new ArrayList<ShopData>();
        //latLng = new LatLng(35.798838,128.583052);
        Log.d("setLocation", "lat:"+ latLng.latitude+ ", long: "+ latLng.longitude);
        mValues = new ServerHandle().getShopList(lo, latLng);
        if (mValues==null) {
            Log.d("setLocation", "mValues : null");
        } else {
            Collections.sort(mValues, new ShopListComparator());
        }
        //recyclerView.setAdapter(new MyItemRecyclerViewAdapter(shopListSample(lo), mListener));
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(mValues, mListener));
    }
    private List<ShopData> shopList(){
        mValues = new ArrayList<ShopData>();
        String menu = "";
        mValues.add(new ShopData(String.valueOf(1), "image", "vvip카페 " + 1, menu,1, "10:00", "17:00"));
        return mValues;
    }
    private List<ShopData> shopListSample(String lo){
        mValues = new ArrayList<ShopData>();
        String menu = shopMenuSample();
        mValues.add(new ShopData(String.valueOf(1), "image", lo+"vvip카페 " + 1, menu,1, "10:00", "17:00"));
        mValues.add(new ShopData(String.valueOf(2), "image", lo+"vip카페 " + 1, menu,1, "10:00", "17:00"));
        mValues.add(new ShopData(String.valueOf(3), "image", lo+"vip카페 " + 2, menu,1, "10:00", "17:00"));
        mValues.add(new ShopData(String.valueOf(4), "image", lo+"vip카페 " + 3, menu,1, "10:00", "17:00"));
        for (int no=1;no<=10;no++){
            mValues.add(new ShopData(String.valueOf(no), "image", lo+"카페 " + no, menu,1, "10:00", "17:00"));
        }
        return mValues;
    }
    private String shopMenuSample(){
        JSONArray js_menus = new JSONArray();
        try {
            JSONObject js_menu = new JSONObject();
            JSONArray ja = new JSONArray();
            JSONObject js1 = new JSONObject();
            js_menu.put("name","아메리카노");
            js_menu.put("image", "이미지주소값");
            js_menu.put("bnum",2);
            js_menu.put("onum",2);
            //js_menu.put("hot",4300);
            //js_menu.put("ice",5000);
            //js_menu.put("basic",4300);
            js1.put("HOT",4300);
            js1.put("ICE",5000);
            //js1.put("basic",4300);
            ja.put(js1);
            js_menu.put("base",ja);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            js1.put("휘핑크림",300);
            ja.put(js1);
            js_menu.put("opt",ja);
            js_menus.put(js_menu);

            js_menu = new JSONObject();
            ja = new JSONArray();
            js1 = new JSONObject();
            js_menu.put("name","카페라떼");
            js_menu.put("image", "이미지주소값");
            js_menu.put("bnum",2);
            js_menu.put("onum",1);
            //js_menu.put("hot",4300);
            //js_menu.put("ice",5000);
            //js_menu.put("basic",4300);
            js1.put("HOT",4500);
            js1.put("ICE",5300);
            //js1.put("basic",4300);
            ja.put(js1);
            js_menu.put("base",ja);
            ja = new JSONArray();
            js1 = new JSONObject();
            js1.put("샷추가",500);
            ja.put(js1);
            js_menu.put("opt",ja);
            js_menus.put(js_menu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js_menus.toString();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ShopData data);
    }
}