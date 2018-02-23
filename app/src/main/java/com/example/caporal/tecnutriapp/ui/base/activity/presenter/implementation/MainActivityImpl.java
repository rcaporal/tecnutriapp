package com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation;


import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.domain.repository.FeedRepository;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.FeedAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnItemProfileClickListener;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caporal on 22/02/18.
 */

public class MainActivityImpl implements MainActivityPresenter {


    private View view;
    private FeedAdapter adapter;

    private int p;
    private Long t;

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void configAdapter() {
        adapter = new FeedAdapter(view.getActivityFromView(), new OnItemProfileClickListener() {
            @Override
            public void onItemProfileClick(Profile profile) {
                view.showSnackBar(profile.getName());
            }
        });

        FeedRepository.getInstance().getFeed(null, null, new FeedRepository.OnGetFeed() {
            @Override
            public void onGetFeedSuccess(List<Card> posts, int page, Long timestamp) {
                incrementP(p);
                t = timestamp;
                adapter.setCardListContent(posts, true);
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }

            @Override
            public void onGetFeedError(String databaseError) {

            }
        });

        view.setAdapterOnRecycler(adapter);
    }

    @Override
    public void getMoreCards() {
        FeedRepository.getInstance().getFeed(p, t, new FeedRepository.OnGetFeed() {
            @Override
            public void onGetFeedSuccess(List<Card> posts, int page, Long timestamp) {
                incrementP(p);
                t = timestamp;
                adapter.setCardListContent(posts, false);
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }

            @Override
            public void onGetFeedError(String databaseError) {
                view.showSnackBar(databaseError);
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }
        });
    }

    public void reloadAdapterList() {
        adapter.setCardListContent(new ArrayList<Card>(), true);
        FeedRepository.getInstance().getFeed(null, null, new FeedRepository.OnGetFeed() {
            @Override
            public void onGetFeedSuccess(List<Card> posts, int page, Long timestamp) {
                incrementP(p);
                t = timestamp;
                adapter.setCardListContent(posts, true);
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }

            @Override
            public void onGetFeedError(String databaseError) {
                view.showSnackBar(databaseError);
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }
        });


    }

    public void incrementP(int p) {
        this.p = p + 1;
    }
}
