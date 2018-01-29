package com.eugene.spacecenter.di;

import com.eugene.spacecenter.ui.apod.ApodHdFragment;
import com.eugene.spacecenter.ui.apod.ApodTodayFragment;
import com.eugene.spacecenter.ui.solar.SolarListFragment;
import com.eugene.spacecenter.ui.sounds.SoundsFragment;
import com.eugene.spacecenter.ui.splash.SplashFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SoundsFragment contributeSoundsFragment();

    @ContributesAndroidInjector
    abstract SplashFragment contributeSplashFragment();

    @ContributesAndroidInjector
    abstract ApodTodayFragment contributeApodTodayFragment();

    @ContributesAndroidInjector
    abstract ApodHdFragment contributeApodHdFragment();

    @ContributesAndroidInjector
    abstract SolarListFragment contributeSolarListFragment();
}