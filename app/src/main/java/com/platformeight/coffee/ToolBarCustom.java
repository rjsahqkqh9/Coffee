/*
 * Create by platform eight on 2019. 10. 24.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import android.graphics.Color;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class ToolBarCustom {

    ActionBar actionBar;
    TextView textTitle;

    ToolBarCustom(ActionBar action){
        setActionBar(action);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar_custom);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back_black);

    }
    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    public void setTitleText(TextView view) { this.textTitle = view; }
    public void setTitle(int titleId){ this.textTitle.setText(titleId); }
    public void setTitle(String title){ this.textTitle.setText(title); }
    public void setTitleColor(String colorcode){ this.textTitle.setTextColor(Color.parseColor(colorcode)); }
    public void setTitleText(TextView view, int titleId){
        this.textTitle = view;
        this.textTitle.setText(titleId);
    }
    public void setTitleText(TextView view, String title){
        this.textTitle = view;
        this.textTitle.setText(title);
    }

}
