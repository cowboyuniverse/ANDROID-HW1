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
//DONE HW1  TODO 9. 4pts: Implement a search menu item. Make the item always appear in the toolbar.

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

//DONE HW1  TODO 8. 10pts: Extend and implement a subclass of AsyncTask to handle the http request. Display the results in a textview.

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

// DONE HW1 TODO 1. 2pts: Visit this site and look around a bit: https://newsapi.org/. Sign up and get your free api key.

// DONE HW1 TODO 2. 2pts: Past this url: https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=   with your api key added to the end in a browser. Cut and paste the result into a text file and submit here. Do this before you demo your app.

// DONE HW1 TODO 3. 1pt: In Android Studio, create a new project called "News App" with a blank activity.

//DONE HW1 TODO 10. EC 2pts: Put spinning progress bars that turn on when the task is running and off when it's finished.




// HW2 TODO 1. 2pts: Create a NewsItem model class to store information about each news story. You need to include fields (make them of type String) for all of the information in each item (see JSON for what those items are (they will include the article's title and description, and url, and other things), you need to figure out what the information is from the JSON).

// HW2 TODO 2. 4pts: Create a method in some sort of utility class (NetworUtils is fine, or make another one if you want) that will parse the JSON you received into an ArrayList<NewsItem>.

// HW2 TODO 3. 4pts: Add a RecyclerView to your activity's layout. Add a new layout for your list item. It should have a LinearLayout with three child TextViews, one for the news item title, one for the description, and one for the time.

// HW2 TODO 4. 6pts: Implement the RecyclerView's adapter, along with the Holder as an inner class. Set up the RecyclerView so that it displays, in each item, the item's title, description, and date (it doesn't have to be formatted, just put it in raw).

// HW2 TODO 5. 6pts: Implement click listeners for the RecyclerView's items so that, when clicked, a browser is opened to the url for that news item (it should be stored in the relevant NewsItem object in your ArrayList).

