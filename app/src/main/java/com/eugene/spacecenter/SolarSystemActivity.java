package com.eugene.spacecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class SolarSystemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar_system);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ArrayList<PlanetItem> planetList = new ArrayList<PlanetItem>();
        planetList.add(new PlanetItem(this,R.drawable.sun,R.string.sun_title,R.string.sun_info, R.string.sun_url));
        planetList.add(new PlanetItem(this,R.drawable.mercury,R.string.mercury_title,R.string.mercury_info, R.string.mercury_url));
        planetList.add(new PlanetItem(this,R.drawable.venus,R.string.venus_title,R.string.venus_info, R.string.venus_url));
        planetList.add(new PlanetItem(this,R.drawable.earth, R.string.earth_title,R.string.earth_info, R.string.earth_url));
        planetList.add(new PlanetItem(this,R.drawable.mars,R.string.mars_title,R.string.mars_info, R.string.mars_url));
        planetList.add(new PlanetItem(this,R.drawable.ceres,R.string.ceres_title,R.string.ceres_info, R.string.ceres_url));
        planetList.add(new PlanetItem(this,R.drawable.jupiter,R.string.jupiter_title,R.string.jupiter_info, R.string.jupiter_url));;
        planetList.add(new PlanetItem(this,R.drawable.saturn,R.string.saturn_title,R.string.saturn_info, R.string.saturn_url));;
        planetList.add(new PlanetItem(this,R.drawable.uranus,R.string.uranus_title,R.string.uranus_info, R.string.uranus_url));;
        planetList.add(new PlanetItem(this,R.drawable.neptune,R.string.neptune_title,R.string.neptune_info, R.string.neptune_url));;
        planetList.add(new PlanetItem(this,R.drawable.pluto,R.string.pluto_title,R.string.pluto_info, R.string.pluto_url));;
        planetList.add(new PlanetItem(this,R.drawable.makemake,R.string.makemake_title,R.string.makemake_info, R.string.makemake_url));;
        planetList.add(new PlanetItem(this,R.drawable.haumea,R.string.haumea_title,R.string.haumea_info, R.string.haumea_url));;
        planetList.add(new PlanetItem(this,R.drawable.eris,R.string.eris_title,R.string.eris_info, R.string.eris_url));;

        final PlanetListAdapter adapter = new PlanetListAdapter(this,planetList);

        ListView listView = (ListView)findViewById(R.id.list_solar_system);

        View loadingIndicator = findViewById(R.id.loading_indicator);
        listView.setEmptyView(loadingIndicator);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PlanetItem current = adapter.getItem(position);

                Intent intent = new Intent(SolarSystemActivity.this,WikiSolarSystemActivity.class);
                intent.putExtra("url",current.getURL());
                startActivity(intent);

            }
        });


    }
}
