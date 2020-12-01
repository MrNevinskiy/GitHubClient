package com.hw.githubclient.mvp.model.repo.retrofit;


import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.mvp.model.cache.IGithubUsersCache;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.model.network.INetworkStatus;
import com.hw.githubclient.mvp.model.repo.IGithubUsersRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {

    private IDataSource api;
    final IGithubUsersCache cache;
    private INetworkStatus networkStatus;

    public RetrofitGithubUsersRepo(IDataSource api, INetworkStatus status, IGithubUsersCache cache) {
        this.api = api;
        this.networkStatus = status;
        this.cache = cache;
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return networkStatus.isOnlineSingle().flatMap((isOline)-> {
            // Мапируем сетевой статус к данным
            if (isOline) {
                return api.getUsers().flatMap((users) -> {
                    return cache.putUsers(users).toSingleDefault(users);
                });
            } else {
                return cache.getUsers();
            }
        }).subscribeOn(Schedulers.io());
    }

}
