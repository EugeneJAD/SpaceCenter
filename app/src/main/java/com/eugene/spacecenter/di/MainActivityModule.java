package com.eugene.spacecenter.di;

import android.support.v4.app.FragmentActivity;

import com.eugene.spacecenter.ui.main.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    FragmentActivity provideActivity(MainActivity activity) {return activity;}
}