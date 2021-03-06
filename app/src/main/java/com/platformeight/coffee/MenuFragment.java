/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MenuFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "MenuFragment";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MenuFragment.OnListFragmentInteractionListener mListener;
    private String menu_json;
    private boolean fold = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MenuFragment newInstance(int columnCount) {
        MenuFragment fragment = new MenuFragment();
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
        //View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // Set the adapter
        if (view.findViewById(R.id.menu_category_list) instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.menu_category_list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            Bundle bundle = getArguments();
            if (bundle != null) {
                menu_json = bundle.getString(Constant.MENU);
                String name = bundle.getString("name");
                TextView tv_name = (TextView) view.findViewById(R.id.menu_category);
                tv_name.setText(name);
                Log.d(TAG, "onCreateView: "+name);
            }
            Log.d(TAG, "menu json : "+ menu_json);
            List<JSONObject> menu = new ArrayList<JSONObject>();
            try {
                JSONArray ja = new JSONArray(menu_json);
                for(int i=0;i<ja.length();i++){
                    JSONObject js = (JSONObject) ja.get(i);
                    menu.add(js);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //recyclerView.setHasFixedSize(true);
            //recyclerView.setNestedScrollingEnabled(false);
            //recyclerView.setAdapter(new MymenuRecyclerViewAdapter(DummyContent.ITEMS, mListener));
            recyclerView.setAdapter(new MymenuRecyclerViewAdapter(menu, mListener));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);


            LinearLayout panel = view.findViewById(R.id.menu_category_panel_folding);
            ImageView image_fold = view.findViewById(R.id.menu_category_btn_folding);
            Animation arrow_down = AnimationUtils.loadAnimation(context, R.anim.category_down);
            Animation arrow_up = AnimationUtils.loadAnimation(context, R.anim.category_up);

            panel.setOnClickListener(v -> {
                if (fold){ //펴기
                    image_fold.startAnimation(arrow_up);
                    recyclerView.setVisibility(View.VISIBLE);
                } else { //접기
                    image_fold.startAnimation(arrow_down);
                    recyclerView.setVisibility(View.GONE);
                }
                this.fold=!fold;
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuFragment.OnListFragmentInteractionListener) {
            mListener = (MenuFragment.OnListFragmentInteractionListener) context;
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
        void onListFragmentInteraction(JSONObject item);
    }
}