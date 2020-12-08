package com.hw.githubclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hw.githubclient.GithubApplication;
import com.hw.githubclient.R;
import com.hw.githubclient.mvp.model.entity.GithubUser;
import com.hw.githubclient.mvp.presenter.UserPresenter;
import com.hw.githubclient.mvp.view.UserView;
import com.hw.githubclient.ui.BackButtonListener;
import com.hw.githubclient.ui.adapter.RepositoriesRVAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class UserFragment extends MvpAppCompatFragment implements UserView, BackButtonListener {
    private static final String USER_ARG = "user";

    private RecyclerView recyclerView;
    private RepositoriesRVAdapter rvAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private View view;

    @InjectPresenter
    UserPresenter presenter;

    @ProvidePresenter
    UserPresenter provideUserPresenter() {
        final GithubUser user = getArguments().getParcelable(USER_ARG);

        return new UserPresenter(user);
    }

    public static UserFragment newInstance(GithubUser user) {
        UserFragment fragment = new UserFragment();

        Bundle args = new Bundle();
        args.putParcelable(USER_ARG, user);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_repositories);

        return view;
    }

    @Override
    public void init() {
        layoutManager = new LinearLayoutManager(view.getContext());

        rvAdapter = new RepositoriesRVAdapter(presenter.getPresenter());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rvAdapter);
    }

    @Override
    public void updateList() {
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void release() {
        GithubApplication.INSTANCE.releaseRepositorySubcomponent();
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}
