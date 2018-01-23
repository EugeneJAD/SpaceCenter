package com.eugene.spacecenter.di;

import android.support.v4.app.FragmentActivity;

import com.eugene.spacecenter.ui.apods.APODsActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eugene on 23.01.2018.
 */
@Module
public class APODsActivityModule {

    @Provides
    FragmentActivity provideActivity(APODsActivity activity) {return activity;}
}
