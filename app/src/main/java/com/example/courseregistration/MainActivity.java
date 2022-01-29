package com.example.courseregistration;

        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.Button;

        import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper myDB;
    Button admin;
    Button student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);

        admin = findViewById(R.id.button);
        admin.setOnClickListener(view -> {
            Intent i1 = new Intent(getApplicationContext(), admin_login.class);
            startActivity(i1);
        });

        student = findViewById(R.id.button2);
        student.setOnClickListener(view -> {
            Intent i2 = new Intent(getApplicationContext(), student_login.class);
            startActivity(i2);
        });
    }
}