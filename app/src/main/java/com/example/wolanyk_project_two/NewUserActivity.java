package com.example.wolanyk_project_two;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewUserActivity extends AppCompatActivity {
    EditText createUsernameEditText;
    EditText createPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        createUsernameEditText = findViewById(R.id.createUsernameInputText);
        createPasswordEditText = findViewById(R.id.createPasswordInputText);
        Button createLoginButton = findViewById(R.id.createLoginButton);

        // Submits the login button when user presses done on password
        EditText passwordEditText = findViewById(R.id.createPasswordInputText);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Call the login button's click event
                    createLoginButton.performClick();
                    return true;
                }
                return false;
            }
        });
        createLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = createUsernameEditText.getText().toString().trim();
                String password = createPasswordEditText.getText().toString().trim();

                DatabaseLogin dbHelper = new DatabaseLogin(NewUserActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("username", username);
                values.put("password", password);
                long newRowId = db.insert("loginTable", null, values);
                db.close();

                if (newRowId != -1) {
                    // Insertion successful
                } else {
                    Toast.makeText(NewUserActivity.this, "Account creation unsuccessful", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(NewUserActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                // Start MainActivity
                Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}