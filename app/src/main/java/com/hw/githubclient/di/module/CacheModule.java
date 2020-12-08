package com.hw.githubclient.di.module;

import androidx.room.Room;

import com.hw.githubclient.GithubApplication;
import com.hw.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.hw.githubclient.mvp.model.cache.IGithubUsersCache;
import com.hw.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache;
import com.hw.githubclient.mvp.model.cache.room.RoomGithubUsersCache;
import com.hw.githubclient.mvp.model.entity.room.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {
    @Singleton
    @Provides
    Database database() {
        return Room.databaseBuilder(GithubApplication.INSTANCE, Database.class, Database.DB_NAME)
                .build();
    }
}