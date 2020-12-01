package com.hw.githubclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

import com.hw.githubclient.GithubApplication;
import com.hw.githubclient.R;
import com.hw.githubclient.mvp.model.cache.room.RoomGithubUsersCache;
import com.hw.githubclient.mvp.model.entity.room.Database;
import com.hw.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.hw.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;
import com.hw.githubclient.mvp.presenter.UsersPresenter;
import com.hw.githubclient.mvp.view.UsersView;
import com.hw.githubclient.ui.BackButtonListener;
import com.hw.githubclient.ui.adapter.UserRVAdapter;
import com.hw.githubclient.ui.network.AndroidNetworkStatus;

public class UsersFragment extends MvpAppCompatFragment implements UsersView, BackButtonListener {

    private RecyclerView recyclerView;
    private UserRVAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View view;

    @InjectPresenter
    UsersPresenter usersPresenter;

    @ProvidePresenter
    UsersPresenter provideUsersPresenter() {
        IGithubUsersRepo usersRepo = new RetrofitGithubUsersRepo(GithubApplication.INSTANCE.getApi(),
                new AndroidNetworkStatus(),
                new RoomGithubUsersCache(Database.getInstance()));
        Router router = GithubApplication.getApplication().getRouter();

        return new UsersPresenter(AndroidSchedulers.mainThread(), usersRepo, router);
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
        view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_users);

        return view;
    }

    @Override
    public void init() {
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new UserRVAdapter(usersPresenter.getUsersListPresenter());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return usersPresenter.backPressed();
    }
}