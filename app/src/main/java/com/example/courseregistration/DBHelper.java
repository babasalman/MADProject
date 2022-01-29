package com.example.courseregistration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CourseRegistration.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table student_DB(stname text, usn text PRIMARY KEY, sem int, section text, email text, password text)");
        DB.execSQL("create table admin_DB(username text, password text)");
        DB.execSQL("create table course_db(courseId text PRIMARY KEY, courseName text, inchargeName text, inchargeEmail text, sem int, courseType text, date text, time text)");
        DB.execSQL("create table studentCourse(usn text, coreSubjectStatus int, electiveSubject text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS student_DB");
        DB.execSQL("DROP TABLE IF EXISTS admin_DB");
    }

    public Boolean checkStudentInDB(String usn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from student_DB where usn = ?", new String[]{usn});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkStudentLogin(String usn, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from student_DB where usn = ? and password = ?", new String[]{usn, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean registerStudentLogin(String username, String usn, int sem, String section, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("stname", username);
        values.put("usn", usn);
        values.put("sem", sem);
        values.put("section", section);
        values.put("email", email);
        values.put("password", password);

        long result = db.insert("student_DB", null, values);
        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean admin_add_courses(String courseID, String courseName, String inchargeName, String inchargeEmail, int sem, String courseType, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("courseId", courseID);
        values.put("CourseName", courseName);
        values.put("inchargeName", inchargeName);
        values.put("inchargeEmail", inchargeEmail);
        values.put("sem", sem);
        values.put("courseType", courseType);
        values.put("date", date);
        values.put("time", time);

        long result = db.insert("course_db", null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean adminInDB(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select username from admin_DB where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean adminAddCreds(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("admin_DB", null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean admin_check_add_courses(String courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from course_db where courseId = ?", new String[]{courseID});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkAdminLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from admin_DB where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else
            return false;
    }


    public Boolean admin_check_edit_courses(String course_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from course_db where courseName = ?", new String[]{course_name});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean admin_edit_courses(String courseID, String courseName, String inchargeName, String inchargeEmail, int sem, String courseType, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("courseId", courseID);
        values.put("CourseName", courseName);
        values.put("inchargeName", inchargeName);
        values.put("inchargeEmail", inchargeEmail);
        values.put("sem", sem);
        values.put("courseType", courseType);
        values.put("date", date);
        values.put("time", time);

        long result = db.update("course_db", values, "courseName = ?", new String[]{courseName});
        if (result == -1)
            return false;
        else
            return true;
    }

    public List<String> getEditCourseNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT courseName from course_db";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public Boolean adminDeleteCourse(String course) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("course_db", "courseName = ?", new String[]{course});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getCoreCourseStudent(String usn) {
        SQLiteDatabase db = this.getReadableDatabase();
        String core = "Core";
        List<String> list = new ArrayList<String>();

        String selectSemQuery = "select courseName from course_db where courseType = ? AND sem in (select sem from student_DB where usn = ?)";
        Cursor cursor = db.rawQuery(selectSemQuery, new String[]{core, usn});

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<String> getElectiveCourseStudent(String usn) {
        SQLiteDatabase db = this.getReadableDatabase();
        String elective = "Elective";
        List<String> list = new ArrayList<String>();

        String selectSemQuery = "select courseName from course_db where courseType = ? AND sem in (select sem from student_DB where usn = ?)";
        Cursor cursor = db.rawQuery(selectSemQuery, new String[]{elective, usn});

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public Boolean checkStudentEntry(String u1) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor s = db.rawQuery("select * from studentCourse where usn = ?", new String[]{u1});

        if (s.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean studentAddCourse(String u1, int coreStatus, String elective1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("usn", u1);
        values.put("coreSubjectStatus", coreStatus);
        values.put("electiveSubject", elective1);

        long result = db.insert("studentCourse", null, values);
        if (result == -1)
            return false;
        else
            return true;

    }

    public List<String> getSubjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> list = new ArrayList<String>();

        String selectSemQuery = "select courseName from course_db";
        Cursor cursor = db.rawQuery(selectSemQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public String getAllData(String subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer list = new StringBuffer();
        String sub;

        String select = "select courseType from course_db where courseName = ?";
        Cursor cursor = db.rawQuery(select, new String[]{subject});
        cursor.moveToFirst();
        sub = cursor.getString(0);

        if(sub.compareTo("Elective") == 0){
            String elective = "select usn from studentCourse where electiveSubject = ?";
            Cursor e = db.rawQuery(elective, new String[]{subject});
            if(e.getCount() == 0){
                return String.valueOf(list);
            }
            if(e.getCount() > 0){
                if(e.moveToFirst()) {
                    do {
                        String stu = "select stname from student_DB where usn = ?";
                        Cursor s = db.rawQuery(stu, new String[]{e.getString(0)});
                        if (s.getCount() > 0) {
                            if (s.moveToFirst()) {
                                do {
                                    list.append(s.getString(0) + "\n");
                                } while (s.moveToNext());
                            }
                        }
                    }
                    while (e.moveToNext());
                }
            }
        }
        else{
            int count = 1;
            String core = "select usn from studentCourse where coreSubjectStatus = ?";
            Cursor c = db.rawQuery(core, new String[]{String.valueOf(count)});
            if(c.getCount() > 0){
                if(c.moveToFirst()){
                    do {
                        String c1 = "select stname from student_DB where usn = ?";
                        Cursor s1 = db.rawQuery(c1, new String[]{c.getString(0)});
                        if(s1.getCount() > 0){
                            if(s1.moveToFirst()){
                                do{
                                    list.append(s1.getString(0) + "\n");
                                }while(s1.moveToNext());
                            }
                        }
                    }while(c.moveToNext());
                }
            }
        }
        return String.valueOf(list);
    }

    public String getIEmail(String course){
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer list = new StringBuffer();

        String selectEmail = "select inchargeEmail from course_db where courseName = ?";
        Cursor c = db.rawQuery(selectEmail, new String[]{String.valueOf(course)});
        if(c.getCount() == 1){
            c.moveToFirst();
            list.append(c.getString(0));
        }
        return String.valueOf(list);
    }
}



