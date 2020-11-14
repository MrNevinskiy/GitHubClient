package com.hw.githubclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hw.githubclient.R;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.presenter.SelectedUserPresenter;
import com.hw.githubclient.mvp.view.SelectedUserView;
import com.hw.githubclient.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class SelectedUserFragment extends MvpAppCompatFragment implements SelectedUserView, BackButtonListener {

    @InjectPresenter
    SelectedUserPresenter selectedUserPresenter;

    private View view;
    private TextView userName;
    private GithubUser user = null;
//    private String name;

    public static SelectedUserFragment getInstance(GithubUser user) {
        SelectedUserFragment fragment = new SelectedUserFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("key", user);

        fragment.setArguments(bundle);
        return fragment;
    }

//    public static SelectedUserFragment getInstance(String name) {
//        SelectedUserFragment fragment = new SelectedUserFragment();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("key", name);
//
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_selected_user, container, false);
        user = getArguments().getParcelable("key");
//        name = getArguments().getString("key");
        return view;
    }

    @Override
    public boolean backPressed() {
        return selectedUserPresenter.backPressed();
    }

    @Override
    public void init() {
       userName = getView().findViewById(R.id.usre_name);
        userName.setText(user.getLogin());
//       userName.setText(name);
    }
}
