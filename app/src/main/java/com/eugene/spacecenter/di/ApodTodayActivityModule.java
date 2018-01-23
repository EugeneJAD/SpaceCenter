package com.eugene.spacecenter.di;

import android.support.v4.app.FragmentActivity;

import com.eugene.spacecenter.ui.apod.APODtodayActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eugene on 21.01.2018.
 */
@Module
public class ApodTodayActivityModule {

    @Provides
    FragmentActivity provideActivity(APODtodayActivity activity) {return activity;}

}
