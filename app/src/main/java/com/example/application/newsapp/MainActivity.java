package com.example.application.newsapp;
import com.example.application.newsapp.Utilities.ReloadNewsItems;
import com.example.application.newsapp.Utilities.ScheduleNewsJob;
import com.example.application.newsapp.adapter.NewsApiAdapter;
import com.example.application.newsapp.database.Contract;
import com.example.application.newsapp.database.DBHelper;
import com.example.application.newsapp.database.DatabaseUtilities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.widget.ProgressBar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


//added load manager
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsApiAdapter.ItemClickListener  {
    static final String TAG = "mainactivity";
//    private TextView refresh_s;
//    private ImageView urlToImage;
//    private TextView error;
    private EditText refresh_search;
    private RecyclerView rv;
    private ProgressBar progress;

    // Added fields to store cursor, adapter and database;
    private NewsApiAdapter adapter;
    private SQLiteDatabase db;
    private Cursor cursor;
    private static final int NEWS_LOADER = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, String.format("Url %s", savedInstanceState));
        setContentView(R.layout.activity_main);

//        textView = (TextView)findViewById(R.id.displayJSON);
//        rv.setAdapter(mNewsApiAdapter);
//        ImageView urlToImage = (ImageView) findViewById(R.id.urlToImage);
//        error = (TextView) findViewById(R.id.error);
//        refresh_search = (EditText) findViewById(R.id.refresh_search);
        progress = (ProgressBar) findViewById(R.id.progressBar);


//      Activity is now loading whats currently in the database
//     HW4 DONE TODO     7) 2pts: In onCreate, have your activity load what's currently in your database into the recyclerview for display.
        rv = (RecyclerView)findViewById(R.id.recyclerView);
//        rv.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        progress = (ProgressBar) findViewById(R.id.progressBar);



        //ADDED load data into the database when the app starts, and checks if app has previously been installed previously
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleNewsJob.scheduleRefresh(this);
    }


    //ADDED data loader into the db
    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

    }

    //  ADDED  cursor to update in the NewsApiAdapter inteface and sttaring the intent
    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


//     HW4 TODO     2) 10pts:  implementing all the required callbacks.
//      https://developer.android.com/reference/android/content/AsyncTaskLoader.html
//     This is called when a new Loader needs to be created
//     ADDED AsyncTaskLoader it's callbacks

    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                ReloadNewsItems.refreshNewsItems(MainActivity.this);
                return null;
            }

        };
    }


//  This does a request to and the Loader starts.
//     HW4 TODO     4) 5pts: Modify your app so that the recyclerview loads from the database.
    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtilities.getAll(db);
        adapter = new NewsApiAdapter(cursor, this);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    // ADED setData is from the api example of asyncloader
    @Override
    public void onLoaderReset(Loader<Void> loader) {
        // Clear the data in the adapter.
        adapter.setData(null);
    }


    // setting new data in the adapter and getting data for the cursor
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtilities.getAll(db);

        adapter = new NewsApiAdapter(cursor, this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

// ADDED
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

////DONE HW1  TODO 9. 4pts: Implement a search menu item. Make the item always appear in the toolbar.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

//don't forget refresh is inside menu.xml
//refresh_search is editText
        if (itemNumber == R.id.refresh) {
            load();
//            String s = refresh_search.getText().toString();
//            NetworkTask task = new NetworkTask(s);
//            task.execute();
        }

        return true;
    }


////DONE HW1  TODO 8. 10pts: Extend and implement a subclass of AsyncTask to handle the http request. Display the results in a textview.
//    public class NetworkTask extends AsyncTask<URL, Void,ArrayList<NewsItem>> {
//        String query;
//        NetworkTask(String s){
//            query = s;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress.setVisibility(View.VISIBLE);
//        }
//        @Override
//        protected ArrayList<NewsItem> doInBackground(URL... params) {
//            ArrayList<NewsItem> result = null;
////            URL url = NetworkUtils.makeURL(query);
//            URL url = NetworkUtils.makeURL();
//            Log.d(TAG, "url:" + url.toString() + "\n\n");
//            String json;
//            try {
//                json = NetworkUtils.getResponseFromHttpUrl(url);
//                result = Json.parseJSON(json);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//        @Override
//        protected void onPostExecute(final ArrayList<NewsItem> data) {
//            super.onPostExecute(data);
//            progress.setVisibility(View.INVISIBLE);
//            error.setVisibility(View.INVISIBLE);
////            urlToImage.setVisibility(View.VISIBLE);
//            if (data != null) {
//                mNewsApiAdapter = new NewsApiAdapter(data, new NewsApiAdapter.ItemClickListener() {
//
//                        //added cursor
//                    @Override
//                    public void onItemClick(Cursor cursor, int clickedItemIndex) {
//                        cursor.moveToPosition(clickedItemIndex);
////                        String url = data.get(clickedItemIndex).getUrl();
//                        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
//                        Log.d(TAG, String.format("Url %s", url));
//                        openWebPage(url);
//                    }
//                });
//                rv.setAdapter(mNewsApiAdapter);
//            }
//            else {
//                error.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//    public void openWebPage(String url) {
//        Uri webpage = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//        if (intent.resolveActivity(getPackageManager()) != null) {
////            intent.setData(Uri.parse(url));
//            startActivity(intent);
//        }
//    }
}

//----------------------------------------------------------------------------------------------------------------------------------------
//     HW4 DONE TODO     1) 0pts: Finish Homework 2.
//     HW4 DONE TODO     2) 10pts: Modify Homework 2 so that AsyncTask is replaced with AsyncTaskLoader, implementing all the required callbacks.
//     HW4 DONE TODO     3) 10pts: Create a contract, subclass SQLiteOpenHelper, modify your app so that your network call stores the data for your news stories in the database (you decide column and table names).
//     HW4 DONE TODO     4) 5pts: Modify your app so that the recyclerview loads from the database.
//     HW4 DONE TODO     5) 10pts: Using Firebase's JobDispatcher, modify your app so that it loads new news information every minute.
//     HW4 DONE TODO     6) 3pts: Have your activity check if the app has been installed before, if not, load data into your database using your network methods.
//     HW4 DONE  TODO     7) 2pts: In onCreate, have your activity load what's currently in your database into the recyclerview for display.
//     HW4 DONE  TODO     8) 5pts: Use Picasso to load a thumbnail for each news item in the recycler view.
//     HW4 DONE  TODO      For each major addition, comment about what you are doing in the appropriate place in your code. If you fail to do this, you may lose all your points.



//------------------------------------------------------------------------------------------------------------------------------------------------------
// DONE HW1 TODO 1. 2pts: Visit this site and look around a bit:c https://newsapi.org/. Sign up and get your free api key.
// DONE HW1 TODO 2. 2pts: Past this url: https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=   with your api key added to the end in a browser. Cut and paste the result into a text file and submit here. Do this before you demo your app.
// DONE HW1 TODO 3. 1pt: In Android Studio, create a new project called "News App" with a blank activity.
// DONE HW1 TODO 10. EC 2pts: Put spinning progress bars that turn on when the task is running and off when it's finished.
// DONE HW2 TODO 5. 6pts: Implement click listeners for the RecyclerView's items so that, when clicked, a browser is opened to the url for that news item (it should be stored in the relevant NewsItem object in your ArrayList).

