package com.example.wolanyk_project_two;

import android.provider.BaseColumns;

public class InventoryTableCategories {
    private InventoryTableCategories() {}

    public static class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_QUANTITY = "quantity";
    }
}
