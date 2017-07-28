package com.example.application.newsapp.json;
import com.example.application.newsapp.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by cowboyuniverse on 6/28/17.
 */
// HW2 DONE TODO 2. 4pts: Create a method in some sort of utility class (NetworUtils is fine, or make another one if you want) that will parse the JSON you received into an ArrayList<NewsItem>.

public class Json {
    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException{
        ArrayList<NewsItem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");
//        String source = main.getString("source");

        for(int i = 0; i < articles.length(); i++){
            JSONObject article = articles.getJSONObject(i);
            String author = article.getString("author");
            String tiitle = article.getString("title");
            String description = article.getString("description");
            String url = article.getString("url");
            String urlToImage = article.getString("urlToImage");
            String publishedAt = article.getString("publishedAt");
            NewsItem newsItems = new NewsItem(author, tiitle,description, url, urlToImage, publishedAt);
            result.add(newsItems);
        }
        return result;
    }
}

//DATA FROM THE API AS A REFERENCE

//        "author": "Bryan Clark",
//        "title": "Drone fight club pits two quadcopters against one another for aerial supremacy",
//        "description": "Two drones go in, one comes out. This is the mantra for a new underground sport known as drone dueling. In it, quadcopters of all shapes and sizes take to the skies to duke it out until ...",
//        "url": "https://newsItemsthenextweb.com/gadgets/2017/06/29/drone-fight-club-pits-two-quadcopters-against-one-another-for-aerial-supremacy/",
//        "urlToImage": "https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/Screen-Shot-2017-06-28-at-5.39.26-PM.jpg",
//        "publishedAt": "2017-06-29T02:51:29Z"