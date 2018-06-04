package com.example.studentsstutsmanagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase StudentsDatabase;
    EditText et_insert_id;
    EditText et_insert_name;
    EditText et_insert_sex;
    EditText et_insert_birthday;

    EditText et_query_id;
    EditText et_query_name;

    EditText et_record_sno;
    EditText et_record_cno;
    EditText et_record_grade;

    EditText et_query_grade_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteHelper helper = new SQLiteHelper(this,"StudentStatusDatabase",null,1);
        StudentsDatabase = helper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("name","数据库原理");
        values.put("cno","zxc321");
        values.put("teacher","李明");
        values.put("kind","BX");
        values.put("credit","11");
        StudentsDatabase.insert("Class",null,values);

        values.put("name","信号与系统");
        values.put("cno","cxz123");
        values.put("teacher","王亮");
        values.put("kind","BX");
        values.put("credit","9");
        StudentsDatabase.insert("Class",null,values);

        values.put("name","微机与系统原理");
        values.put("cno","cxz123");
        values.put("teacher","王暗");
        values.put("kind","XX");
        values.put("credit","19");
        StudentsDatabase.insert("Class",null,values);

        values.put("name","操作系统");
        values.put("cno","zxc123");
        values.put("teacher","李暗");
        values.put("kind","XX");
        values.put("credit","21");
        StudentsDatabase.insert("Class",null,values);

        insert();
        query();
        record();
        query_grade_teacher();
        query_dropping_out_student();

    }

    protected void insert(){
        et_insert_id = (EditText)findViewById(R.id.et_insert_id);
        et_insert_name = (EditText)findViewById(R.id.et_insert_name);
        et_insert_sex = (EditText)findViewById(R.id.et_insert_sex);
        et_insert_birthday = (EditText)findViewById(R.id.et_insert_birthday);
        Button btn_insert = (Button)findViewById(R.id.btn_insert);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("sno",et_insert_id.getText().toString());
                values.put("name",et_insert_name.getText().toString());
                values.put("sex",et_insert_sex.getText().toString());
                values.put("birthday",et_insert_birthday.getText().toString());
                StudentsDatabase.insert("Student",null,values);
                Toast.makeText(MainActivity.this, "录入成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected void query(){
        Button btn_query_id = findViewById(R.id.btn_query_id);
        et_query_id = findViewById(R.id.et_query_id);

        btn_query_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = StudentsDatabase.query("Student",null,"sno=?",new String[]{et_query_id.getText().toString()},null,null,null);
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String id = cursor.getString(cursor.getColumnIndex("sno"));
                    String sex = cursor.getString(cursor.getColumnIndex("sex"));
                    int birthday = cursor.getInt(cursor.getColumnIndex("birthday"));

                    new AlertDialog.Builder(MainActivity.this)
                                .setTitle("查询结果")
                                .setMessage(id+" "+name+" "+sex+" "+birthday)
                                .setPositiveButton("确定",null)
                                .show();

                }
            }
        });


        Button btn_query_name = findViewById(R.id.btn_query_name);
        et_query_name = findViewById(R.id.et_query_name);

        btn_query_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = StudentsDatabase.query("Student",null,"name=?",new String[]{et_query_name.getText().toString()},null,null,null);
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String id = cursor.getString(cursor.getColumnIndex("sno"));
                    String sex = cursor.getString(cursor.getColumnIndex("sex"));
                    int birthday = cursor.getInt(cursor.getColumnIndex("birthday"));
                    newAlertDialog(id+" "+name+" "+sex+" "+birthday);
                }
            }
        });
    }
    protected void record(){
        et_record_sno = findViewById(R.id.et_record_id);
        et_record_cno = findViewById(R.id.et_record_class);
        et_record_grade = findViewById(R.id.et_record_grade);
        Button btn_record = findViewById(R.id.btn_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("sno",et_record_sno.getText().toString());
                values.put("cno",et_record_cno.getText().toString());
                values.put("grade",et_record_grade.getText().toString());
                StudentsDatabase.insert("Grade",null,values);
                Toast.makeText(MainActivity.this, "录入成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void query_grade_teacher(){
        et_query_grade_id = findViewById(R.id.et_query_grade_id);
        Button btn_query_grade = findViewById(R.id.btn_query_grade);
        Button btn_query_grade_BX = findViewById(R.id.btn_query_grade_BX);
        Button btn_query_grade_XX = findViewById(R.id.btn_query_grade_XX);
        Button btn_query_teacher = findViewById(R.id.btn_query_teacher);

        btn_query_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text ="";
                Cursor cursor = StudentsDatabase.query("Class,Grade",new String[]{"name,grade"},
                        "Class.cno=Grade.cno and sno=?",new String[]{et_query_grade_id.getText().toString()},null,null,null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int grade = cursor.getInt(cursor.getColumnIndex("grade"));
                    text = text+"\n"+name+" "+grade;
                }
               newAlertDialog(text);
            }
        });

        btn_query_grade_BX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int grade=0;
                int i=0;
                Cursor cursor = StudentsDatabase.query("Class,Grade",new String[]{"grade"},
                        "Class.cno=Grade.cno and sno=? and kind='BX'",new String[]{et_query_grade_id.getText().toString()},null,null,null);
                while (cursor.moveToNext()) {
                    grade = grade + cursor.getInt(cursor.getColumnIndex("grade"));
                    i++;
                }
                newAlertDialog("必修课平均成绩:"+grade/i);
            }
        });

        btn_query_grade_XX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int grade=0;
                int i=0;
                Cursor cursor= StudentsDatabase.query("Class,Grade",new String[]{"grade"},
                        "Class.cno=Grade.cno and sno=? and kind='XX'",new String[]{et_query_grade_id.getText().toString()},null,null,null);
                while (cursor.moveToNext()) {
                    grade = grade + cursor.getInt(cursor.getColumnIndex("grade"));
                    i++;
                }
                newAlertDialog("选修课平均成绩:"+grade/i);
            }
        });

        btn_query_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teacher ="";
                Cursor cursor= StudentsDatabase.query("Class,Grade",new String[]{"teacher"},
                        "Class.cno=Grade.cno and sno=?",new String[]{et_query_grade_id.getText().toString()},null,null,null);
                while (cursor.moveToNext()) {
                    teacher = teacher+"\n"+cursor.getString(cursor.getColumnIndex("teacher"));
                }
               newAlertDialog("该生的老师为:" +teacher);
            }
        });
    }

    protected void query_dropping_out_student(){
       findViewById(R.id.btn_query_dropping_out_student).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int negative_credit_BX = 0;
               int negative_credit_XX = 0;
               String student_BX="";
               String student_XX="";
               String dropping_out_student_BX="";
               String dropping_out_student_XX="";
               Cursor cursor_BX= StudentsDatabase.query("Student,Grade,Class",new String[]{"Student.name,grade,credit"},
                       "Student.sno=Grade.sno and Class.cno = Grade.cno and kind = 'BX'",null,null,null,null);
               while (cursor_BX.moveToNext()) {
                   if (student_BX!=cursor_BX.getString(cursor_BX.getColumnIndex("name"))){
                       if (negative_credit_BX<10){
                           dropping_out_student_BX = dropping_out_student_BX + student_BX;
                       }

                       student_BX = cursor_BX.getString(cursor_BX.getColumnIndex("name"));
                       if (cursor_BX.getInt(cursor_BX.getColumnIndex("grade"))<60)
                       {negative_credit_BX = - cursor_BX.getInt(cursor_BX.getColumnIndex("credit"));}
                       else {negative_credit_BX = 0;}
                   }
                   else {
                       if (cursor_BX.getInt(cursor_BX.getColumnIndex("grade"))<60) {
                           negative_credit_BX = negative_credit_BX - cursor_BX.getInt(cursor_BX.getColumnIndex("credit"));
                       }
                   }
               }
               Cursor cursor_XX= StudentsDatabase.query("Student,Grade,Class",new String[]{"Student.name,grade,credit"},
                       "Student.sno=Grade.sno and Class.cno = Grade.cno and kind = 'XX'",null,null,null,null);
               while (cursor_XX.moveToNext()) {
                   if (student_XX!=cursor_XX.getString(cursor_XX.getColumnIndex("name"))){
                       if (negative_credit_XX<20){
                           dropping_out_student_XX = dropping_out_student_XX + student_XX;
                       }

                       student_XX = cursor_XX.getString(cursor_XX.getColumnIndex("name"));
                       if (cursor_XX.getInt(cursor_XX.getColumnIndex("grade"))<60)
                       {negative_credit_XX = - cursor_XX.getInt(cursor_XX.getColumnIndex("credit"));}
                       else {negative_credit_XX = 0;}
                   }
                   else {
                       if (cursor_XX.getInt(cursor_XX.getColumnIndex("grade"))<60) {
                           negative_credit_XX = negative_credit_XX - cursor_XX.getInt(cursor_XX.getColumnIndex("credit"));
                       }
                   }
               }
               newAlertDialog("即将退学："+dropping_out_student_BX+"\n"+dropping_out_student_XX);
           }
       });
    }


    public void newAlertDialog(String message){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("查询结果")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }
    public void newAlertDialog(int message){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("查询结果")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }


}
