package com.example.wolanyk_project_two;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class InventoryAdapter extends CursorAdapter {

    private DatabaseInventory inventoryDatabase;

    public InventoryAdapter(Context context, Cursor cursor, int flags, DatabaseInventory databaseInventory) {
        super(context, cursor, flags);
        inventoryDatabase = databaseInventory;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the layout for a new list item view
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get the column index for each column in your inventory item table
        int idColumnIndex = cursor.getColumnIndexOrThrow(InventoryItemContract.COLUMN_ID);
        int itemNameColumnIndex = cursor.getColumnIndexOrThrow(InventoryItemContract.COLUMN_ITEM_NAME);
        int quantityColumnIndex = cursor.getColumnIndexOrThrow(InventoryItemContract.COLUMN_QUANTITY);

        // Extract the values from the cursor for the current row
        int id = cursor.getInt(idColumnIndex);
        String itemName = cursor.getString(itemNameColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);

        // Bind the values to the appropriate views in the custom list item layout
        TextView itemNameTextView = view.findViewById(R.id.itemNameTextView);
        TextView quantityTextView = view.findViewById(R.id.itemDescriptionTextView);
        Button removeButton = view.findViewById(R.id.removeButton);

        itemNameTextView.setText(itemName);
        quantityTextView.setText(String.valueOf(quantity));

        // Declare cursor as final
        final Cursor finalCursor = cursor;

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call removeItemFromDatabase() on DatabaseInventory instance
                inventoryDatabase.removeItemFromDatabase(id);

                // Update UI components with the latest data
                Cursor newCursor = inventoryDatabase.getCursor(); // Get a new cursor with the latest data from the database
                swapCursor(newCursor); // Update the adapter with the new cursor
                notifyDataSetChanged(); // Notify the adapter that the data set has changed
            }
        });

        Button modifyButton = view.findViewById(R.id.modifyButton); // Assuming you have a modify button in your list item layout
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the item ID from the cursor for the current row
                int id = finalCursor.getInt(finalCursor.getColumnIndexOrThrow(InventoryItemContract.COLUMN_ID));

                // Get the item name and quantity from the cursor for the current row
                String itemName = finalCursor.getString(finalCursor.getColumnIndexOrThrow(InventoryItemContract.COLUMN_ITEM_NAME));
                int itemQuantity = finalCursor.getInt(finalCursor.getColumnIndexOrThrow(InventoryItemContract.COLUMN_QUANTITY));

                // Create an intent to start ModifyItemActivity
                Intent intent = new Intent(context, ModifyItemActivity.class);

                // Pass the item information as extras to the intent
                intent.putExtra("itemId", id);
                intent.putExtra("itemName", itemName);
                intent.putExtra("itemQuantity", itemQuantity);

                // Start ModifyItemActivity
                context.startActivity(intent);
            }
        });
    }
}