package com.hw.githubclient.mvp.presenter;

import com.hw.githubclient.mvp.model.Model;
import com.hw.githubclient.mvp.view.MainView;

public class Presenter {

    private MainView view;
    private Model model = new Model();

    public Presenter (MainView view) {
        this.view = view;
    }

    public void setButtonOneText(){
        view.setButtonOneText(String.valueOf(model.next(0)));
    }
    public void setButtonTwoText(){
        view.setButtonTwoText(String.valueOf(model.next(1)));
    }
    public void setButtonThreeText(){
        view.setButtonThreeText(String.valueOf(model.next(2)));
    }

}
