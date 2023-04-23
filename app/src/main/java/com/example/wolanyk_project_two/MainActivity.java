package com.example.wolanyk_project_two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseLogin loginDatabase = new DatabaseLogin(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        Button newUserButton = findViewById(R.id.newUserButton);

        // Submits the login button when user presses done on password
        EditText passwordEditText = findViewById(R.id.passwordInputText);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Call the login button's click event
                    loginButton.performClick();
                    return true;
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username and password input from the EditText fields
                EditText usernameEditText = findViewById(R.id.usernameInputText);
                EditText passwordEditText = findViewById(R.id.passwordInputText);

                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                SQLiteDatabase db = loginDatabase.getReadableDatabase();
                String[] projection = {"username", "password"};
                String selection = "username=? AND password=?";
                String[] selectionArgs = {username, password};
                String invalidUserText = "Invalid username or password. New user? Please register!";
                Cursor cursor = db.query("loginTable", projection, selection, selectionArgs, null, null, null);

                if (cursor.moveToFirst()) {
                    // Username and password match
                    cursor.close();
                    db.close();
                    Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                    startActivity(intent);
                } else {
                    // Username and password do not match
                    Toast.makeText(MainActivity.this, invalidUserText, Toast.LENGTH_SHORT).show();
                    cursor.close();
                    db.close();
                }
            }
        });

        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to navigate to another page when newUserButton is clicked
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });
    }
}