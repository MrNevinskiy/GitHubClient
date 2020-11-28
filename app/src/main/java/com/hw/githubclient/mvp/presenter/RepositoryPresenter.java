package com.hw.githubclient.mvp.presenter;

import com.hw.githubclient.GithubApplication;
import com.hw.githubclient.mvp.model.entity.GithubRepository;
import com.hw.githubclient.mvp.model.entity.GithubUserRepo;
import com.hw.githubclient.mvp.view.RepositoryView;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class RepositoryPresenter extends MvpPresenter<RepositoryView> {

    private final GithubRepository githubRepository;

    private Router router = GithubApplication.getApplication().getRouter();

    public RepositoryPresenter(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        String id = githubRepository.getId();
        String title = githubRepository.getName();
        int forks = githubRepository.getForksCount();

        getViewState().setId(id != null ? id : "");
        getViewState().setTitle(title != null ? title : "");
        getViewState().setForksCount(String.valueOf(forks));
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}
