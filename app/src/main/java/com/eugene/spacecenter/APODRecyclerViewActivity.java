package com.eugene.spacecenter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

public class APODRecyclerViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ApodBox>>, APODRecyclerViewAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    private APODRecyclerViewAdapter apodRecyclerViewAdapter;
    private static final String JSON_URL = "https://api.nasa.gov/planetary/apod?hd=false";
    private static final String API_KEY="&api_key=YAurBnDkk9eId7of823JM3MgW2ZptbrQGXGaD81w";
    private static final String DATE_QUERY="&date=";
    private static final int APOD_LOADER_ID = 2;
    private List<ApodBox> mListApodBoxes;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apodrecycler_view);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView noInternetMessage = (TextView) findViewById(R.id.no_internet_connection_message);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        recyclerView = (RecyclerView) findViewById(R.id.apod_recycler_view);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        apodRecyclerViewAdapter = new APODRecyclerViewAdapter (new ArrayList<ApodBox>(), this);

        recyclerView.setAdapter(apodRecyclerViewAdapter);

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
    public Loader<List<ApodBox>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<ApodBox>>(this) {

            List<ApodBox> mApodBoxes;

            protected void onStartLoading() {
                if (mApodBoxes != null) {
                    deliverResult(mApodBoxes);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<ApodBox> loadInBackground() {

                long date = currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                List<ApodBox> resultList = new ArrayList<>();

                for (int i=0; i<20;i++) {

                    String dateForUrl = simpleDateFormat.format(date);
                    String url = JSON_URL+DATE_QUERY+dateForUrl+API_KEY;
                    ApodBox apodBox = QueryUtils.fetchAPODData(url);
                    if (apodBox != null) {
                        resultList.add(apodBox);
                    }
                    date = date-TimeUnit.DAYS.toMillis(1);

                }

                return resultList;
            }

            public void deliverResult(List<ApodBox> data) {
                mApodBoxes = data;
                super.deliverResult(data);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<List<ApodBox>> loader, List<ApodBox> data) {

        progressBar.setVisibility(View.INVISIBLE);

        if(data!=null) {
            apodRecyclerViewAdapter.setAPODImagesData(data);
        } else {
            Toast.makeText(this, R.string.images_not_found, Toast.LENGTH_LONG).show();
        }
        mListApodBoxes = data;

    }

    @Override
    public void onLoaderReset(Loader<List<ApodBox>> loader) {

    }

    @Override
    public void onClick(int indexOfItem) {

        if (mListApodBoxes!=null){
            Intent intent = new Intent(this,APODtodayActivity.class);
            intent.putExtra("date",mListApodBoxes.get(indexOfItem).getDate());
            startActivity(intent);
        }


    }
}
