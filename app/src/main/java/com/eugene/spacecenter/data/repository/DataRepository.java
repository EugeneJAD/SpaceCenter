package com.eugene.spacecenter.data.repository;

import android.arch.lifecycle.LiveData;

import com.eugene.spacecenter.data.api.ApiResponse;
import com.eugene.spacecenter.data.models.Apod;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Eugene on 20.01.2018.
 */

@Singleton
public class DataRepository implements Repository {

    private RemoteSource remoteSource;

    @Inject
    public DataRepository(RemoteSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @Override
    public LiveData<ApiResponse<Apod>> getApod(String date) {return remoteSource.apodCall(date);}

}
