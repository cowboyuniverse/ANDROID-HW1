package com.example.application.newsapp.Utilities;

/**
 * Created by cowboyuniverse on 7/26/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.application.newsapp.NetworkUtils;
import com.example.application.newsapp.database.DBHelper;
import com.example.application.newsapp.json.Json;
import com.example.application.newsapp.model.NewsItem;
import com.example.application.newsapp.database.DatabaseUtilities;


import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

//     Loads info in the database from the api source

//     HW4 TODO     5) 10pts: Using Firebase's JobDispatcher, modify your app so that it loads new news information every minute.
public class ReloadNewsItems {
    public static final String ACTION_REFRESH = "refresh";
    public static void refreshNewsItems(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.makeURL();
//        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        try {
            DatabaseUtilities.deleteAll(database);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = Json.parseJSON(json);
            DatabaseUtilities.bulkInsert(database, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        database.close();
    }
}
