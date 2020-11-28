package com.hw.githubclient.mvp.model.repo.retrofit;

import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.mvp.model.entity.GithubRepository;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.model.repo.IGithubRepositoriesRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubRepositoriesRepo implements IGithubRepositoriesRepo {

    private IDataSource api;

    public RetrofitGithubRepositoriesRepo(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<List<GithubRepository>> getRepositories(GithubUser user) {
        final String url = user.getReposUrl();

        return api.getRepositories(url).subscribeOn(Schedulers.io());
    }
}
