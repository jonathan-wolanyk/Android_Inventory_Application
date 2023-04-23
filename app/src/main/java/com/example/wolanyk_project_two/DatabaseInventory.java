package com.example.wolanyk_project_two;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseInventory extends SQLiteOpenHelper {

    // Define the database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "inventory.db";

    // Define the SQL statement to create the inventory table
    private static final String SQL_CREATE_INVENTORY_TABLE =
            "CREATE TABLE " + InventoryItemContract.TABLE_NAME + " (" +
                    InventoryItemContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    InventoryItemContract.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                    InventoryItemContract.COLUMN_QUANTITY + " INTEGER)";

    public DatabaseInventory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the SQL statement to create the inventory table
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + InventoryItemContract.TABLE_NAME + " ADD COLUMN NEW_COLUMN_NAME TEXT");
        }
    }

    // Method to remove an item from the inventory table based on item ID
    public void removeItemFromDatabase(int itemId) {
        // Get a writable instance of the database
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the selection and selectionArgs for the item ID
        String selection = InventoryItemContract.COLUMN_ID + "=?";
        String[] selectionArgs = { String.valueOf(itemId) };

        // Perform the delete operation on the inventory table
        db.delete(InventoryItemContract.TABLE_NAME, selection, selectionArgs);

        // Close the database
        db.close();
    }

    // Method to get a cursor containing all rows from the inventory table
    public Cursor getCursor() {
        // Get a readable instance of the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the projection (columns to retrieve)
        String[] projection = {
                InventoryItemContract.COLUMN_ID,
                InventoryItemContract.COLUMN_ITEM_NAME,
                InventoryItemContract.COLUMN_QUANTITY
        };

        // Perform the query operation to retrieve all rows from the inventory table
        Cursor cursor = db.query(
                InventoryItemContract.TABLE_NAME, // Table name
                projection, // Columns to retrieve
                null, // Selection
                null, // SelectionArgs
                null, // GroupBy
                null, // Having
                null // OrderBy
        );

        // Return the cursor
        return cursor;
    }
}