package com.eugene.spacecenter.ui.apod;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.eugene.spacecenter.R;
import com.eugene.spacecenter.databinding.FragmentApodTodayBinding;
import com.eugene.spacecenter.di.Injectable;
import com.eugene.spacecenter.ui.base.AppNavigator;
import com.eugene.spacecenter.ui.base.BaseFragment;
import com.eugene.spacecenter.ui.base.GlideApp;
import com.eugene.spacecenter.utils.DateUtils;
import com.eugene.spacecenter.utils.SnackbarUtils;

import java.io.File;
import java.util.Calendar;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApodTodayFragment extends BaseFragment<FragmentApodTodayBinding,ApodFragmentViewModel>
        implements Injectable, DatePickerDialog.OnDateSetListener{

    @Inject AppNavigator appNavigator;

    private DownloadManager downloadManager;
    private long downloadId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      return setAndBindContentView(inflater,container,savedInstanceState,R.layout.fragment_apod_today);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(viewModel.getDate().getValue()==null) {
            if(getArguments()!=null)
                viewModel.setDate(getArguments().getString(APODtodayActivity.KEY_DATE,DateUtils.getFormattedCurrentDate()));
            else
                viewModel.setDate(DateUtils.getFormattedCurrentDate());
        }

        observeViewModel();

        binding.setDate.setOnClickListener(v -> appNavigator.showDatePickerDialog(createDatePickerDialog()));

        binding.imageTodayApod.setOnClickListener(v->{
                if(viewModel.getApod().getValue()!=null && viewModel.getApod().getValue().isSuccessful()) {
                    if(viewModel.getApod().getValue().body.getHdurl()!=null)
                        appNavigator.navigateToApodHdFragment(viewModel.getApod().getValue().body.getHdurl());
                    else
                        appNavigator.navigateToApodHdFragment(viewModel.getApod().getValue().body.getUrl());
                }
        });

        binding.downloadButton.setOnClickListener(v-> downloadImage());


    }

    private void observeViewModel() {

        viewModel.getApod().observe(this, apodApiResponse -> {
            if (apodApiResponse != null) {

                if (apodApiResponse.isSuccessful()) {
                    binding.setApod(apodApiResponse.body);
                    loadImage(apodApiResponse.body.getUrl());
                } else {
                    if (isInternetConnected())
                        SnackbarUtils.showSnackbar(binding.getRoot(), apodApiResponse.errorMessage);
                    else
                        SnackbarUtils.showSnackbar(binding.getRoot(), getString(R.string.no_internet_connection));
                    binding.loadingImageIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadImage(String url) {

        Glide.with(this).clear(binding.imageTodayApod);
        binding.loadingImageIndicator.setVisibility(View.VISIBLE);
        binding.downloadButton.setVisibility(View.GONE);
        binding.errorImageText.setVisibility(View.GONE);

        GlideApp.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.loadingImageIndicator.setVisibility(View.GONE);
                        binding.downloadButton.setVisibility(View.GONE);
                        binding.errorImageText.setVisibility(View.VISIBLE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.loadingImageIndicator.setVisibility(View.GONE);
                        binding.downloadButton.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(binding.imageTodayApod);

    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnected();

    }

   private DatePickerDialog createDatePickerDialog(){
       Calendar cal = Calendar.getInstance();
       return new DatePickerDialog(getContext(), this,
               cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
   }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        viewModel.setDate(DateUtils.getFormattedDate(year, month+1, day));
    }

    private void downloadImage() {

        if(viewModel.getApod().getValue()==null || !viewModel.getApod().getValue().isSuccessful())
            return;


        if(downloadManager!=null)
            downloadManager.remove(downloadId);

        final Uri imageUri = Uri.parse(viewModel.getApod().getValue().body.getUrl());
        final String imageFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + imageUri.getLastPathSegment();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.download_image_message)
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
