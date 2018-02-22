package com.example.caporal.tecnutriapp.ui.base.activity.presenter;

import android.app.Activity;

/**
 * Created by caporal on 22/02/18.
 */

public interface MainActivityPresenter {

    void setView(MainActivityPresenter.View view);

    interface View {
        Activity getActivityFromView();
        void showSnackBar(String message);
    }
}
