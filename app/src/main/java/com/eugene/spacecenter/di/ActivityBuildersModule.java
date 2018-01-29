package com.eugene.spacecenter.di;

import com.eugene.spacecenter.ui.apod.APODtodayActivity;
import com.eugene.spacecenter.ui.apods.APODsActivity;
import com.eugene.spacecenter.ui.main.MainActivity;
import com.eugene.spacecenter.ui.solar.SolarSystemActivity;
import com.eugene.spacecenter.ui.solar.WikiActivity;
import com.eugene.spacecenter.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Eugene on 17.01.2018.
 */

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {SplashActivityModule.class, FragmentBuildersModule.class})
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class, FragmentBuildersModule.class})
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = {ApodTodayActivityModule.class, FragmentBuildersModule.class})
    abstract APODtodayActivity contributeAPODtodayActivity();

    @ContributesAndroidInjector(modules = {APODsActivityModule.class, FragmentBuildersModule.class})
    abstract APODsActivity contributeAPODsActivity();

    @ContributesAndroidInjector(modules = {SolarSystemActivityModule.class, FragmentBuildersModule.class})
    abstract SolarSystemActivity contributeSolarSystemActivity();

    @ContributesAndroidInjector(modules = {WikiActivityModule.class, FragmentBuildersModule.class})
    abstract WikiActivity contributeWikiActivity();
}

