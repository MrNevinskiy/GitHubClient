package com.hw.githubclient.di.module;

import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.hw.githubclient.mvp.model.cache.IGithubUsersCache;
import com.hw.githubclient.mvp.model.network.INetworkStatus;
import com.hw.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.hw.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.hw.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;
import com.hw.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {
    @Singleton
    @Provides
    public IGithubUsersRepo usersRepo(IDataSource api, INetworkStatus status, IGithubUsersCache cache) {
        return new RetrofitGithubUsersRepo(api, status, cache);
    }

    @Singleton
    @Provides
    public IGithubRepositoriesRepo userRepositoriesRepo(IDataSource api, INetworkStatus networkStatus, IGithubRepositoriesCache cache) {
        return new RetrofitGithubRepositoriesRepo(api, networkStatus, cache);
    }
}
