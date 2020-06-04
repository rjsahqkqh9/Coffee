package com.platformeight.coffee.dummy;

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
public class ShopContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ShopData> ITEMS = new ArrayList<ShopData>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ShopData> ITEM_MAP = new HashMap<String, ShopData>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createShopItem(i));
        }
    }

    private static void addItem(ShopData item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.getNo()), item);
    }

    private static ShopData createShopItem(int position) {
        return new ShopData(String.valueOf(position), "카페 sample " + position, makeDetails(position));
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