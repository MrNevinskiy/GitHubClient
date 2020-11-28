package com.hw.githubclient.navigation;

import androidx.fragment.app.Fragment;

import com.hw.githubclient.mvp.model.entity.GithubRepository;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.ui.fragments.RepositoryFragment;
import com.hw.githubclient.ui.fragments.UserFragment;
import com.hw.githubclient.ui.fragments.UsersFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class UserScreen extends SupportAppScreen{
        private final GithubUser user;

        public UserScreen(GithubUser user) {
            this.user = user;
        }

        @Override
        public Fragment getFragment() {
            return UserFragment.newInstance(user);
        }
    }

    public static class UsersScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return UsersFragment.getInstance(0);
        }
    }


    public static class RepositoryScreen extends SupportAppScreen {
        private final GithubRepository repository;

        public RepositoryScreen(GithubRepository repo) {
            this.repository = repo;
        }

        @Override
        public Fragment getFragment() {
            return RepositoryFragment.newInstance(repository);
        }
    }
}
