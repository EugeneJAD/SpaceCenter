package com.eugene.spacecenter;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.Calendar;


public class APODtodayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ApodBox>,  DatePickerDialog.OnDateSetListener{

    private static final String JSON_URL = "https://api.nasa.gov/planetary/apod?hd=false";
    private static final String API_KEY="&api_key=YAurBnDkk9eId7of823JM3MgW2ZptbrQGXGaD81w";
    private static final String DATE_QUERY="&date=";
    private static final int APOD_LOADER_ID = 1;
    private String url;
    private String downloadImageURL;
    private String imageUrl;
    private String imageUrlHD;
    private TextView dateText;
    private TextView explanationText;
    private TextView titleText;
    private ImageView apodImageView;
    private View loadingImageIndicator;
    private DownloadManager downloadManager;
    private long downloadId;
    private ImageView downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apodtoday);


        dateText = (TextView) findViewById(R.id.date_apod);
        explanationText = (TextView) findViewById(R.id.explanation_apod);
        titleText = (TextView) findViewById(R.id.title_apod);
        apodImageView=(ImageView) findViewById(R.id.image_today_apod);
        downloadButton = (ImageView) findViewById(R.id.download_button);

        loadingImageIndicator = findViewById(R.id.loading_image_indicator);
        TextView setDate = (TextView) findViewById(R.id.set_date);

        if(getIntent().hasExtra("date")){
            url = JSON_URL+DATE_QUERY+getIntent().getStringExtra("date")+API_KEY;
        } else {
            Calendar c = Calendar.getInstance();
            url = createURL(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        }

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(APOD_LOADER_ID, null, this);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (downloadImageURL!=null) {

                    if(downloadManager!=null)
                        downloadManager.remove(downloadId);

                    final Uri imageUri = Uri.parse(downloadImageURL);
                    final String imageFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + imageUri.getLastPathSegment();

                    AlertDialog.Builder builder = new AlertDialog.Builder(APODtodayActivity.this);
                    builder.setMessage(R.string.download_image_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    File file = new File(imageFilePath);
                                    if(!file.exists()) {
                                        downloadData(downloadImageURL);
                                    } else {
                                        Toast.makeText(APODtodayActivity.this,R.string.image_exists,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (dialog!=null)
                                        dialog.dismiss();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                    Toast.makeText(APODtodayActivity.this,R.string.image_not_found, Toast.LENGTH_SHORT).show();

            }
        });


        apodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUrlHD!=null) {
                    makeIntentToImageActivity(imageUrlHD);
                } else {
                    makeIntentToImageActivity(imageUrl);
                }

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        url=createURL(year,month+1, day);
        downloadButton.setVisibility(View.GONE);
        loadingImageIndicator.setVisibility(View.GONE);
        getSupportLoaderManager().restartLoader(APOD_LOADER_ID,null,this);

    }

    private String createURL(int year, int month, int day) {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(JSON_URL)
                .append(DATE_QUERY)
                .append(year)
                .append("-")
                .append(month)
                .append("-")
                .append(day)
                .append(API_KEY);

        return urlBuilder.toString();

    }

    @Override
    public Loader<ApodBox> onCreateLoader(int id, Bundle args) {

        return new ApodLoader(this,url);
    }

    @Override
    public void onLoadFinished(Loader<ApodBox> loader, ApodBox data) {

        loadingImageIndicator.setVisibility(View.VISIBLE);

        if (data!=null) {
            dateText.setText(data.getDate());
            titleText.setText(data.getTitle());
            explanationText.setText(data.getExplanation());

            imageUrlHD = data.getHDurl();
            imageUrl = data.getUrl();

            Glide.with(this).load(imageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            loadingImageIndicator.setVisibility(View.GONE);
                            downloadButton.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(apodImageView);

            downloadImageURL = data.getHDurl();
        }
        else {

            if (!isInternetConnected()) {
                dateText.setText(R.string.no_internet_connection);
                loadingImageIndicator.setVisibility(View.GONE);
                titleText.setText("");
                explanationText.setText("");
            }
            else {
                dateText.setText(R.string.notfound);
                loadingImageIndicator.setVisibility(View.GONE);
                titleText.setText("");
                explanationText.setText("");
            }
        }



    }

    @Override
    public void onLoaderReset(Loader<ApodBox> loader) {
        loader = null;
    }

    public static class DatePickerFragment extends DialogFragment{

        DatePickerDialog myDatePicker;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            myDatePicker = new DatePickerDialog(getActivity(), (APODtodayActivity)getActivity(), year, month, day);

            return myDatePicker;
        }
    }

    private void downloadData(String downloadImageURL) {


        if (isStoragePermission()) {

            Uri imageUri = Uri.parse(downloadImageURL);

            // Create request for android download manager
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(imageUri);

            //Restrict the types of networks over which this download may proceed.
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

            //Set whether this download may proceed over a roaming connection.
            request.setAllowedOverRoaming(false);

            //Setting title of request
            request.setTitle(imageUri.getLastPathSegment());

            //Setting description of request
            request.setDescription("Astronomy Picture of the Day");

            request.allowScanningByMediaScanner();

            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/SpaceCenter/"+imageUri.getLastPathSegment());
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, imageUri.getLastPathSegment());

            //Enqueue a new download and same the referenceId
            downloadId = downloadManager.enqueue(request);

            Toast.makeText(this, R.string.image_downloading, Toast.LENGTH_SHORT).show();

        } else{
            Toast.makeText(this, R.string.no_permission_storage, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isStoragePermission (){

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT>=23){
            if (permissionCheck == PackageManager.PERMISSION_GRANTED)
                return true;
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return false;
        } else
            return true;

    }

    private boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnected();

    }

    private void makeIntentToImageActivity(String url){

        Intent intent = new Intent(this,APODImageActivity.class);
        intent.putExtra("hdImageURL", url);
        startActivity(intent);

    }

}
