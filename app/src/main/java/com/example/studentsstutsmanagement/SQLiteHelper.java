package com.example.studentsstutsmanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 陈志强 on 2018/6/4.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String CREATE_STUDENT="create table Student("+
            "sno string primary key,"+
            "name string,"+
            "sex string,"+
            "birthday int)";
    public static final String CREATE_CLASS="create table Class("+
            "cno string primary key,"+
            "name string,"+
            "kind string,"+
            "credit int,"+
            "teacher string)";
    public static final String CREATE_GRADE="create table Grade("+
            "id integer primary key autoincrement,"+
            "sno string,"+
            "cno string,"+
            "grade int)";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STUDENT);
        sqLiteDatabase.execSQL(CREATE_CLASS);
        sqLiteDatabase.execSQL(CREATE_GRADE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
