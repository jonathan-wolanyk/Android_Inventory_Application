package com.example.wolanyk_project_two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

public class NotificationsActivity extends AppCompatActivity {
    private static CompoundButton notificationsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notificationsSwitch = findViewById(R.id.notificationsSwitch);

        Button myButton = findViewById(R.id.returnToInventoryButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });
    }

    public static boolean GetNotificationSwitch(){
        if (notificationsSwitch != null){
            return notificationsSwitch.isChecked();
        }
        else {
            return false;
        }
    }
}