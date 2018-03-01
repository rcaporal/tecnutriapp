package com.example.caporal.tecnutriapp.ui.base.activity.presenter;

import android.app.Activity;

import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.MiniPostAdapter;

/**
 * Created by caporal on 28/02/18.
 */

public interface ProfileActivityPresenter {

    void setView(ProfileActivityPresenter.View view);
    void configAdapter();
    void getMiniPosts(Long userId, Integer page, Long timestamp);
    void getMoreMiniPosts();
    void setProfile(Profile profile);

    interface View {
        Activity getActivityFromView();
        void configViews();
        void showSnackBar(String message);
        void setAdapterOnRecycler(MiniPostAdapter adapter);
        void setIsRefreshing(boolean refreshing);
        void setIsRequesting(boolean requesting);
    }

}
