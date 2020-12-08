package com.hw.githubclient.di.user;

import com.hw.githubclient.di.repository.RepositorySubcomponent;
import com.hw.githubclient.di.user.module.UsersModule;
import com.hw.githubclient.mvp.presenter.UsersPresenter;
import com.hw.githubclient.ui.adapter.UserRVAdapter;

import dagger.Subcomponent;

@UsersScope
@Subcomponent(
        modules = {
                UsersModule.class
        }
)
public interface UsersSubcomponent {
    RepositorySubcomponent repositorySubComponent();

    void inject(UsersPresenter usersPresenter);
    void inject(UserRVAdapter adapter);
}
