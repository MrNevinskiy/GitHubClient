package com.hw.githubclient;

import android.app.Application;

import com.hw.githubclient.mvp.model.api.IDataSource;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class GithubApplication extends Application {

    public static final boolean DEBUG = true;

    public static GithubApplication INSTANCE;

    private Cicerone<Router> cicerone;
    private ApiHolder apiHolder;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        initCicerone();
        apiHolder = new ApiHolder();
    }

    public static GithubApplication getApplication() {
        return INSTANCE;
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public IDataSource getApi() {
        return apiHolder.getDataSource();
    }
}
