/*
 *  Create by platform eight on 2020. 7. 6.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LicenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LicenseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private List<LicenseData> mList;

    public LicenseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LicenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LicenseFragment newInstance(String param1, String param2) {
        LicenseFragment fragment = new LicenseFragment();
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
        View view = inflater.inflate(R.layout.fragment_license_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            item();
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
            //dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.recyclerview_divider));
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        // Inflate the layout for this fragment
        return view;
    }

    /*라이센스 리사이클뷰 시작*/
    public void addItem(String title, String desc) {
        LicenseData item = new LicenseData(title, desc);
        mList.add(item);
    }
    public void item(){
        list();
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        LicenseAdapter mAdapter = new LicenseAdapter(mList) ;
        recyclerView.setAdapter(mAdapter) ;

    }

    public void list(){
        mList = new ArrayList<>();
        addItem(getString(R.string.Google_Firebase_Title), getString(R.string.Google_Firebase_Content));
        addItem(getString(R.string.Crashlytics_Title), getString(R.string.Crashlytics_Content));
        addItem(getString(R.string.Junit_Title), getString(R.string.Junit_Content));
        addItem(getString(R.string.Junit_Platform_Runner_Title), getString(R.string.Junit_Platform_Runner_Content));
        addItem(getString(R.string.Apache_License_Version_2_0_Title), getString(R.string.Apache_License_Version_2_0_Content));
        addItem(getString(R.string.MIT_License_Title), getString(R.string.MIT_License_Content));
        addItem(getString(R.string.Eclipse_Public_License_1_0_Title), getString(R.string.Eclipse_Public_License_1_0_Content));
        addItem(getString(R.string.Eclipse_Public_License_V2_0_Title), getString(R.string.Eclipse_Public_License_V2_0_Content));
        //mAdapter.notifyDataSetChanged() ;
    }
    /*라이센스 리사이클뷰 끝*/
}