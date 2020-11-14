package com.hw.githubclient.mvp.presenter;

import com.hw.githubclient.GithubApplication;

import com.hw.githubclient.mvp.view.MainView;
import com.hw.githubclient.navigation.Screens;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class MainPresenter extends MvpPresenter<MainView> {

    private Router router = GithubApplication.getApplication().getRouter();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        // TODO: Nothing to do

        router.replaceScreen(new Screens.UsersScreen());
    }

    public void backClicked() {
        router.exit();
    }

}
