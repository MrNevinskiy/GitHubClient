package com.hw.githubclient.navigation;

import androidx.fragment.app.Fragment;

import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.ui.fragments.SelectedUserFragment;
import com.hw.githubclient.ui.fragments.UsersFragment;

import java.io.Serializable;
import java.util.Arrays;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class SelectedUserScreen extends SupportAppScreen{

        private GithubUser user;

        public SelectedUserScreen(GithubUser user) {

            this.user = user;
        }

        @Override
        public Fragment getFragment() {
            return SelectedUserFragment.getInstance(user);
        }
//        String name;
//        public SelectedUserScreen(Serializable name) {
//           this.name = (String) name;
//        }
//
//        @Override
//        public Fragment getFragment() {
//            return SelectedUserFragment.getInstance(name);
//        }
    }
    public static class UsersScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return UsersFragment.getInstance(0);
        }
    }
}
