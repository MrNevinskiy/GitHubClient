package com.hw.githubclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hw.githubclient.di.AppComponent;
import com.hw.githubclient.di.DaggerAppComponent;
import com.hw.githubclient.di.repository.RepositorySubcomponent;
import com.hw.githubclient.di.user.UsersSubcomponent;
import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.di.module.AppModule;


public class GithubApplication extends Application {

    public static final boolean DEBUG = true;

    public static GithubApplication INSTANCE;

    private ApiHolder apiHolder;

    private AppComponent appComponent;

    @Nullable
    private UsersSubcomponent userSubcomponent;

    @Nullable
    private RepositorySubcomponent repositorySubcomponent;

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

    @NonNull
    public UsersSubcomponent initUserSubcomponent() {
        AppComponent appComp = this.appComponent;

        if (appComp == null) {
            throw new IllegalStateException("appComponent must be initialized!!!");
        }

        if (userSubcomponent == null) {
            UsersSubcomponent userSubcomponent = appComp.userSubComponent();

            this.userSubcomponent = userSubcomponent;
        }

        return userSubcomponent;
    }

    public void releaseUserSubcomponent() {
        userSubcomponent = null;
    }

    @Nullable
    public RepositorySubcomponent initRepositoriesSubcomponent() {
        RepositorySubcomponent repositorySubcomponent = (userSubcomponent != null) ? userSubcomponent.repositorySubComponent() : null;

        this.repositorySubcomponent = repositorySubcomponent;

        return repositorySubcomponent;
    }

    public void releaseRepositorySubcomponent() {
        repositorySubcomponent = null;
    }
}
