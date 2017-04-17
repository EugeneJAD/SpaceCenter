package com.eugene.spacecenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

public class AsteroidsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Asteroid>> {

    private RecyclerView mRecyclerView;
    private AsteroidsAdapter mAsteroidsAdapter;
    private static final String JSON_URL = "https://api.nasa.gov/neo/rest/v1/feed?";
    private static final String API_KEY="&api_key=YAurBnDkk9eId7of823JM3MgW2ZptbrQGXGaD81w";
    private static final String DATE_START="start_date=";
    private static final String DATE_END="&end_date=";
    private static final int APOD_LOADER_ID = 3;
    private ProgressBar progressBar;
    private TextView noInformationMassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroids);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView noInternetMessage = (TextView) findViewById(R.id.no_internet_connection_message);
        noInformationMassage = (TextView) findViewById(R.id.no_information_found);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.apod_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAsteroidsAdapter = new AsteroidsAdapter (new ArrayList<Asteroid>());
        mRecyclerView.setAdapter(mAsteroidsAdapter);


        if (isInternetConnected()) {
            noInternetMessage.setVisibility(View.GONE);
            getSupportLoaderManager().initLoader(APOD_LOADER_ID, null, this);
        } else {
            noInternetMessage.setVisibility(View.VISIBLE);
        }

    }

    private boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnected();

    }

    @Override
    public Loader<List<Asteroid>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Asteroid>>(this) {

            private List<Asteroid> mAsteroidList;

            protected void onStartLoading() {
                if (mAsteroidList != null) {
                    deliverResult(mAsteroidList);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Asteroid> loadInBackground() {

                long date = currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                List<Asteroid> resultList = new ArrayList<>();

                String dateEnd= simpleDateFormat.format(date);
                String dateStart = simpleDateFormat.format(date-TimeUnit.DAYS.toMillis(1));
                String url = JSON_URL+DATE_START+dateStart+DATE_END+dateEnd+API_KEY;

                resultList = QueryUtils.fetchAsteroidsData(url,dateStart,dateEnd);

                return resultList;
            }

            public void deliverResult(List<Asteroid> data) {
                mAsteroidList = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Asteroid>> loader, List<Asteroid> data) {

        progressBar.setVisibility(View.INVISIBLE);

        if(data!=null && data.size()>0) {
            mAsteroidsAdapter.setAsteroidsData(data);
        } else {
            noInformationMassage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Asteroid>> loader) {

    }
}
