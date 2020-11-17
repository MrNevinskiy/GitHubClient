package com.hw.githubclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.hw.githubclient.mvp.presenter.MainPresenter;
import com.hw.githubclient.mvp.view.MainView;
import com.hw.githubclient.rxtest.BackPressure;
import com.hw.githubclient.rxtest.Creation;
import com.hw.githubclient.rxtest.Operators;
import com.hw.githubclient.rxtest.Sources;
import com.hw.githubclient.ui.BackButtonListener;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    private NavigatorHolder navigatorHolder = GithubApplication.getApplication().getNavigatorHolder();
    private Navigator navigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.container);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creation.exec();
        //Operators.exec();
        //Sources.exec();
        //BackPressure.exec();
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();

        navigatorHolder.removeNavigator();
    }

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BackButtonListener && ((BackButtonListener)fragment).backPressed()) {
                return;
            }
        }
        presenter.backClicked();
    }
}