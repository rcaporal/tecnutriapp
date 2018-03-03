package com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation;


import android.app.Activity;
import android.content.Intent;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.domain.entity.LikeEvent;
import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.domain.repository.FeedRepository;
import com.example.caporal.tecnutriapp.domain.repository.LikePersistenceRepository;
import com.example.caporal.tecnutriapp.ui.base.activity.PostDetailsActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.ProfileActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.FeedAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnItemProfileClickListener;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnPostBodyClickListener;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.MainActivityPresenter;
import com.example.caporal.tecnutriapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.caporal.tecnutriapp.utils.Constants.CARD_PARCELABLE_STRING;
import static com.example.caporal.tecnutriapp.utils.Constants.FEED_HASH_STRING_PARCELABLE;
import static com.example.caporal.tecnutriapp.utils.Constants.PROFILE_STRING_PARCELABLE;

/**
 * Created by caporal on 22/02/18.
 */

public class MainImpl implements MainActivityPresenter, OnItemProfileClickListener, OnPostBodyClickListener {


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
        adapter = new FeedAdapter(view.getActivityFromView(), this, this);

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

    private void goToPostDetailsActivity(Card card){
        Activity activity = view.getActivityFromView();
        Intent intent = new Intent(activity, PostDetailsActivity.class);
        intent.putExtra(FEED_HASH_STRING_PARCELABLE, card.getFeedHash());
        intent.putExtra(PROFILE_STRING_PARCELABLE, card.getProfile());
        intent.putExtra(CARD_PARCELABLE_STRING, card);
        activity.startActivity(intent);
    }

    private void goToProfileActivity(Profile profile, Card card){
        Activity activity = view.getActivityFromView();
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(PROFILE_STRING_PARCELABLE, profile);
        intent.putExtra(CARD_PARCELABLE_STRING, card);
        activity.startActivity(intent);
    }

    @Override
    public void onItemProfileClick(Profile profile, Card card) {
        goToProfileActivity(profile, card);
    }

    @Override
    public void onPostBodyClick(Card card) {
        goToPostDetailsActivity(card);
    }

    public void changeCardLikeStatusByHash(String feedHash, boolean liked) {
        List<Card> cardList = adapter.getCardsList();
        for(int i = 0; i < cardList.size(); i++){
            if(cardList.get(i).getFeedHash().equals(feedHash)){
                if(cardList.get(i).isLiked() != liked){
                    Card card = cardList.get(i);
                    card.setLiked(liked);
                    adapter.getCardsList().remove(i);
                    adapter.getCardsList().add(i,card);
                    adapter.notifyItemChanged(i);
                }
                break;
            }
        }
    }
}
