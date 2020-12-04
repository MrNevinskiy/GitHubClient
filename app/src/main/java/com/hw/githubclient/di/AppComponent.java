package com.hw.githubclient.di;

import com.hw.githubclient.MainActivity;
import com.hw.githubclient.di.module.ApiModule;
import com.hw.githubclient.di.module.AppModule;
import com.hw.githubclient.di.module.CacheModule;
import com.hw.githubclient.di.module.CiceroneModule;
import com.hw.githubclient.di.module.RepoModule;
import com.hw.githubclient.mvp.presenter.MainPresenter;
import com.hw.githubclient.mvp.presenter.RepositoryPresenter;
import com.hw.githubclient.mvp.presenter.UserPresenter;
import com.hw.githubclient.mvp.presenter.UsersPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApiModule.class,
                AppModule.class,
                CacheModule.class,
                CiceroneModule.class,
                RepoModule.class
        }
)

public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
    void inject(UsersPresenter usersPresenter);
    void inject(UserPresenter userPresenter);
    void inject(RepositoryPresenter repositoryPresenter);
}
