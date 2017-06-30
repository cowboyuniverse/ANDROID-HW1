package com.example.application.newsapp.model;

/**
 * Created by cowboyuniverse on 6/28/17.
 */

// HW2 DONE TODO 1. 2pts: Create a NewsItem model class to store information about each news story. You need to include fields (make them of type String) for all of the information in each item (see JSON for what those items are (they will include the article's title and description, and url, and other things), you need to figure out what the information is from the JSON).


public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public NewsItem(String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}


//        "author": "Bryan Clark",
//        "title": "Drone fight club pits two quadcopters against one another for aerial supremacy",
//        "description": "Two drones go in, one comes out. This is the mantra for a new underground sport known as drone dueling. In it, quadcopters of all shapes and sizes take to the skies to duke it out until ...",
//        "url": "https://thenextweb.com/gadgets/2017/06/29/drone-fight-club-pits-two-quadcopters-against-one-another-for-aerial-supremacy/",
//        "urlToImage": "https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/Screen-Shot-2017-06-28-at-5.39.26-PM.jpg",
//        "publishedAt": "2017-06-29T02:51:29Z"