package com.eugene.spacecenter.di;

import android.support.v4.app.FragmentActivity;

import com.eugene.spacecenter.ui.solar.WikiActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eugene on 26.01.2018.
 */
@Module
public class WikiActivityModule {

    @Provides
    FragmentActivity provideActivity(WikiActivity activity) {return activity;}
}
