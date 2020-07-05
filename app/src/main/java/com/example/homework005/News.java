/*
   Homework 05: News APP
   Group #: 28
   Saloni Gupta 801080992
   Renju Hanna Robin 801076715
*/
package com.example.homework005;

public class News {
    private String author;
    private String title;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public News(String author, String title, String url, String urlToImage, String publishedAt) {
        this.author = author.equals("null")  ?  ApplicationConstants.AUTHOR_NOT_FOUND_MESSAGE: author;
        this.title = title.equals("null") ? ApplicationConstants.TITLE_NOT_FOUND_MESSAGE : title;
        this.url = url.equals("null") ? ApplicationConstants.URL_NOT_FOUND_MESSAGE : url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt.equals("null") ? ApplicationConstants.PUBLISHED_DATE_TIME_NOT_FOUND_MESSAGE : publishedAt;
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
