package com.example.application.newsapp;

/**
 * Created by cowboyuniverse on 6/19/17.
 */
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by cowboyuniverse on 6/20/17.
 */

// DONE TODO 4. 5pts: Create a new class called NetworkUtils. Define the appropriate base_url and query_parameter constants (make sure they are Java constants) here as static class members.

// DONE TODO 5. 5pts: Create a static method in NetworkUtils that uses Uri.Builder to build the appropriate url, the url you used in (2), to return a completed Java URL.
// DONE TODO 6. 2pts: Put this method in your NetworkUtils class:

    public class NetworkUtils {
        public static final String APIKEY="97635e7f96974c1b95b2f7a11c1b792b";
        public static final String SOURCE ="source=the-next-web";
        public static final String SORTBY = "latest";
        public static final String NEWSAPI_BASE_URL = "https://newsapi.org/v1/articles?"+ SOURCE + "&sortBy=" + SORTBY + "&apiKey=" + APIKEY;
        public static final String PARAM_QUERY = "q";

        public static URL makeURL(String searchQuery) {
            Uri uri = Uri.parse(NEWSAPI_BASE_URL).buildUpon()
                    .appendQueryParameter(PARAM_QUERY, searchQuery)
                    .build();
            URL url = null;
            try {
                String urlString = uri.toString();
                Log.d(TAG, "url:" + urlString);
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

        public static String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }
    }
