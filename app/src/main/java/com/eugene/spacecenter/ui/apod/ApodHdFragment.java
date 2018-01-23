package com.eugene.spacecenter.ui.apod;


import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.eugene.spacecenter.R;
import com.eugene.spacecenter.databinding.FragmentApodHdBinding;
import com.eugene.spacecenter.di.Injectable;
import com.eugene.spacecenter.ui.base.BaseFragment;
import com.eugene.spacecenter.ui.base.GlideApp;

import java.io.File;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApodHdFragment extends BaseFragment<FragmentApodHdBinding, ApodFragmentViewModel>
        implements Injectable{

    public static final String KEY_URL = "key_url";
    private String url;
    private DownloadManager downloadManager;
    private long downloadId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return setAndBindContentView(inflater,container,savedInstanceState,R.layout.fragment_apod_hd);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null)
            url = getArguments().getString(KEY_URL);

        binding.downloadButton.setOnClickListener(v-> downloadImage());

        loadImage();

    }

    private void loadImage() {

        if(url==null) {
            binding.errorImageText.setVisibility(View.VISIBLE);
            return;
        }

        Timber.d("url = %s", url);

        binding.errorImageText.setVisibility(View.GONE);
        binding.downloadButton.setVisibility(View.GONE);
        binding.loadingImageIndicator.setVisibility(View.VISIBLE);

        GlideApp.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.loadingImageIndicator.setVisibility(View.GONE);
                        binding.downloadButton.setVisibility(View.GONE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.loadingImageIndicator.setVisibility(View.GONE);
                        binding.downloadButton.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(binding.apodTodayBigImage);
    }

    private void downloadImage() {

        if(downloadManager!=null)
            downloadManager.remove(downloadId);

        final Uri imageUri = Uri.parse(url);
        final String imageFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + imageUri.getLastPathSegment();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.download_HD_image_message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    File file = new File(imageFilePath);
                    if(!file.exists()) {
                        download(imageUri);
                    } else {
                        Toast.makeText(getContext(),R.string.image_exists,Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void download(Uri uri) {

        if (isStoragePermission()) {

            // Create request for android download manager
            downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            //Restrict the types of networks over which this download may proceed.
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

            //Set whether this download may proceed over a roaming connection.
            request.setAllowedOverRoaming(false);

            //Setting title of request
            request.setTitle(uri.getLastPathSegment());

            //Setting description of request
            request.setDescription("Astronomy Picture of the Day");

            request.allowScanningByMediaScanner();

            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,"/SpaceCenter/"+imageUri.getLastPathSegment());
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, uri.getLastPathSegment());

            //Enqueue a new download and same the referenceId
            downloadId = downloadManager.enqueue(request);

            Toast.makeText(getContext(),  R.string.image_downloading, Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getContext(), R.string.no_permission_storage, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT>=23){
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else
            return true;
    }

}
