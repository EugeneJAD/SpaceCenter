package com.eugene.spacecenter.di;

import com.eugene.spacecenter.BuildConfig;
import com.eugene.spacecenter.data.api.NasaApi;
import com.eugene.spacecenter.data.repository.DataRepository;
import com.eugene.spacecenter.data.repository.RemoteSource;
import com.eugene.spacecenter.data.repository.Repository;
import com.eugene.spacecenter.ui.apod.ApodFragmentViewModel;
import com.eugene.spacecenter.ui.apods.ApodsActivityViewModel;
import com.eugene.spacecenter.ui.main.MainActivityViewModel;
import com.eugene.spacecenter.ui.splash.SplashActivityViewModel;
import com.eugene.spacecenter.ui.splash.SplashFragmentViewModel;
import com.eugene.spacecenter.utils.AppConstants;
import com.eugene.spacecenter.utils.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module (includes = ViewModelModule.class)
class AppModule {


    @Singleton @Provides
    OkHttpClient provideOkHttpClient(){

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        return httpClientBuilder.build();
    }

    @Singleton @Provides
    Retrofit provideHttpClient(OkHttpClient okHttpClient){
        return new Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }


    @Provides
    RemoteSource remoteSource(NasaApi nasaApi){return new RemoteSource(nasaApi);}

    @Singleton @Provides
    Repository provideRepository(RemoteSource remoteSource){return new DataRepository(remoteSource);}

    @Provides
    NasaApi provideNasaApi(Retrofit retrofit) {
        return retrofit.create(NasaApi.class);
    }

    @Provides
    Class<SplashActivityViewModel> provideSplashActivityViewModel(){return SplashActivityViewModel.class;}

    @Provides
    Class<SplashFragmentViewModel> provideSplashFragmentViewModel(){return SplashFragmentViewModel.class;}

    @Provides
    Class<MainActivityViewModel> provideMainActivityViewModel(){return MainActivityViewModel.class;}

    @Provides
    Class<ApodFragmentViewModel> provideApodFragmentViewModel(){return ApodFragmentViewModel.class;}

    @Provides
    Class<ApodsActivityViewModel> provideApodsActivityViewModel(){return ApodsActivityViewModel.class;}
}
