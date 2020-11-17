package com.hw.githubclient.mvp.presenter;

import android.util.Log;

import com.hw.githubclient.GithubApplication;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.model.entity.GithubUserRepo;
import com.hw.githubclient.mvp.presenter.list.IUserListPresenter;
import com.hw.githubclient.mvp.view.UserItemView;
import com.hw.githubclient.mvp.view.UsersView;
import com.hw.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<UsersView> {
    private static final String TAG = UsersPresenter.class.getSimpleName();

    private static final boolean VERBOSE = true;

    private GithubUserRepo usersRepo = new GithubUserRepo();
    private Router router = GithubApplication.getApplication().getRouter();

    private class UsersListPresenter implements IUserListPresenter {

        private List<GithubUser> users = new ArrayList<>();

        @Override
        public void onItemClick(UserItemView view) {
            GithubUser user = users.get(view.getPos());
            router.navigateTo(new Screens.SelectedUserScreen(user));
            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + view.getPos());
            }
        }

        @Override
        public void bindView(UserItemView view) {
            GithubUser user = users.get(view.getPos());
            view.setLogin(user.getLogin());
        }

        @Override
        public int getCount() {
            return users.size();
        }
    }

    private UsersListPresenter usersListPresenter = new UsersListPresenter();

    public UsersListPresenter getUsersListPresenter() {
        return usersListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        loadData();

    }

    private void loadData() {
        usersRepo.getUsers().subscribe(stringObserver);
        getViewState().updateList();
    }

    final Observer<List> stringObserver = new Observer<List>() {

        @Override
        public void onSubscribe(@NonNull Disposable d) { }

        @Override
        public void onNext(@NonNull List arrayList) {
            usersListPresenter.users.addAll(arrayList);
        }

        @Override
        public void onError(@NonNull Throwable e) { }

        @Override
        public void onComplete() { }
    };

    public boolean backPressed() {
        router.exit();
        return true;

    }
}
