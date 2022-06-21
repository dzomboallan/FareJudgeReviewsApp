package com.example.reviewsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "reviews.db";
    public static final String BARTABLE = "bar";
    public static final String CAFETABLE = "cafe";
    public static final String RESTAURANTTABLE = "restaurants";
    public static final String REVIEWSTABLE = "reviews";
    public static final int VER = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+BARTABLE+"(id integer primary key, avatar blob, name text)");
        db.execSQL("create table "+CAFETABLE+"(id integer primary key, avatar blob, name text)");
        db.execSQL("create table "+RESTAURANTTABLE+"(id integer primary key, avatar blob, name text)");
        db.execSQL("create table "+REVIEWSTABLE+"(id integer primary key,name text, type text, food text, location text, remarks text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+BARTABLE+"");
        db.execSQL("drop table if exists "+CAFETABLE+"");
        db.execSQL("drop table if exists "+RESTAURANTTABLE+"");
        db.execSQL("drop table if exists "+REVIEWSTABLE+"");

    }
}
