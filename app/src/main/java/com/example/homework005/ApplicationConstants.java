/*
   Homework 05: News APP
   Group #: 28
   Saloni Gupta 801080992
   Renju Hanna Robin 801076715
*/
package com.example.homework005;

public class ApplicationConstants {

    public static final String AUTHOR_NOT_FOUND_MESSAGE = "Author Not Found";
    public static final String TITLE_NOT_FOUND_MESSAGE = "Title Not Found";
    public static final String URL_NOT_FOUND_MESSAGE = "Url Not Found";
    public static final String PUBLISHED_DATE_TIME_NOT_FOUND_MESSAGE = "Time and Date  Not Found";
    public static final String URL_ENCODING = "UTF8";
    public static final String INTERNET_FAILED_MESSAGE = "Internet is not connected!!";
    //Main Activity Constants:
    public static final String MAIN_ACTIVITY_LOGGER_TAG = "Main";
    public static final String MAIN_ACTIVITY_APPLICATION_TITLE = "Main Activity";
    public static final String MAIN_ACTIVITY_PROGRESS_DIALOG_MESSAGE = "Loading Sources...";
    public static final int MAIN_ACTIVITY_PROGRESS_DIALOG_START_VALUE = 0;
    public static final String MAIN_ACTIVITY_API = "https://newsapi.org/v2/sources?apiKey=3669e86c04b14353a7f39ac01f21c633";
    public static final String MAIN_ACTIVITY_JSON_ROOT = "sources";
    public static final String JSON_ROOT_EMPTY = "No News Found";
    public static final String MAIN_ACTIVITY_JSON_NAME = "name";
    public static final String MAIN_ACTIVITY_JSON_ID = "id";
    public static final String MAIN_ACTIVITY_INTENT_KEY = "NewsSource";
    //NewsActivity Constants:
    public static final String NEWS_ACTIVITY_LOGGER_TAG = "NewsA";
    public static final String NEWS_ACTIVITY_INTENT_KEY = "NewsUrl";
    public static final String NEWS_ACTIVITY_JSON_ROOT = "articles";
    public static final String NEWS_ACTIVITY_AUTHOR = "author";
    public static final String NEWS_ACTIVITY_TITLE = "title";
    public static final String NEWS_ACTIVITY_URL = "url";
    public static final String NEWS_ACTIVITY_URL_TO_IMAGE = "urlToImage";
    public static final String NEWS_ACTIVITY_PUBLISHED_DATE_TIME = "publishedAt";
    //WebActivity Constants:
    public static final String WEB_ACTIVITY_APPLICATION_TITLE = "Web View Activity";
    public static final String WEB_ACTIVITY_EMPTY_NEWS_URL = "No URL for the News Found";



}
