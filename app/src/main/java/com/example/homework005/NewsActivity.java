/*
   Homework 05: News APP
   Group #: 28
   Saloni Gupta 801080992
   Renju Hanna Robin 801076715
*/
package com.example.homework005;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    private ProgressDialog myPd;
    private String sourceName = "DEFAULT APPLICATION";
    private String sourceId = "DEFAULT ID";
    private ListView listViewNewsAdapter;
    private Source newsSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listViewNewsAdapter = findViewById(R.id.listViewNewsAdapter);
        myPd = new ProgressDialog(NewsActivity.this);
        myPd.setMessage("Loading News");

        if(getIntent() != null && getIntent().getExtras() != null) {
            newsSource = getIntent().getExtras().getParcelable(ApplicationConstants.MAIN_ACTIVITY_INTENT_KEY);
            sourceName = newsSource.getName();
            sourceId = newsSource.getId();
            Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG,sourceName);

        } else {
            Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG,"Intent and Extras are null");
            return;
        }

        setTitle(sourceName);
        if(isConnected()) {
            myPd.show();
            new NewsActivity.GetDataParamsAsync().execute("https://newsapi.org/v2/top-headlines?sources=" + sourceId + "&apiKey=3669e86c04b14353a7f39ac01f21c633");
        } else {
            Toast.makeText(this, ApplicationConstants.INTERNET_FAILED_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private  boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =  connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null ||
                !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI &&
                        networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }


    private class GetDataParamsAsync extends AsyncTask<String, Void, ArrayList<News>> {
        ArrayList<News> resultList = new ArrayList<>();
        ArrayList<News> newsList = new ArrayList<>();

        @Override
        protected void onPostExecute(ArrayList<News> result) {
            final ArrayList<News> newsSourcesList = result;
            Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG,"Result = "+ result);
            if(result != null) {
                NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this,R.layout.news_item,result);
                listViewNewsAdapter.setAdapter(newsAdapter);

                listViewNewsAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG, "Item clicked = " + position + " with id = " + id);
                        Intent intent = new Intent(NewsActivity.this,WebViewActivity.class);
                        if(!newsSourcesList.get(position).getUrl().equals("")) {
                            intent.putExtra(ApplicationConstants.NEWS_ACTIVITY_INTENT_KEY,newsSourcesList.get(position).getUrl());
                            startActivity(intent);
                        } else {
                            Toast.makeText(NewsActivity.this, ApplicationConstants.WEB_ACTIVITY_EMPTY_NEWS_URL, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                if(myPd.isShowing()) {
                    myPd.dismiss();
                }
            } else {
                Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG, " onPostExecute --> No resultList = " );
            }
        }

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            try {
                Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG, "Im hereee before connection");
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();
                Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG, "Im hereee");
                Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG , "Response Code = " + urlConnection.getResponseCode());
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String jsonString = IOUtils.toString(urlConnection.getInputStream(),ApplicationConstants.URL_ENCODING);
                    Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG,jsonString);
                    JSONObject root = new JSONObject(jsonString);
                    JSONArray jsonArr = root.getJSONArray(ApplicationConstants.NEWS_ACTIVITY_JSON_ROOT);
                    if(jsonArr.length() == 0) {
                        Toast.makeText(NewsActivity.this, ApplicationConstants.JSON_ROOT_EMPTY, Toast.LENGTH_SHORT).show();
                    } else {

                        for(int i = 0; i < jsonArr.length(); i++) {
                            JSONObject newsJSON = jsonArr.getJSONObject(i);
                            News news = new News(newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_AUTHOR),
                                    newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_TITLE),
                                    newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_URL),
                                    newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_URL_TO_IMAGE),
                                    newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_PUBLISHED_DATE_TIME)
                                    );

                            Log.v(ApplicationConstants.NEWS_ACTIVITY_LOGGER_TAG, "Author = " + news.getAuthor());
                            news.setAuthor(news.getAuthor());
                            //news.setTitle(newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_TITLE));
                            //news.setUrl(newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_URL));
                            //news.setUrlToImage(newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_URL_TO_IMAGE));
                            //news.setPublishedAt(newsJSON.getString(ApplicationConstants.NEWS_ACTIVITY_PUBLISHED_DATE_TIME));
                            resultList.add(news);
                            newsList.add(news);
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return resultList;
        }
    }
}
