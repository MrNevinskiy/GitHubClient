package com.hw.githubclient.mvp.model.repo.retrofit;

import com.hw.githubclient.mvp.model.api.IDataSource;
import com.hw.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.hw.githubclient.mvp.model.entity.GithubRepository;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.model.network.INetworkStatus;
import com.hw.githubclient.mvp.model.repo.IGithubRepositoriesRepo;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubRepositoriesRepo implements IGithubRepositoriesRepo {

    private IDataSource api;
    final IGithubRepositoriesCache cache;
    private INetworkStatus networkStatus;

    public RetrofitGithubRepositoriesRepo(IDataSource api, INetworkStatus status, IGithubRepositoriesCache cache) {
        this.api = api;
        this.networkStatus = status;
        this.cache = cache;
    }

    @Override
    public Single<List<GithubRepository>> getRepositories(GithubUser user) {
        return networkStatus.isOnlineSingle().flatMap((isOline)-> {
            if (isOline) {
                final String url = user.getReposUrl();

                if (url != null) {
                    return api.getRepositories(url).flatMap((repositories) -> {
                        return cache.putUserRepos(user, repositories).toSingleDefault(repositories);
                    });
                } else {
                    return Single.fromCallable(()->{
                        final List<GithubRepository> emptyList = Collections.emptyList();
                        return emptyList;
                    });
                }
            } else {
                return cache.getUserRepos(user);
            }
        }).subscribeOn(Schedulers.io());
    }
}
