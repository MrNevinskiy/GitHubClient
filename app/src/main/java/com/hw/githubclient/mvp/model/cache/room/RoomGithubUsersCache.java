package com.hw.githubclient.mvp.model.cache.room;

import com.hw.githubclient.mvp.model.cache.IGithubUsersCache;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.model.entity.room.Database;
import com.hw.githubclient.mvp.model.entity.room.RoomGithubUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomGithubUsersCache implements IGithubUsersCache {
    private final Database db;

    public RoomGithubUsersCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return Single.fromCallable(()->{
            List<RoomGithubUser> roomGithubUsers = db.userDao().getAll();

            List<GithubUser> users = new ArrayList<>();

            for (RoomGithubUser roomGithubUser : roomGithubUsers) {
                GithubUser githubUser = new GithubUser(roomGithubUser.getId(),
                        roomGithubUser.getLogin(),
                        roomGithubUser.getAvatarUrl(),
                        roomGithubUser.getReposUrl());

                users.add(githubUser);
            }

            return users;
        });
    }

    @Override
    public Completable putUsers(List<GithubUser> users) {
        return Completable.fromAction(()->{
            List<RoomGithubUser> roomGithubUsers = new ArrayList<>();

            for (GithubUser user: users) {
                RoomGithubUser roomUser = new RoomGithubUser(user.getId(),
                        user.getLogin(),
                        user.getAvatarUrl(),
                        user.getReposUrl());

                roomGithubUsers.add(roomUser);
            }

            db.userDao().insert(roomGithubUsers);
        }).subscribeOn(Schedulers.io());
    }
}
