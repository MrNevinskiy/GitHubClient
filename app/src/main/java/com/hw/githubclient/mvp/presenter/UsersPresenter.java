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
import java.util.List;

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
//            String name = user.getLogin();
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
        List<GithubUser> users = usersRepo.getUsers();
        usersListPresenter.users.addAll(users);
        getViewState().updateList();
    }

    public boolean backPressed() {
        router.exit();
        return true;

    }
}
