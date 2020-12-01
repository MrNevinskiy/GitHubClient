package com.hw.githubclient.mvp.model.cache;

import com.hw.githubclient.mvp.model.entity.GithubRepository;
import com.hw.githubclient.mvp.model.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IGithubRepositoriesCache {
    Single<List<GithubRepository>> getUserRepos(GithubUser user);
    Completable putUserRepos(GithubUser user, List<GithubRepository> repositories);
}
