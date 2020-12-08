package com.hw.githubclient.di.repository;

import com.hw.githubclient.di.repository.module.RepositoryModule;
import com.hw.githubclient.mvp.presenter.RepositoryPresenter;
import com.hw.githubclient.mvp.presenter.UserPresenter;

import dagger.Subcomponent;

@RepositoryScope
@Subcomponent(
        modules = {
                RepositoryModule.class
        }
)
public interface RepositorySubcomponent {
    void inject(UserPresenter userPresenter);
    void inject(RepositoryPresenter repoPresenter);
}
