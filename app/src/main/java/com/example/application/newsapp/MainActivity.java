package com.example.application.newsapp;
import com.example.application.newsapp.adapter.NewsApiAdapter;
import com.example.application.newsapp.json.Json;
import com.example.application.newsapp.model.NewsItem;
import com.example.application.newsapp.NetworkUtils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;



import java.util.ArrayList;

import static com.example.application.newsapp.R.id.refresh;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";
    private TextView refresh_s;
    private ImageView urlToImage;
    private ProgressBar progress;
    private TextView error;
    private EditText refresh_search;
    private NewsApiAdapter mNewsApiAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsApiAdapter = new NewsApiAdapter();
//        textView = (TextView)findViewById(R.id.displayJSON);
//        mRecyclerView.setAdapter(mNewsApiAdapter);
//        ImageView urlToImage = (ImageView) findViewById(R.id.urlToImage);
        refresh_search = (EditText) findViewById(R.id.refresh_search);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        error = (TextView) findViewById(R.id.error);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
//don't forget refresh is inside menu.xml
//refresh_search is editText
        if (itemNumber == R.id.refresh) {
            String s = refresh_search.getText().toString();
            NetworkTask task = new NetworkTask(s);
            task.execute();
        }

        return true;
    }


//DONE HW1  TODO 8. 10pts: Extend and implement a subclass of AsyncTask to handle the http request. Display the results in a textview.

    public class NetworkTask extends AsyncTask<URL, Void,ArrayList<NewsItem>> {
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
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> result = null;
            URL url = NetworkUtils.makeURL(query);
            Log.d(TAG, "url:" + url.toString() + "\n\n");
            String json;
            try {
                json = NetworkUtils.getResponseFromHttpUrl(url);
                result = Json.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            progress.setVisibility(View.INVISIBLE);
            error.setVisibility(View.INVISIBLE);
//            urlToImage.setVisibility(View.VISIBLE);
            if (data != null) {
                mNewsApiAdapter = new NewsApiAdapter(data, new NewsApiAdapter.ItemClickListener() {


                    @Override
                    public void onItemClick(int clickedItemIndex) {
                        String url = data.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                        openWebPage(url);
                    }
                });
                mRecyclerView.setAdapter(mNewsApiAdapter);

            }
            else {
                error.setVisibility(View.VISIBLE);
            }
        }

    }
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}



//----------------------------------------------------------------------------------------------------------------------------------------
//     HW4 DONE TODO     1) 0pts: Finish Homework 2.
//
//     HW4 TODO     2) 10pts: Modify Homework 2 so that AsyncTask is replaced with AsyncTaskLoader, implementing all the required callbacks.
//
//     HW4 TODO     3) 10pts: Create a contract, subclass SQLiteOpenHelper, modify your app so that your network call stores the data for your news stories in the database (you decide column and table names).
//
//     HW4 TODO     4) 5pts: Modify your app so that the recyclerview loads from the database.
//
//     HW4 TODO     5) 10pts: Using Firebase's JobDispatcher, modify your app so that it loads new news information every minute.
//
//     HW4 TODO     6) 3pts: Have your activity check if the app has been installed before, if not, load data into your database using your network methods.
//
//     HW4 TODO     7) 2pts: In onCreate, have your activity load what's currently in your database into the recyclerview for display.
//
//     HW4 TODO      8) 5pts: Use Picasso to load a thumbnail for each news item in the recycler view.
//
//     HW4 TODO      For each major addition, comment about what you are doing in the appropriate place in your code. If you fail to do this, you may lose all your points.



//------------------------------------------------------------------------------------------------------------------------------------------------------

// DONE HW1 TODO 1. 2pts: Visit this site and look around a bit: https://newsapi.org/. Sign up and get your free api key.

// DONE HW1 TODO 2. 2pts: Past this url: https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=   with your api key added to the end in a browser. Cut and paste the result into a text file and submit here. Do this before you demo your app.

// DONE HW1 TODO 3. 1pt: In Android Studio, create a new project called "News App" with a blank activity.

// DONE HW1 TODO 10. EC 2pts: Put spinning progress bars that turn on when the task is running and off when it's finished.

// DONE HW2 TODO 5. 6pts: Implement click listeners for the RecyclerView's items so that, when clicked, a browser is opened to the url for that news item (it should be stored in the relevant NewsItem object in your ArrayList).

