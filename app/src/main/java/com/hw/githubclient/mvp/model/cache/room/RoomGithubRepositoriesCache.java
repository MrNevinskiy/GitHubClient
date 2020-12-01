package com.hw.githubclient.mvp.model.cache.room;

import com.hw.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.hw.githubclient.mvp.model.entity.GithubRepository;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.model.entity.room.Database;
import com.hw.githubclient.mvp.model.entity.room.RoomGithubRepository;
import com.hw.githubclient.mvp.model.entity.room.RoomGithubUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomGithubRepositoriesCache implements IGithubRepositoriesCache {
    private final Database db;

    public RoomGithubRepositoriesCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<List<GithubRepository>> getUserRepos(GithubUser user) {
        return Single.fromCallable(()-> {

            RoomGithubUser roomUser = db.userDao().findByLogin(user.getLogin());

            if (roomUser == null) {
                throw new RuntimeException("No such user in cache");
            }

            List<RoomGithubRepository> roomGithubRepository = db.repositoryDao().findByUser(roomUser.getId());

            List<GithubRepository> githubRepositoryList = new ArrayList<>();

            for (RoomGithubRepository roomGithubrepository : roomGithubRepository) {
                GithubRepository githubRepository = new GithubRepository(roomGithubrepository.getId(),
                        roomGithubrepository.getName(),
                        roomGithubrepository.getForksCount());

                githubRepositoryList.add(githubRepository);
            }

            return githubRepositoryList;
        });
    }

    @Override
    public Completable putUserRepos(GithubUser user, List<GithubRepository> repositories) {
        return Completable.fromAction(()->{
            RoomGithubUser roomUser = db.userDao().findByLogin(user.getLogin());

            List<RoomGithubRepository> roomGithubRepositories = new ArrayList<>();

            for (GithubRepository repo: repositories) {
                RoomGithubRepository roomRepo = new RoomGithubRepository(repo.getId(), repo.getName(), repo.getForksCount(), roomUser.getId());
                roomGithubRepositories.add(roomRepo);
            }

            db.repositoryDao().insert(roomGithubRepositories);
        }).subscribeOn(Schedulers.io());
    }
}