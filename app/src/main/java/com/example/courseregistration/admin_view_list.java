package com.example.courseregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class admin_view_list extends AppCompatActivity {
    AlertDialog.Builder builder;
    StringBuffer buffer;
    Button view, send;
    Spinner spinner;
    DBHelper mydb;
    String spin, iemail, studentlist;

    String sEmail;
    String sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_list_activity);
        builder = new AlertDialog.Builder(this);

        mydb = new DBHelper(this);
        spinner = findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                spin = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        loadSubjectSpinner();

        view = findViewById(R.id.button17);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String students = mydb.getAllData(spin);
                showPeople(spin, students);
            }
        });

        send = findViewById(R.id.button18);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i;
                List<String> labels = mydb.getSubjects();
                for(i = 0; i < labels.size(); i++){
                    String students = mydb.getAllData(spin);
                    String iemail = mydb.getIEmail(labels.get(i));
                    sendEmail(iemail, students);
                }
            }
        });
    }
    private void loadSubjectSpinner() {
        List<String> labels = mydb.getSubjects();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void showPeople(String title, String Message){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void sendEmail(String iEmail, String studentList){
        sEmail = "salmanmadproject@gmail.com";
        sPassword = "bmsce@123";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail, sPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(iEmail));
            message.setSubject("This list of students for your course");
            message.setText(studentList);

            new SendMail().execute(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private class SendMail extends AsyncTask<Message, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(admin_view_list.this, "Sending", "please wait", true, false);

        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if(s.equals("success")){
                Toast.makeText(admin_view_list.this, "sent", Toast.LENGTH_SHORT).show();
            }
        }
    }

}