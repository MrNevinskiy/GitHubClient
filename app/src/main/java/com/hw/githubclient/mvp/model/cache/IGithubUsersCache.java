package com.hw.githubclient.mvp.model.cache;

import com.hw.githubclient.mvp.model.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IGithubUsersCache {
    Single<List<GithubUser>> getUsers();
    Completable putUsers(List<GithubUser> users);
}
