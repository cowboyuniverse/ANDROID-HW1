package com.example.application.newsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cowboyuniverse on 7/25/17.
 */


// HW4 DONE TODO 3) 10pts: Create a contract, subclass SQLiteOpenHelper, modify your app so that your network call stores the data for your news stories in the database (you decide column and table names).

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "articles.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //using the skeleton sql table representation of the java object to create
    // and put data in the mysql table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME + " ("+
                Contract.TABLE_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT + " DATE " +
                "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + Contract.TABLE_ARTICLES.TABLE_NAME + " if exists;");
    }
}

