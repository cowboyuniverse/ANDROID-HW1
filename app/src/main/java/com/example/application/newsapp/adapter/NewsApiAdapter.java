package com.example.application.newsapp.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.application.newsapp.model.NewsItem;
import com.example.application.newsapp.NetworkUtils;
import com.example.application.newsapp.json.Json;
import com.example.application.newsapp.R;

import java.util.ArrayList;
/**
 * Created by cowboyuniverse on 6/28/17.
 */
// HW2 DONE TODO 4. 6pts: Implement the RecyclerView's adapter, along with the Holder as an inner class. Set up the RecyclerView so that it displays, in each item, the item's title, description, and date (it doesn't have to be formatted, just put it in raw).




public class NewsApiAdapter extends RecyclerView.Adapter<NewsApiAdapter.ItemHolder>{
    private ArrayList<NewsItem> data;
    ItemClickListener listener;

    public NewsApiAdapter(){

    }
    public NewsApiAdapter(ArrayList<NewsItem> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }
//    private String author;
//    private String title;
//    private String description;
//    private String url;
//    private String urlToImage;
//    private String publishedAt;

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView author;
        TextView title;
        TextView description;
        TextView url;
//        ImageView urlToImage;
//        TextView urlToImage;
        TextView publishedAt;

        ItemHolder(View view){
            super(view);
            author = (TextView)view.findViewById(R.id.author);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            url = (TextView)view.findViewById(R.id.url);
//            urlToImage = (ImageView)view.findViewById(R.id.urlToImage);
            publishedAt = (TextView)view.findViewById(R.id.publishedAt);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            NewsItem items = data.get(pos);
            author.setText(items.getAuthor());
            title.setText(items.getTitle());
            description.setText(items.getDescription());
            url.setText(items.getUrl());
//            urlToImage.setText(items.getUrlToImage());
            publishedAt.setText(items.getPublishedAt());
        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }

}
