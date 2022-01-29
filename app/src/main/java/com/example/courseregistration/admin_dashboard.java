package com.example.courseregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class admin_dashboard extends AppCompatActivity {
    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_activity);

        b1 = findViewById(R.id.button4);
        b1.setOnClickListener(view -> {
            Intent i3 = new Intent(admin_dashboard.this, admin_add_courses.class);
            startActivity(i3);
        });

        b2 = findViewById(R.id.button6);
        b2.setOnClickListener(view -> {
            Intent i5 = new Intent(admin_dashboard.this, admin_view_list.class);
            startActivity(i5);
        });

        b3 = findViewById(R.id.button5);
        b3.setOnClickListener(view -> {
            Intent edit = new Intent(admin_dashboard.this, admin_editcourse.class);
            startActivity(edit);
        });
    }
}