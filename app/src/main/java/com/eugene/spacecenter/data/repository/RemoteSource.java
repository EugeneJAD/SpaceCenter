package com.eugene.spacecenter.data.repository;

import android.arch.lifecycle.LiveData;

import com.eugene.spacecenter.data.api.ApiResponse;
import com.eugene.spacecenter.data.api.NasaApi;
import com.eugene.spacecenter.data.models.Apod;
import com.eugene.spacecenter.utils.AppConstants;

import javax.inject.Inject;

/**
 * Created by Eugene on 20.01.2018.
 */

public class RemoteSource {

    private NasaApi nasaApi;

    @Inject
    public RemoteSource(NasaApi nasaApi) {
        this.nasaApi = nasaApi;
    }

    public LiveData<ApiResponse<Apod>> apodCall(String date){return nasaApi.getAPOD(date, AppConstants.NASA_API_KEY);}

}
