package com.example.application.newsapp.database;

/**
 * Created by cowboyuniverse on 7/26/17.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.application.newsapp.model.NewsItem;

import java.util.ArrayList;

import static com.example.application.newsapp.database.Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR;
import static com.example.application.newsapp.database.Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION;
import static com.example.application.newsapp.database.Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT;
import static com.example.application.newsapp.database.Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE;
import static com.example.application.newsapp.database.Contract.TABLE_ARTICLES.COLUMN_NAME_URL;
import static com.example.application.newsapp.database.Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE;
import static com.example.application.newsapp.database.Contract.TABLE_ARTICLES.TABLE_NAME;


//     HW4 TODO     6) 3pts: Have your activity check if the app has been installed before, if not, load data into your database using your network methods.
//
public class DatabaseUtilities {
        //Gets a query of sql and retrieves all in a Descending order
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_AT + " DESC"
        );
        return cursor;
    }

    //update data within the database table when you click refresh
    public static void bulkInsert(SQLiteDatabase database, ArrayList<NewsItem> newsItems) {
        database.beginTransaction();
        try {
            deleteAll(database);
            for (NewsItem item : newsItems) {
                ContentValues content = new ContentValues();
                content.put(COLUMN_NAME_AUTHOR, item.getAuthor());
                content.put(COLUMN_NAME_TITLE, item.getTitle());
                content.put(COLUMN_NAME_DESCRIPTION, item.getDescription());
                content.put(COLUMN_NAME_URL, item.getUrl());
                content.put(COLUMN_NAME_URL_TO_IMAGE, item.getUrlToImage());
                content.put(COLUMN_NAME_PUBLISHED_AT, item.getPublishedAt());
                database.insert(TABLE_NAME, null, content);
            }
            database.setTransactionSuccessful();
        } finally {

            database.endTransaction();
            database.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {

        db.delete(TABLE_NAME, null, null);
    }

}

