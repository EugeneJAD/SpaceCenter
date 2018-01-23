package com.eugene.spacecenter.data.api;


import android.arch.lifecycle.LiveData;

import com.eugene.spacecenter.data.models.Apod;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApi {

    @GET("planetary/apod")
    LiveData<ApiResponse<Apod>> getAPOD(@Query("date") String data, @Query("api_key") String key);

}
