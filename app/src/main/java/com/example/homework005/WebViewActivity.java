/*
   Homework 05: News APP
   Group #: 28
   Saloni Gupta 801080992
   Renju Hanna Robin 801076715
*/
package com.example.homework005;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private WebView webViewNewsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setTitle(ApplicationConstants.WEB_ACTIVITY_APPLICATION_TITLE);
        String urlNews = (String)getIntent().getExtras().get("NewsUrl");
        webViewNewsUrl = findViewById(R.id.webViewNewsUrl);

        if(isConnected()) {
            webViewNewsUrl.loadUrl(urlNews);
        } else {
            Toast.makeText(this, ApplicationConstants.INTERNET_FAILED_MESSAGE,
                    Toast.LENGTH_SHORT).show();
        }

    }

    private  boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null ||
                !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI &&
                        networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}
