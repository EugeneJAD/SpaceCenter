package com.eugene.spacecenter;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Calendar;

public class ApodFragment extends Fragment implements LoaderManager.LoaderCallbacks<ApodBox>,  DatePickerDialog.OnDateSetListener{

    private static final String LOG_TAG = ApodFragment.class.getSimpleName();
    private static final String JSON_URL = "https://api.nasa.gov/planetary/apod?hd=false";
    private static final String API_KEY="&api_key=YAurBnDkk9eId7of823JM3MgW2ptbrQGXGaD81w";
    private static final String DATE_QUERY="&date=";
    private static final int APOD_LOADER_ID = 1;
    private String url;
    private String downloadImageURL;
    private String imageUrl;
    private TextView dateText;
    private TextView explanationText;
    private TextView titleText;
    private ImageView apodImageView;
    private View loadingIndicator;
    private View loadingImageIndicator;
    private TextView errorImageTextView;
    private DownloadManager downloadManager;
    private long downloadId;
    private ImageView downloadButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_apod, container, false);

        dateText = (TextView) rootView.findViewById(R.id.date_apod);
        explanationText = (TextView) rootView.findViewById(R.id.explanation_apod);
        titleText = (TextView) rootView.findViewById(R.id.title_apod);
        errorImageTextView = (TextView) rootView.findViewById(R.id.text_error_download_image);
        apodImageView=(ImageView) rootView.findViewById(R.id.web_view_apod);
        downloadButton = (ImageView) rootView.findViewById(R.id.download_button);

        loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingImageIndicator = rootView.findViewById(R.id.loading_image_indicator);
        TextView setDate = (TextView) rootView.findViewById(R.id.set_date);

        Calendar c = Calendar.getInstance();
        if (url==null)
            url=createURL(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(ApodFragment.this,0);
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(APOD_LOADER_ID, null, this);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (downloadImageURL!=null) {

                    if(downloadManager!=null)
                        downloadManager.remove(downloadId);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.download_image_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        downloadData(downloadImageURL);
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
                    Toast.makeText(getContext(),R.string.image_not_found, Toast.LENGTH_SHORT).show();

            }
        });

        errorImageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUrl!=null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        startActivity(intent);
                    }catch (ActivityNotFoundException ex) {
                        // Chrome browser presumably not installed so allow user to choose instead
                        intent.setPackage(null);
                        startActivity(intent);
                    }
                }
                else
                    Toast.makeText(getContext(),R.string.image_not_found, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        url=createURL(year,month+1, day);
        loadingIndicator.setVisibility(View.VISIBLE);
        downloadButton.setVisibility(View.GONE);
        loadingImageIndicator.setVisibility(View.GONE);
        errorImageTextView.setVisibility(View.GONE);
        getLoaderManager().restartLoader(APOD_LOADER_ID,null,this);

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

        return new ApodLoader(getContext(),url);
    }

    @Override
    public void onLoadFinished(Loader<ApodBox> loader, ApodBox data) {

        loadingIndicator.setVisibility(View.GONE);
        loadingImageIndicator.setVisibility(View.VISIBLE);
        errorImageTextView.setVisibility(View.VISIBLE);

        if (data!=null) {
            dateText.setText(data.getDate());
            titleText.setText(data.getTitle());
            explanationText.setText(data.getExplanation());

            Log.v(LOG_TAG, "data.getUrl() = "+data.getUrl());

            imageUrl = data.getUrl();

            Glide.with(getActivity()).load(imageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            loadingImageIndicator.setVisibility(View.GONE);
                            errorImageTextView.setVisibility(View.GONE);
                            downloadButton.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .dontAnimate()
                    .into(apodImageView);

            downloadImageURL = data.getHDurl();
        }
        else {

            if (!isInternetConnected()) {
                dateText.setText(R.string.no_internet_connection);
                loadingImageIndicator.setVisibility(View.GONE);
                errorImageTextView.setVisibility(View.GONE);
                titleText.setText("");
                explanationText.setText("");
            }
            else {
                dateText.setText(R.string.notfound);
                loadingImageIndicator.setVisibility(View.GONE);
                errorImageTextView.setVisibility(View.GONE);
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

        private DatePickerDialog.OnDateSetListener dateSetListener;
        DatePickerDialog myDatePicker;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            dateSetListener = (DatePickerDialog.OnDateSetListener)getTargetFragment();

            myDatePicker = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);

            return myDatePicker;
        }
    }

    private void downloadData(String downloadImageURL) {

        Log.v(LOG_TAG, "downloadImageURL "+downloadImageURL);

        Uri imageUri = Uri.parse(downloadImageURL);

        // Create request for android download manager
        downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
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
        request.setDestinationInExternalFilesDir(getContext(), Environment.DIRECTORY_DOWNLOADS,imageUri.getLastPathSegment());

        //Enqueue a new download and same the referenceId
        downloadId= downloadManager.enqueue(request);

        Toast.makeText(getContext(),"Image Downloading",Toast.LENGTH_SHORT).show();


    }

    private boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnected();

    }

}

