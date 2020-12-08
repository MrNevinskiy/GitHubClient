package com.hw.githubclient.di;

import com.hw.githubclient.MainActivity;
import com.hw.githubclient.di.module.ApiModule;
import com.hw.githubclient.di.module.AppModule;
import com.hw.githubclient.di.module.CacheModule;
import com.hw.githubclient.di.module.CiceroneModule;
import com.hw.githubclient.di.module.ImageModule;
import com.hw.githubclient.di.user.UsersSubcomponent;
import com.hw.githubclient.mvp.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (
        modules = {
                ApiModule.class,
                AppModule.class,
                CacheModule.class,
                CiceroneModule.class,
                ImageModule.class
        }
)

public interface AppComponent {
    UsersSubcomponent userSubComponent();

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
}
