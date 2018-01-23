package com.eugene.spacecenter.data.repository;

import android.arch.lifecycle.LiveData;

import com.eugene.spacecenter.data.api.ApiResponse;
import com.eugene.spacecenter.data.models.Apod;

/**
 * Created by Eugene on 20.01.2018.
 */

public interface Repository {

    LiveData<ApiResponse<Apod>> getApod(String date);

}
