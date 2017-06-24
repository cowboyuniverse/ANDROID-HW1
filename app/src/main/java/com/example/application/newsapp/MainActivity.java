package com.example.application.newsapp;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";
    private ProgressBar progress;
    private EditText search;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        search = (EditText) findViewById(R.id.searchQuery);
        textView = (TextView)findViewById(R.id.displayJSON);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
// DONE TODO 9. 4pts: Implement a search menu item. Make the item always appear in the toolbar.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if (itemNumber == R.id.search) {
            String s = search.getText().toString();
            NetworkTask task = new NetworkTask(s);
            task.execute();
        }

        return true;
    }

//DONE TODO 8. 10pts: Extend and implement a subclass of AsyncTask to handle the http request. Display the results in a textview.

    class NetworkTask extends AsyncTask<URL, Void, String> {
        String query;

        NetworkTask(String s){
            query = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... params) {
            String result = null;
            URL url = NetworkUtils.makeURL(query);
            Log.d(TAG, "url:" + url.toString());
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(View.GONE);
            if(s == null){
                textView.setText("Sorry, no text was received");
            }else{
                textView.setText(s);
            }
        }
    }
}


// DONE TODO 1. 2pts: Visit this site and look around a bit: https://newsapi.org/. Sign up and get your free api key.

// DONE TODO 2. 2pts: Past this url: https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=   with your api key added to the end in a browser. Cut and paste the result into a text file and submit here. Do this before you demo your app.

// DONE TODO 3. 1pt: In Android Studio, create a new project called "News App" with a blank activity.

//DONE TODO 10. EC 2pts: Put spinning progress bars that turn on when the task is running and off when it's finished.

