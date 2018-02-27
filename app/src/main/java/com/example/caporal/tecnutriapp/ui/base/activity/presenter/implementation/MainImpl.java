package com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation;


import android.app.Activity;
import android.content.Intent;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.domain.repository.FeedRepository;
import com.example.caporal.tecnutriapp.ui.base.activity.PostDetailsActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.FeedAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnItemProfileClickListener;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnPostBodyClickListener;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.MainActivityPresenter;
import com.example.caporal.tecnutriapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caporal on 22/02/18.
 */

public class MainImpl implements MainActivityPresenter {


    private View view;
    private FeedAdapter adapter;

    private Integer p = 0;
    private Long t = 0L;

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
        }, new OnPostBodyClickListener() {
            @Override
            public void onPostBodyClick(String feedHash) {
                goToPostDetailsActivity(feedHash);
            }
        });

        getCards(null, null);

        view.setAdapterOnRecycler(adapter);
    }

    @Override
    public void getCards(Integer page, Long timestamp) {
        FeedRepository.getInstance().getFeed(page, timestamp, new FeedRepository.OnGetFeed() {
            @Override
            public void onGetFeedSuccess(List<Card> posts, int page, Long timestamp) {
                p = page;
                t = timestamp;
                adapter.addCardsToListContent(posts, false);
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }

            @Override
            public void onGetFeedError(String databaseError) {
                view.showSnackBar(view.getActivityFromView().getString(R.string.feed_load_error));
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }
        });
    }

    @Override
    public void getMoreCards() {
        p++;
        getCards(p, t);
    }

    public void reloadAdapterList() {
        adapter.addCardsToListContent(new ArrayList<Card>(), true);
        getCards(null, null);
    }

    private void goToPostDetailsActivity(String feedHash){
        Activity activity = view.getActivityFromView();
        Intent intent = new Intent(activity, PostDetailsActivity.class);
        intent.putExtra(Constants.FEED_HASH_STRING_PARCELABLE, feedHash);
        activity.startActivity(intent);
    }
}
