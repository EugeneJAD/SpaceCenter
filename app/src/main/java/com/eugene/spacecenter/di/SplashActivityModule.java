package com.eugene.spacecenter.di;

import android.support.v4.app.FragmentActivity;

import com.eugene.spacecenter.ui.splash.SplashActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eugene on 17.01.2018.
 */

@Module
public class SplashActivityModule {

    @Provides
    FragmentActivity provideActivity(SplashActivity activity) {return activity;}
}
