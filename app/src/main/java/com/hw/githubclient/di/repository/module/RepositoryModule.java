package com.hw.githubclient.di.repository.module;

import com.hw.githubclient.di.repository.RepositoryScope;
import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.hw.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache;
import com.hw.githubclient.mvp.model.entity.room.Database;
import com.hw.githubclient.mvp.model.network.INetworkStatus;
import com.hw.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.hw.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    IGithubRepositoriesCache userRepositoriesCache(Database db) {
        return new RoomGithubRepositoriesCache(db);
    }

    @RepositoryScope
    @Provides
    public IGithubRepositoriesRepo userRepositoriesRepo(IDataSource api, INetworkStatus networkStatus, IGithubRepositoriesCache cache) {
        return new RetrofitGithubRepositoriesRepo(api, networkStatus, cache);
    }
}
