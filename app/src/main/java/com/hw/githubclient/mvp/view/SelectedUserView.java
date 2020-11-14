package com.hw.githubclient.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface SelectedUserView extends MvpView {
    void init();
}
