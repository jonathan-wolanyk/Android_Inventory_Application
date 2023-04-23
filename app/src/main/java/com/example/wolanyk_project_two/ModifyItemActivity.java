package com.example.wolanyk_project_two;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ModifyItemActivity extends AppCompatActivity {
    private EditText itemQuantityEditText;
    private Button saveButton;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);

        // Get the item ID passed from previous activity
        itemId = getIntent().getIntExtra("itemId", -1);

        // Initialize UI elements
        itemQuantityEditText = findViewById(R.id.modifyQuantityText);
        saveButton = findViewById(R.id.saveButton);

        // Set click listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated quantity from UI
                int updatedQuantity = Integer.parseInt(itemQuantityEditText.getText().toString().trim());

                // Update item in database
                updateItem(updatedQuantity);

                // Show success message
                Toast.makeText(ModifyItemActivity.this, "Item updated successfully", Toast.LENGTH_SHORT).show();

                // Finish activity
                finish();
            }
        });
    }

    // Update item in database
    private void updateItem(int updatedQuantity) {
        // Get database instance
        DatabaseInventory inventoryDatabase = new DatabaseInventory(this);
        SQLiteDatabase db = inventoryDatabase.getWritableDatabase();

        // Prepare values to update
        ContentValues values = new ContentValues();
        values.put(InventoryItemContract.COLUMN_QUANTITY, updatedQuantity);

        // Update item in database
        db.update(InventoryItemContract.TABLE_NAME, values, InventoryItemContract.COLUMN_ID + " = ?", new String[]{String.valueOf(itemId)});

        // Check if the updated quantity is less than 10 and send SMS notification if necessary
        if (NotificationsActivity.GetNotificationSwitch() && updatedQuantity < 10) {
            String message = "Low inventory alert: Quantity of that item is less than 10!";
            sendSMSNotification(message);
        }
        else {

        }

        // Close database connection
        db.close();
    }

    // Method to send SMS notification
    private void sendSMSNotification(String message) {
        // Simply making a toast for now
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}