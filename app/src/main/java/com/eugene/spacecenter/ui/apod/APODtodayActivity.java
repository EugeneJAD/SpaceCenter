package com.eugene.spacecenter.ui.apod;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.ui.base.AppNavigator;
import com.eugene.spacecenter.utils.DateUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class APODtodayActivity extends AppCompatActivity implements HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject AppNavigator appNavigator;

    public static final String KEY_DATE = "key_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apodtoday);

        String date;
        if(getIntent().getExtras()!=null)
            date = getIntent().getExtras().getString(KEY_DATE);
        else
            date = DateUtils.getFormattedCurrentDate();

        if(savedInstanceState==null)
            appNavigator.navigateToApodTodayFragment(date);

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
                if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof ApodHdFragment) {
                getSupportFragmentManager().popBackStack();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
