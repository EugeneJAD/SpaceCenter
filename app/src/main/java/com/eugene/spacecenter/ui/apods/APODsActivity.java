package com.eugene.spacecenter.ui.apods;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.databinding.ActivityApodrecyclerViewBinding;
import com.eugene.spacecenter.ui.base.AppNavigator;
import com.eugene.spacecenter.ui.base.BaseActivity;
import com.eugene.spacecenter.utils.DateUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static java.lang.System.currentTimeMillis;

public class APODsActivity extends BaseActivity<ActivityApodrecyclerViewBinding,ApodsActivityViewModel>
        implements HasSupportFragmentInjector, ApodRVAdapter.ApodItemClickListener {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject AppNavigator appNavigator;

    private ApodRVAdapter adapter;
    private static final int ITEMS_QUANTITY = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAndBindContentView(R.layout.activity_apodrecycler_view);

        binding.apodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.apodRecyclerView.setHasFixedSize(true);
        adapter = new ApodRVAdapter(this);
        binding.apodRecyclerView.setAdapter(adapter);

        observeViewModel();

        if(viewModel.getApods().isEmpty()){
            viewModel.setDateInMillis(currentTimeMillis());
            viewModel.setDate(DateUtils.getFormattedDate(viewModel.getDateInMillis()));
        }

        if (isInternetConnected()) {
            binding.noInternetConnectionMessage.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.noInternetConnectionMessage.setVisibility(View.VISIBLE);
        }

    }

    private void observeViewModel() {

        viewModel.getApod().observe(this, apodApiResponse -> {
            if(apodApiResponse!=null && apodApiResponse.isSuccessful()){
                viewModel.getApods().add(apodApiResponse.body);
                if(binding.progressBar.getVisibility()!=View.GONE)
                    binding.progressBar.setVisibility(View.GONE);
                adapter.replace(viewModel.getApods());
            }

            if(viewModel.getApods().size()<ITEMS_QUANTITY){
                viewModel.setDateInMillis(viewModel.getDateInMillis()- TimeUnit.DAYS.toMillis(1));
                viewModel.setDate(DateUtils.getFormattedDate(viewModel.getDateInMillis()));
            }
        });
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnected();
    }


    @Override
    public void onItemClick(String date) {appNavigator.navigateToAPOD(date);}

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {return dispatchingAndroidInjector;}
}
