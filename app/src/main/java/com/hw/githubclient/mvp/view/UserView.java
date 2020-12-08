package com.hw.githubclient.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface UserView extends MvpView {
    void init();
    void updateList();
    void release();
}
