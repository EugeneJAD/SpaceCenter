package com.eugene.spacecenter.ui.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.eugene.spacecenter.data.api.ApiResponse;
import com.eugene.spacecenter.data.models.Apod;
import com.eugene.spacecenter.data.repository.Repository;
import com.eugene.spacecenter.utils.AbsentLiveData;
import com.eugene.spacecenter.utils.Objects;

import javax.inject.Inject;

/**
 * Created by Eugene on 21.01.2018.
 */

public class ApodFragmentViewModel extends ViewModel {

    @Inject Repository repository;

    private final LiveData<ApiResponse<Apod>> apod;
    private final MutableLiveData<String> date = new MutableLiveData<>();

    @Inject
    public ApodFragmentViewModel() {

        apod = Transformations.switchMap(date, date -> {
            if (date.isEmpty())
                return AbsentLiveData.create();

            return repository.getApod(date);
        });
    }

    public void setDate(String update) {
        if(update==null || Objects.equals(date.getValue(), update))
            return;
        date.setValue(update);
    }

    public LiveData<String> getDate() {return date;}

    public LiveData<ApiResponse<Apod>> getApod() {return apod;}


}
