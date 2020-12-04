package com.hw.githubclient.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class CiceroneModule {
    private Cicerone<Router> cicerone = Cicerone.create();

    @Provides
    public Cicerone<Router> cicerone() {
        return cicerone;
    }

    @Singleton
    @Provides
    public Router router() {
        return cicerone.getRouter();
    }

    @Singleton
    @Provides
    public NavigatorHolder navigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

}
