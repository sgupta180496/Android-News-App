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
import android.widget.ArrayAdapter;
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

public class MainActivity extends AppCompatActivity {

    ListView listViewNews;
    ArrayList<Source> sourceObjArray = new ArrayList<>();
    private ProgressDialog myPd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(ApplicationConstants.MAIN_ACTIVITY_APPLICATION_TITLE);
        myPd = new ProgressDialog(MainActivity.this);
        myPd.setMessage(ApplicationConstants.MAIN_ACTIVITY_PROGRESS_DIALOG_MESSAGE);
        myPd.setProgress(ApplicationConstants.MAIN_ACTIVITY_PROGRESS_DIALOG_START_VALUE);

        if (isConnected()) {
            myPd.show();
            new GetDataParamsAsync().execute(ApplicationConstants.MAIN_ACTIVITY_API);
        } else {
            Toast.makeText(this, ApplicationConstants.INTERNET_FAILED_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        listViewNews = findViewById(R.id.listViewNews);
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = (NetworkInfo) connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null ||
                !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI &&
                        networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetDataParamsAsync extends AsyncTask<String, Void, ArrayList<Source>> {

        ArrayList<Source> resultList = new ArrayList<>();

        @Override
        protected void onPostExecute(ArrayList<Source> result) {
            final ArrayList<Source> newsSourcesList = result;
            if (result != null) {
                ArrayAdapter<Source> adapter = new ArrayAdapter<>
                        (MainActivity.this, android.R.layout.simple_list_item_1,
                                android.R.id.text1,
                                result);
                listViewNews.setAdapter(adapter);
                listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Log.v(ApplicationConstants.MAIN_ACTIVITY_LOGGER_TAG, "Item clicked = " + position + " with id = " + id);
                        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                        intent.putExtra(ApplicationConstants.MAIN_ACTIVITY_INTENT_KEY, newsSourcesList.get(position));
                        Log.v(ApplicationConstants.MAIN_ACTIVITY_LOGGER_TAG, "Item = ");
                        startActivity(intent);
                    }
                });
                myPd.dismiss();
            } else {
                Log.v(ApplicationConstants.MAIN_ACTIVITY_LOGGER_TAG, " onPostExecute --> No resultList = ");
            }
        }

        @Override
        protected ArrayList<Source> doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strings[0]);
                Log.v(ApplicationConstants.MAIN_ACTIVITY_LOGGER_TAG, "URL = " + url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                Log.v(ApplicationConstants.MAIN_ACTIVITY_LOGGER_TAG, "Response Code = " + urlConnection.getResponseCode());
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String jsonString = IOUtils.toString(urlConnection.getInputStream(), ApplicationConstants.URL_ENCODING);
                    JSONObject root = new JSONObject(jsonString);
                    JSONArray jsonArr = root.getJSONArray(ApplicationConstants.MAIN_ACTIVITY_JSON_ROOT);
                    if (jsonArr.length() == 0) {
                        Toast.makeText(MainActivity.this, ApplicationConstants.JSON_ROOT_EMPTY, Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject sourceJSON = jsonArr.getJSONObject(i);
                            Source sourceObject = new Source(sourceJSON.getString(ApplicationConstants.MAIN_ACTIVITY_JSON_NAME), sourceJSON.getString(ApplicationConstants.MAIN_ACTIVITY_JSON_ID));
                            sourceObject.setName(sourceJSON.getString(ApplicationConstants.MAIN_ACTIVITY_JSON_NAME));
                            sourceObject.setId(sourceJSON.getString(ApplicationConstants.MAIN_ACTIVITY_JSON_ID));
                            resultList.add(sourceObject);
                            sourceObjArray.add(sourceObject);
                        }

                        for (int i = 0; i < 1000; i++) {
                            for (int j = 0; j < 1000000; j++) {
                            }
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return resultList;
        }
    }
}
