package com.example.courseregistration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class student_login extends AppCompatActivity {
    Button b1;
    Button b2;

    EditText editText1;
    EditText editText2;

    String usn;
    String password;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login_activity);
        db = new DBHelper(this);

        b2 = findViewById(R.id.button14);
        b2.setOnClickListener(view -> {
            editText1 = findViewById(R.id.editTextTextPersonName12);
            editText2 = findViewById(R.id.editTextTextPassword);

            usn = editText1.getText().toString();
            password = editText2.getText().toString();

            Boolean checkUser = db.checkStudentInDB(usn);
            if(checkUser == true){
                Boolean loginUserCheck = db.checkStudentLogin(usn, password);
                if(loginUserCheck == true){
                    Intent i1 = new Intent(student_login.this, student_course_register.class);
                    startActivity(i1);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Register please", Toast.LENGTH_LONG).show();
                }
            }

        });

        b1 = findViewById(R.id.button13);
        b1.setOnClickListener(view -> {
            Intent i1 = new Intent(getApplicationContext(), student_register.class);
            startActivity(i1);
        });
    }
}