package com.eugene.spacecenter.ui.solar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Planet;
import com.eugene.spacecenter.utils.JsonUtils;

public class SolarSystemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar_system);

        final PlanetListAdapter adapter = new PlanetListAdapter(this, JsonUtils.getAllSolarObjects(this));

        ListView listView = (ListView)findViewById(R.id.list_solar_system);

        View loadingIndicator = findViewById(R.id.loading_indicator);
        listView.setEmptyView(loadingIndicator);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Planet current = adapter.getItem(position);

                Intent intent = new Intent(SolarSystemActivity.this,WikiSolarSystemActivity.class);
                intent.putExtra("url",current.getUrl());
                startActivity(intent);

            }
        });


    }
}
