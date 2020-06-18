/*
 *  Create by platform eight on 2020. 6. 16.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import java.time.LocalTime;
import java.util.Comparator;

public class ShopListComparator implements Comparator {
    ShopData shop1;
    ShopData shop2;
    @Override
    public int compare(Object o1, Object o2) {
        shop1 = (ShopData) o1;
        shop2 = (ShopData) o2;
        if(shop1.getState() > shop2.getState()){
            return -1;
        }else if(shop1.getState() < shop2.getState()){
            return 1;
        }else{
            return 0;
        }
    }
}
