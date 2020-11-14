package com.hw.githubclient.mvp.presenter;

import com.hw.githubclient.GithubApplication;
import com.hw.githubclient.mvp.model.entity.GithubUserRepo;
import com.hw.githubclient.mvp.view.SelectedUserView;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class SelectedUserPresenter extends MvpPresenter<SelectedUserView> {

    private GithubUserRepo usersRepo = new GithubUserRepo();
    private Router router = GithubApplication.getApplication().getRouter();


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}
