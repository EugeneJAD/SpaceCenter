package com.eugene.spacecenter.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.eugene.spacecenter.ui.apod.ApodFragmentViewModel;
import com.eugene.spacecenter.ui.apods.ApodsActivityViewModel;
import com.eugene.spacecenter.ui.main.MainActivityViewModel;
import com.eugene.spacecenter.ui.solar.SolarFragmentViewModel;
import com.eugene.spacecenter.ui.splash.SplashActivityViewModel;
import com.eugene.spacecenter.ui.splash.SplashFragmentViewModel;
import com.eugene.spacecenter.viewmodel.SpaceCenterViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashFragmentViewModel.class)
    abstract ViewModel bindSplashFragmentViewModel(SplashFragmentViewModel splashFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashActivityViewModel.class)
    abstract ViewModel bindSplashActivityViewModel(SplashActivityViewModel splashActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ApodFragmentViewModel.class)
    abstract ViewModel bindApodFragmentViewModel(ApodFragmentViewModel apodFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ApodsActivityViewModel.class)
    abstract ViewModel bindApodsActivityViewModel(ApodsActivityViewModel apodsActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SolarFragmentViewModel.class)
    abstract ViewModel bindSolarFragmentViewModel(SolarFragmentViewModel solarFragmentViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(SpaceCenterViewModelFactory factory);
}
