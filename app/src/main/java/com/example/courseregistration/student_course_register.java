package com.example.courseregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class student_course_register extends AppCompatActivity {
    Button register, lookup;
    Spinner coreSpinner, electiveSpinner;
    AutoCompleteTextView auto;
    EditText editText1;
    DBHelper mydb;

    String core1, elective1, u1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_course_register_activity);
        mydb = new DBHelper(this);

        coreSpinner = findViewById(R.id.spinner3);
        electiveSpinner = findViewById(R.id.spinner4);
        auto = findViewById(R.id.autoCompleteTextView);
        editText1 = findViewById(R.id.editTextTextPersonName11);


        lookup = findViewById(R.id.button20);
        lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u1 = editText1.getText().toString();
                loadCoreSpinner(u1);
                loadElectiveSpinner(u1);
                loadAutoComplete(u1);
            }
        });

        register = findViewById(R.id.button21);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elective1 = auto.getText().toString();
                int coreStatus = 1;
                Boolean check = mydb.checkStudentEntry(u1);
                if(check == false){
                    Boolean addCourse = mydb.studentAddCourse(u1, coreStatus, elective1);
                    if(addCourse == true) {
                        Toast.makeText(student_course_register.this, "Courses Registered successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(student_course_register.this, "Cannot register", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(student_course_register.this, "Cannot register again", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadCoreSpinner(String usn) {
        List<String> labels = mydb.getCoreCourseStudent(usn);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coreSpinner.setAdapter(dataAdapter);
        Toast.makeText(student_course_register.this, "reached", Toast.LENGTH_LONG).show();
    }

    private void loadElectiveSpinner(String usn) {
        List<String> labels = mydb.getElectiveCourseStudent(usn);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        electiveSpinner.setAdapter(dataAdapter);
    }

    private void loadAutoComplete(String u1){
        List<String> labels = mydb.getElectiveCourseStudent(u1);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, labels);
        auto.setAdapter(dataAdapter);
        auto.setThreshold(1);
    }
}