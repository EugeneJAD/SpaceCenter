package com.eugene.spacecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String TWITTER_KEY = "KuydykHcqTFPt0Whcgdrnncd0";
    private static final String TWITTER_SECRET = "R6eZR8UulR8rhBHUlMhObp9OTkG32BDfZF6Xn7GwLx54bKmzs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.nasa_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.esa_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.iss_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.hubble_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_list_white_24dp, R.string.earth_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_art_track_white_24dp, R.string.solar_system_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_insert_photo_white_24dp, R.string.apod_page_title));
        categoryArrayList.add(new Category(R.drawable.ic_library_music_white_24dp, R.string.sounds_page_title));

        ListView listView = (ListView) findViewById(R.id.list_category);

        final CategoryAdapter categoryAdapter = new CategoryAdapter(this,categoryArrayList);

        listView.setAdapter(categoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Category current = categoryAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });

    }

}
