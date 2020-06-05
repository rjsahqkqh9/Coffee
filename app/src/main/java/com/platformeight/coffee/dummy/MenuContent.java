package com.platformeight.coffee.dummy;

import com.platformeight.coffee.MenuData;
import com.platformeight.coffee.ShopData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MenuContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<MenuData> ITEMS = new ArrayList<MenuData>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, MenuData> ITEM_MAP = new HashMap<String, MenuData>();

    private static final int COUNT = 10;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createShopItem(i));
        }
    }

    private static void addItem(MenuData item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.getNo()), item);
    }

    private static MenuData createShopItem(int position) {
        return new MenuData(String.valueOf(position), "메뉴명 "+position);
        //return new ShopData(String.valueOf(position), "카페 sample " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}