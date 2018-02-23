package com.example.caporal.tecnutriapp.ui.base.activity.presenter;

import android.app.Activity;

import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.FeedAdapter;

import java.util.List;

/**
 * Created by caporal on 22/02/18.
 */

public interface MainActivityPresenter {

    void setView(MainActivityPresenter.View view);
    void configAdapter();
    void getMoreCards();

    interface View {
        Activity getActivityFromView();
        void showSnackBar(String message);
        void setAdapterOnRecycler(FeedAdapter adapter);
        void setIsRefreshing(boolean refreshing);
        void setIsRequesting(boolean requesting);
    }
}
