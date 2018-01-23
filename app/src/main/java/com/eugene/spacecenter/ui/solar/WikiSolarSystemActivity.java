package com.eugene.spacecenter.ui.solar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.eugene.spacecenter.R;

public class WikiSolarSystemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_solar_system);

//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        WebView webView=(WebView)findViewById(R.id.web_view);

        String urlWiki = getIntent().getStringExtra("url");

        if (urlWiki!=null) {
            webView.loadUrl(urlWiki);
        }


    }
}
