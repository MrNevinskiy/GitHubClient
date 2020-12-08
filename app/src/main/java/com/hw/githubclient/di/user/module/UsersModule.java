package com.hw.githubclient.di.user.module;

import com.hw.githubclient.di.user.UsersScope;
import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.mvp.model.cache.IGithubUsersCache;
import com.hw.githubclient.mvp.model.cache.room.RoomGithubUsersCache;
import com.hw.githubclient.mvp.model.entity.room.Database;
import com.hw.githubclient.mvp.model.network.INetworkStatus;
import com.hw.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.hw.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersModule {
    @Provides
    IGithubUsersCache usersCache(Database db) {
        return new RoomGithubUsersCache(db);
    }

    @UsersScope
    @Provides
    public IGithubUsersRepo usersRepo(IDataSource api, INetworkStatus status, IGithubUsersCache cache) {
        return new RetrofitGithubUsersRepo(api, status, cache);
    }
}
