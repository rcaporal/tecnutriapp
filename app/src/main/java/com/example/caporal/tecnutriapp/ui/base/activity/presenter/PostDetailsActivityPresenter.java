package com.example.caporal.tecnutriapp.ui.base.activity.presenter;

import android.app.Activity;

import com.example.caporal.tecnutriapp.ui.base.activity.adapters.PostAdapter;

/**
 * Created by caporal on 27/02/18.
 */

public interface PostDetailsActivityPresenter {

    void setView(PostDetailsActivityPresenter.View view);
    void configAdapter();
    void getPost(String feedHash);

    interface View {
        Activity getActivityFromView();
        void showSnackBar(String message);
        void setAdapterOnRecycler(PostAdapter adapter);
        void setIsRefreshing(boolean refreshing);
    }
}
