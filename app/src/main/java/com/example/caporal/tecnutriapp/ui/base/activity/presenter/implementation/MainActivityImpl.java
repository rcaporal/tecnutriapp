package com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation;

import com.example.caporal.tecnutriapp.ui.base.activity.presenter.MainActivityPresenter;

/**
 * Created by caporal on 22/02/18.
 */

public class MainActivityImpl implements MainActivityPresenter {


    private View view;

    @Override
    public void setView(View view) {
        this.view = view;
    }
}
