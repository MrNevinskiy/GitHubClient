package com.hw.githubclient.di.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.mvp.model.network.INetworkStatus;
import com.hw.githubclient.ui.network.AndroidNetworkStatus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Named("baseUrl")
    @Provides
    String baseUrl() {
        return "https://api.github.com/";
    }

    @Singleton
    @Provides
    Gson gson() {
        return new GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation().create();
    }

    @Singleton
    @Provides
    IDataSource api(@Named("baseUrl") String baseUrl, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(IDataSource.class);
    }

    @Singleton
    @Provides
    INetworkStatus networkStatus() {
        return new AndroidNetworkStatus();
    }

}
