package com.hw.githubclient.mvp.model.repo.retrofit;


import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.model.repo.IGithubUsersRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {
    private IDataSource api;

    public RetrofitGithubUsersRepo(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return api.getUsers().subscribeOn(Schedulers.io());
    }

}
