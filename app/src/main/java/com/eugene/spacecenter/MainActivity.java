package com.eugene.spacecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

import static com.eugene.spacecenter.FragmentAdapter.ESA_FRAGMENT_POSITION;
import static com.eugene.spacecenter.FragmentAdapter.HUBBLE_FRAGMENT_POSITION;
import static com.eugene.spacecenter.FragmentAdapter.ISS_FRAGMENT_POSITION;
import static com.eugene.spacecenter.FragmentAdapter.NASA_FRAGMENT_POSITION;
import static com.eugene.spacecenter.FragmentAdapter.SPACEX_FRAGMENT_POSITION;

public class MainActivity extends AppCompatActivity {

    private static final String TWITTER_KEY = "KuydykHcqTFPt0Whcgdrnncd0";
    private static final String TWITTER_SECRET = "R6eZR8UulR8rhBHUlMhObp9OXTkG32BDfZF6Xn7GwLx54bKmzs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), getString(R.string.admob_app_id));

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        categoryArrayList.add(new Category(R.drawable.ic_insert_photo_white_24dp, R.string.apod_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_art_track_white_24dp, R.string.asteroids_threat));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.esa_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.hubble_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.iss_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.nasa_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_art_track_white_24dp, R.string.solar_system_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_library_music_white_24dp, R.string.sounds_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.spacex_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_insert_photo_white_24dp, R.string.apod_list_page_title));

        ListView listView = (ListView) findViewById(R.id.list_category);

        final CategoryAdapter categoryAdapter = new CategoryAdapter(this,categoryArrayList);

        listView.setAdapter(categoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, APODtodayActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, AsteroidsActivity.class);
                    startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(MainActivity.this, SolarSystemActivity.class);
                    startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(MainActivity.this, SoundsActivity.class);
                    startActivity(intent);
                } else if (position == 9) {
                    Intent intent = new Intent(MainActivity.this, APODRecyclerViewActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);

                    if(position==2)
                        intent.putExtra("position", ESA_FRAGMENT_POSITION);
                    else if (position==3)
                        intent.putExtra("position", HUBBLE_FRAGMENT_POSITION);
                    else if (position==4)
                        intent.putExtra("position", ISS_FRAGMENT_POSITION);
                    else if (position==5)
                        intent.putExtra("position", NASA_FRAGMENT_POSITION);
                    else if (position==8)
                        intent.putExtra("position", SPACEX_FRAGMENT_POSITION);

                    startActivity(intent);
                }

            }

        });

    }
}
