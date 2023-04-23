package com.example.wolanyk_project_two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class InventoryActivity extends AppCompatActivity {

    private ListView inventoryListView;
    private InventoryAdapter inventoryAdapter;
    private DatabaseInventory databaseInventory;
    private static final int REQUEST_CODE_ADD_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Initialize views
        inventoryListView = findViewById(R.id.inventoryListView);

        // Initialize database helper
        databaseInventory = new DatabaseInventory(this);

        // Set click listener for notifications button
        Button myButton = findViewById(R.id.notificationsButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for add item button
        Button addItemButton = findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity to add a new item
                Intent intent = new Intent(InventoryActivity.this, AddItemActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ITEM);
            }
        });

        // Initialize and set the custom adapter for the ListView
        inventoryAdapter = new InventoryAdapter(this, null, 0, databaseInventory);
        inventoryListView.setAdapter(inventoryAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the inventory items when the activity resumes
        loadInventoryItems();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database helper
        databaseInventory.close();
    }

    private void loadInventoryItems() {
        // Retrieve data from the items table
        SQLiteDatabase db = databaseInventory.getReadableDatabase();
        Cursor cursor = db.query("items", null, null, null, null, null, null);

        // Update the cursor in the custom adapter
        inventoryAdapter.changeCursor(cursor);
    }

    private Cursor getInventoryItemsCursor() {
        // Retrieve data from the items table
        SQLiteDatabase db = databaseInventory.getReadableDatabase();
        return db.query("items", null, null, null, null, null, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            // Refresh the inventory items when an item is added
            loadInventoryItems();
        }
    }
}