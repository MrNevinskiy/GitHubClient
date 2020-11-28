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
import com.hw.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.hw.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;
import com.hw.githubclient.mvp.presenter.UserPresenter;
import com.hw.githubclient.mvp.presenter.UsersPresenter;
import com.hw.githubclient.mvp.view.UserView;
import com.hw.githubclient.mvp.view.UsersView;
import com.hw.githubclient.ui.BackButtonListener;
import com.hw.githubclient.ui.adapter.RepositoriesRVAdapter;
import com.hw.githubclient.ui.adapter.UserRVAdapter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

public class UsersFragment extends MvpAppCompatFragment implements UserView, BackButtonListener {
    private static final String USER_ARG = "user";

    private RecyclerView mRecyclerView;
    private RepositoriesRVAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private View mView;

    @InjectPresenter
    UserPresenter mPresenter;

    @ProvidePresenter
    UserPresenter provideUserPresenter() {
        final GithubUser user = getArguments().getParcelable(USER_ARG);

        IGithubRepositoriesRepo githubRepositoriesRepo = new RetrofitGithubRepositoriesRepo(GithubApplication.INSTANCE.getApi());

        Router router = GithubApplication.getApplication().getRouter();

        return new UserPresenter(user, AndroidSchedulers.mainThread(), githubRepositoriesRepo, router);
    }


    public static UsersFragment getInstance(int data) {
        UsersFragment fragment = new UsersFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("key", data);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);

        mRecyclerView = (RecyclerView)mView.findViewById(R.id.rv_repositories);

        return mView;
    }

    @Override
    public void init() {
        mLayoutManager = new LinearLayoutManager(mView.getContext());

        mAdapter = new RepositoriesRVAdapter(mPresenter.getPresenter());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }
}
