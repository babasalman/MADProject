package com.example.courseregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class admin_login extends AppCompatActivity {
    DBHelper db;

    EditText editText1;
    EditText editText2;

    String user;
    String password;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_activity);

        db = new DBHelper(this);

        editText1 = findViewById(R.id.admin_username);
        editText2 = findViewById(R.id.admin_password);

        b1 = findViewById(R.id.button3);
        b1.setOnClickListener(view -> {
            user = editText1.getText().toString();
            password = editText2.getText().toString();

            Boolean checkUser = db.adminInDB(user);
            if(checkUser == true){

                Boolean adminStatus = db.checkAdminLogin(user, password);
                if(adminStatus == true){
                    Intent i1 = new Intent(getApplicationContext(), admin_dashboard.class);
                    startActivity(i1);
                }
                else{
                    Toast.makeText(admin_login.this, "wrong password", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Boolean add = db.adminAddCreds(user, password);
                if(add == true){
                    Toast.makeText(admin_login.this, "", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}