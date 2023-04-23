package com.example.wolanyk_project_two;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    private EditText itemNameEditText;
    private EditText itemQuantityText;
    private Button saveButton;
    private DatabaseInventory databaseInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Initialize views
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemQuantityText = findViewById(R.id.itemQuantityEditText);
        saveButton = findViewById(R.id.saveButton);

        // Initialize database helper
        databaseInventory = new DatabaseInventory(this);

        // Set click listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input from EditTexts
                String itemName = itemNameEditText.getText().toString();
                String itemDescription = itemQuantityText.getText().toString();

                // Insert item into database
                SQLiteDatabase db = databaseInventory.getWritableDatabase();
                ContentValues values = new ContentValues();
                // Omit the "_id" column
                values.put("name", itemName);
                values.put("description", itemDescription);
                db.insert("items", null, values);
                db.close();

                // Close activity
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database helper
        databaseInventory.close();
    }
}
