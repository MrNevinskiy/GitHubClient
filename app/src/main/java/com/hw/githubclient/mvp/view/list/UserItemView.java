package com.hw.githubclient.mvp.view.list;

public interface UserItemView extends IItemView {
    void setLogin(String text);
    void loadAvatar(String url);
}
