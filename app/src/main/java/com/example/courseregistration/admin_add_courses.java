package com.example.courseregistration;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class admin_add_courses extends AppCompatActivity {
    Button date;
    Button time;
    Button add_course;
    AlertDialog.Builder builder;

    TextView date_text, time_text;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    RadioGroup radioGroup;
    RadioButton radioButton;

    DBHelper mydb;

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;

    String course_name;
    String course_code;
    String in_charge_name;
    String in_charge_email;
    String course_type;
    String date_db, time_db;
    String time_format;

    int sem;
    int radioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_courses_activity);

        mydb = new DBHelper(this);
        builder = new AlertDialog.Builder(this);

        time_text = findViewById(R.id.textView22);
        time = findViewById(R.id.button7);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(admin_add_courses.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());

                        SimpleDateFormat format = new SimpleDateFormat("k:mm");
                        time_format = format.format(c.getTime());
                        time_text.setText(time_format);
                    }
                }, hours, minute, true);
                timePickerDialog.show();
            }
        });

        date_text = findViewById(R.id.textView23);
        date = findViewById(R.id.button8);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(admin_add_courses.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        date_text.setText(d + "/" + (m + 1) + "/" + y);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        date_db = DateFormat.getDateInstance().format(calendar.getTime());
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        add_course = findViewById(R.id.button9);
        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Are you sure ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                editText1 = findViewById(R.id.editTextTextPersonName);
                                editText2 = findViewById(R.id.editTextTextPersonName2);
                                editText3 = findViewById(R.id.editTextTextPersonName3);
                                editText4 = findViewById(R.id.editTextTextPersonName4);
                                editText5 = findViewById(R.id.editTextTextPersonName5);

                                radioGroup = findViewById(R.id.radio_group);
                                radioID = radioGroup.getCheckedRadioButtonId();
                                radioButton = findViewById(radioID);

                                course_name = editText1.getText().toString();
                                course_code = editText2.getText().toString();
                                in_charge_name = editText3.getText().toString();
                                in_charge_email = editText4.getText().toString();
                                sem = Integer.parseInt(editText5.getText().toString());
                                course_type = radioButton.getText().toString();
                                time_db = time_format;

                                Boolean checkCourse = mydb.admin_check_add_courses(course_code);
                                if(checkCourse == false){
                                    Boolean addCourse = mydb.admin_add_courses(course_code, course_name, in_charge_name, in_charge_email, sem, course_type, date_db, time_db);
                                    if(addCourse == true){
                                        Toast.makeText(getApplicationContext(), "Course added successfully", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Error in adding course", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(admin_add_courses.this, "Course already exists", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}