package com.hw.githubclient.mvp.model.repo;

import com.hw.githubclient.mvp.model.entity.GithubRepository;
import com.hw.githubclient.mvp.model.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IGithubRepositoriesRepo {
    Single<List<GithubRepository>> getRepositories(GithubUser user);
}
