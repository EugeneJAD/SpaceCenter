package com.eugene.spacecenter.di;

import android.support.v4.app.FragmentActivity;

import com.eugene.spacecenter.ui.solar.SolarSystemActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eugene on 25.01.2018.
 */

@Module
public class SolarSystemActivityModule {

    @Provides
    FragmentActivity provideActivity(SolarSystemActivity activity) {return activity;}
}
