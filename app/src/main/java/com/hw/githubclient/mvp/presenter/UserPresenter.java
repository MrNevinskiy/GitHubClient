package com.hw.githubclient.mvp.presenter;

import android.util.Log;

import com.hw.githubclient.GithubApplication;
import com.hw.githubclient.mvp.model.entity.GithubRepository;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.hw.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;
import com.hw.githubclient.mvp.presenter.list.IRepositoryListPresenter;
import com.hw.githubclient.mvp.view.UserView;
import com.hw.githubclient.mvp.view.list.RepositoryItemView;
import com.hw.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UserPresenter extends MvpPresenter<UserView> {
    private static final String TAG = UserPresenter.class.getSimpleName();

    private static final boolean VERBOSE = true;

    private Router router;

    private IGithubRepositoriesRepo githubRepositoriesRepo;

    private Scheduler scheduler;
    private final GithubUser user;

    public UserPresenter(GithubUser user, Scheduler scheduler, IGithubRepositoriesRepo repo, Router router) {
        this.user = user;
        this.scheduler = scheduler;
        this.githubRepositoriesRepo = repo;
        this.router = router;
    }


    private class RepositoriesListPresenter implements IRepositoryListPresenter {
        private List<GithubRepository> repositories = new ArrayList<>();

        @Override
        public void onItemClick(RepositoryItemView view) {
            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + view.getPos());
            }

            final GithubRepository repository = repositories.get(view.getPos());
            router.navigateTo(new Screens.RepositoryScreen(repository));
        }

        @Override
        public void bindView(RepositoryItemView view) {
            GithubRepository repository = repositories.get(view.getPos());
            view.setName(repository.getName());
        }

        @Override
        public int getCount() {
            return repositories.size();
        }
    }

    private UserPresenter.RepositoriesListPresenter repositoriesListPresenter = new UserPresenter.RepositoriesListPresenter();

    public IRepositoryListPresenter getPresenter() {
        return repositoriesListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        loadData();
    }

    private void loadData() {
        githubRepositoriesRepo.getRepositories(user).observeOn(scheduler).subscribe(repositories -> {
            repositoriesListPresenter.repositories.clear();
            repositoriesListPresenter.repositories.addAll(repositories);
            getViewState().updateList();
        }, (e) -> {
            Log.w(TAG, "Error" + e.getMessage());
        });

    }

    public boolean backPressed() {
        router.exit();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
