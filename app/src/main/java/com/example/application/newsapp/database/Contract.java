package com.example.application.newsapp.database;

import android.provider.BaseColumns;

/**
 * Created by cowboyuniverse on 7/25/17.
 */

//     This is the sql table representation formed as an object class within java
//     HW4 DONE TODO 3) 10pts: Create a contract, subclass SQLiteOpenHelper, modify your app so that your network call stores the data for your news stories in the database (you decide column and table names).

public class Contract {

    public static class TABLE_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_NAME_PUBLISHED_AT = "publishedAt";
    }
}

//DATA FROM THE API AS A REFERENCE

//        "author": "Bryan Clark",
//        "title": "Drone fight club pits two quadcopters against one another for aerial supremacy",
//        "description": "Two drones go in, one comes out. This is the mantra for a new underground sport known as drone dueling. In it, quadcopters of all shapes and sizes take to the skies to duke it out until ...",
//        "url": "https://newsItemsthenextweb.com/gadgets/2017/06/29/drone-fight-club-pits-two-quadcopters-against-one-another-for-aerial-supremacy/",
//        "urlToImage": "https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/Screen-Shot-2017-06-28-at-5.39.26-PM.jpg",
//        "publishedAt": "2017-06-29T02:51:29Z"