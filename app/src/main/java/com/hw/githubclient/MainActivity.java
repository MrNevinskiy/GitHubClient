package com.hw.githubclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hw.githubclient.mvp.presenter.Presenter;
import com.hw.githubclient.mvp.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    private Presenter presenter;

    public Button buttonCounterOne;
    public Button buttonCounterTwo;
    public Button buttonCounterThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this);

        buttonCounterOne = findViewById(R.id.btn_counter1);
        buttonCounterTwo = findViewById(R.id.btn_counter2);
        buttonCounterThree = findViewById(R.id.btn_counter3);

        buttonCounterOne.setOnClickListener(this);
        buttonCounterTwo.setOnClickListener(this);
        buttonCounterThree.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_counter1:
                presenter.setButtonOneText();
                break;
            case R.id.btn_counter2:
                presenter.setButtonTwoText();
                break;
            case R.id.btn_counter3:
                presenter.setButtonThreeText();
                break;
        }
    }

    @Override
    public void setButtonOneText(String text) {
        buttonCounterOne.setText(text);
    }

    @Override
    public void setButtonTwoText(String text) {
        buttonCounterTwo.setText(text);
    }

    @Override
    public void setButtonThreeText(String text) {
        buttonCounterThree.setText(text);
    }
}