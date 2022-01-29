package com.example.courseregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class student_register extends AppCompatActivity {
    Button b1;

    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7;

    String username, usn, section, password, cPassword, email;
    int sem;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_register_activity);

        db = new DBHelper(this);

        editText1 = findViewById(R.id.editTextTextPersonName13);
        editText2 = findViewById(R.id.editTextTextPersonName14);
        editText3 = findViewById(R.id.editTextTextPersonName15);
        editText4 = findViewById(R.id.editTextTextPersonName16);
        editText5 = findViewById(R.id.editTextTextPersonName17);
        editText6 = findViewById(R.id.editTextTextPassword2);
        editText7 = findViewById(R.id.editTextTextPersonName18);

        b1 = findViewById(R.id.button15);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editText1.getText().toString();
                usn = editText2.getText().toString();
                sem = Integer.parseInt(String.valueOf(editText3.getText()));
                section = editText4.getText().toString();
                email = editText5.getText().toString();
                password = editText6.getText().toString();
                cPassword = editText7.getText().toString();

                if(password.compareTo(cPassword) == 0){
                    Boolean checkUser = db.checkStudentInDB(usn);
                    if(checkUser == false){
                        Boolean insertUser = db.registerStudentLogin(username, usn, sem, section, email, password);
                        if(insertUser == true){
                            Toast.makeText(getApplicationContext(), "Account created successfully, go to login page and login", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "User already exists, you can login directly", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}