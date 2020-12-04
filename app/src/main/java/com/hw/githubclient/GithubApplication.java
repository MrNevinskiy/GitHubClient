package com.hw.githubclient;

import android.app.Application;

import com.hw.githubclient.di.AppComponent;
import com.hw.githubclient.di.DaggerAppComponent;
import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.di.module.AppModule;


public class GithubApplication extends Application {

    public static final boolean DEBUG = true;

    public static GithubApplication INSTANCE;

    private ApiHolder apiHolder;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        apiHolder = new ApiHolder();

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static GithubApplication getApplication() {
        return INSTANCE;
    }

    public IDataSource getApi() {
        return apiHolder.getDataSource();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
