package com.hw.githubclient.mvp.presenter.list;

import com.hw.githubclient.mvp.view.list.IItemView;

public interface IListPresenter<V extends IItemView> {
    void onItemClick(V view);
    void bindView(V view);
    int getCount();
}
