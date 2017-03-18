package com.eugene.spacecenter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class APODImageActivity extends AppCompatActivity {

    ProgressBar loadingImageIndicator;
    ImageView downloadButton;
    private DownloadManager downloadManager;
    String imageURL;
    private long downloadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apodimage);

        imageURL = getIntent().getExtras().getString("hdImageURL");

        ImageView imageView = (ImageView) findViewById(R.id.apod_today_big_image);
        loadingImageIndicator = (ProgressBar) findViewById(R.id.loading_image_indicator);
        downloadButton = (ImageView) findViewById(R.id.download_button);

        Glide.with(this).load(imageURL)
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
                .centerCrop().into(imageView);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageURL!=null) {

                    if(downloadManager!=null)
                        downloadManager.remove(downloadId);

                    final Uri imageUri = Uri.parse(imageURL);
                    final String imageFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + imageUri.getLastPathSegment();

                    AlertDialog.Builder builder = new AlertDialog.Builder(APODImageActivity.this);
                    builder.setMessage(R.string.download_HD_image_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File file = new File(imageFilePath);
                                    if(!file.exists()) {
                                        downloadData(imageURL);
                                    } else {
                                        Toast.makeText(APODImageActivity.this,R.string.image_exists,Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(APODImageActivity.this,R.string.image_not_found, Toast.LENGTH_SHORT).show();

            }
        });

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

            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,"/SpaceCenter/"+imageUri.getLastPathSegment());
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, imageUri.getLastPathSegment());

            //Enqueue a new download and same the referenceId
            downloadId = downloadManager.enqueue(request);

            Toast.makeText(this,  R.string.image_downloading, Toast.LENGTH_SHORT).show();
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
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return false;
        } else
            return true;

    }



}
