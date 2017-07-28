package com.example.application.newsapp.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.application.newsapp.database.Contract;
import com.example.application.newsapp.model.NewsItem;
import com.example.application.newsapp.NetworkUtils;
import com.example.application.newsapp.json.Json;
import com.example.application.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.util.Collections.addAll;

/**
 * Created by cowboyuniverse on 6/28/17.
 */
// HW2 DONE TODO 4. 6pts: Implement the RecyclerView's adapter, along with the Holder as an inner class. Set up the RecyclerView so that it displays, in each item, the item's title, description, and date (it doesn't have to be formatted, just put it in raw).



//added cursor
public class NewsApiAdapter extends RecyclerView.Adapter<NewsApiAdapter.ItemHolder>{
    private ArrayList<NewsItem> data;
    private ItemClickListener listener;
    private Cursor cursor;
    private Context context;

    public NewsApiAdapter(){
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }


    public NewsApiAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public NewsApiAdapter(ArrayList<NewsItem> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

//loads data in the database with holder
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;

    }
//    this method is in the api for adapter class
//https://developer.android.com/reference/android/content/AsyncTaskLoader.html
    public void setData(ArrayList<NewsItem> data) {
        if (data != null) {
            addAll(data);
        }
    }


    @Override
    public void onBindViewHolder(
            ItemHolder holder, int position) {
        holder.bind(position);
    }
//    private String author;
//    private String title;
//    private String description;
//    private String url;
//    private String urlToImage;
//    private String publishedAt;

//    @Override
//    public int getItemCount() {
//        return data.size();
//    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView author;
        TextView title;
        TextView description;
        TextView url;
        ImageView urlToImage;
//        TextView urlToImage;
        TextView publishedAt;

        ItemHolder(View view){
            super(view);
            author = (TextView)view.findViewById(R.id.author);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            url = (TextView)view.findViewById(R.id.url);
            urlToImage = (ImageView)view.findViewById(R.id.urlToImage);
            publishedAt = (TextView)view.findViewById(R.id.publishedAt);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
//            NewsItem items = data.get(pos);
//            author.setText(items.getAuthor());
//            title.setText(items.getTitle());
//            description.setText(items.getDescription());
//            url.setText(items.getUrl());
//            publishedAt.setText(items.getPublishedAt());

            cursor.moveToPosition(pos);
            author.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR)));
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            url.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL)));
            publishedAt.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT)));


//     HW4 TODO      8) 5pts: Use Picasso to load a thumbnail for each news item in the recycler view.
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE));
            if(url != null){
                Picasso.with(context)
                        .load(url)
                        .into(urlToImage);
            }
        }
//     HW4 TODO     4) 5pts: Modify your app so that the recyclerview loads from the database.
        //added cursor on itemclick
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);

        }
    }

}
