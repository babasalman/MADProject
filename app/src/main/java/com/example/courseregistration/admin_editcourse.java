package com.example.courseregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class admin_editcourse extends AppCompatActivity{

    DBHelper mydb;
    Button date;
    Button time;
    Button delete_course;
    Button edit_course;

    TextView date_text, time_text;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    RadioGroup radioGroup;
    RadioButton radioButton;

    EditText editText1, editText2, editText3, editText4;
    int sem, radioID;
    String time_format, spin;
    String course_name, course_code, in_charge_name, in_charge_email, course_type, date_db, time_db;

    Spinner spinner;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_editcourse_activity);
        mydb = new DBHelper(this);
        builder = new AlertDialog.Builder(this);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                spin = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        loadSpinnerData();


        time_text = findViewById(R.id.textView13);
        time = findViewById(R.id.button10);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(admin_editcourse.this, new TimePickerDialog.OnTimeSetListener() {
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

        date_text = findViewById(R.id.textView14);
        date = findViewById(R.id.button11);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(admin_editcourse.this, new DatePickerDialog.OnDateSetListener() {
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

        edit_course = findViewById(R.id.button12);
        edit_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Did you enter the correct details?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                editText1 = findViewById(R.id.editTextTextPersonName6);
                                editText2 = findViewById(R.id.editTextTextPersonName7);
                                editText3 = findViewById(R.id.editTextTextPersonName8);
                                editText4 = findViewById(R.id.editTextTextPersonName9);

                                radioGroup = findViewById(R.id.radio_group1);
                                radioID = radioGroup.getCheckedRadioButtonId();
                                radioButton = findViewById(radioID);

                                course_name = spin;
                                course_code = editText1.getText().toString();
                                in_charge_name = editText2.getText().toString();
                                in_charge_email = editText3.getText().toString();
                                sem = Integer.parseInt(editText4.getText().toString());
                                course_type = radioButton.getText().toString();
                                time_db = time_format;

                                Boolean checkCourse = mydb.admin_check_edit_courses(course_name);
                                if(checkCourse == true){
                                    Boolean editCourse = mydb.admin_edit_courses(course_code, course_name, in_charge_name, in_charge_email, sem, course_type, date_db, time_db);
                                    if(editCourse == true){
                                        Toast.makeText(getApplicationContext(), "Course edited successfully", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Error in editing course", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(admin_editcourse.this, "Course does not exists", Toast.LENGTH_LONG).show();
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

        delete_course = findViewById(R.id.button16);
        delete_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Do you want to delete")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               String course = spin;
                               Boolean adminDeleteCourse = mydb.adminDeleteCourse(course);
                               if(adminDeleteCourse == true)
                                   Toast.makeText(admin_editcourse.this, "Record deleted", Toast.LENGTH_LONG).show();
                               else
                                   Toast.makeText(admin_editcourse.this, "Cannot delete this record", Toast.LENGTH_LONG).show();
                               loadSpinnerData();
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
    private void loadSpinnerData() {
        mydb = new DBHelper(this);
        List<String> labels = mydb.getEditCourseNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}