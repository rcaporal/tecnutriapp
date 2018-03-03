package com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.domain.entity.MiniPost;
import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.domain.repository.ProfileRepository;
import com.example.caporal.tecnutriapp.ui.base.activity.PostDetailsActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.MiniPostAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnMiniPostItemClickListener;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.ProfileActivityPresenter;
import com.example.caporal.tecnutriapp.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.caporal.tecnutriapp.utils.Constants.CARD_PARCELABLE_STRING;
import static com.example.caporal.tecnutriapp.utils.Constants.FEED_HASH_STRING_PARCELABLE;
import static com.example.caporal.tecnutriapp.utils.Constants.PROFILE_STRING_PARCELABLE;

/**
 * Created by caporal on 01/03/18.
 */

public class ProfileImpl implements ProfileActivityPresenter, OnMiniPostItemClickListener {

    private View view;
    private MiniPostAdapter adapter;
    private Profile profile;
    private Integer p = 0;
    private Long t = 0L;
    private Card card;

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void configAdapter() {
        adapter = new MiniPostAdapter(view.getActivityFromView(), this);
        getMiniPosts(profile.getId(), null, null);
        view.setAdapterOnRecycler(adapter);
    }

    @Override
    public void getMiniPosts(Long userId, Integer page, Long timestamp) {
        ProfileRepository.getInstance().getUserPosts(userId, page, timestamp, new ProfileRepository.OnGetUserPosts() {
            @Override
            public void onGetUserPostsSuccess(List<MiniPost> posts, int page, Long timestamp) {
                p = page;
                t = timestamp;
                adapter.addMiniPostList(posts, false);
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }

            @Override
            public void onGetUserPostsError(String databaseError) {
                view.showSnackBar(view.getActivityFromView().getString(R.string.mini_post_load_error));
                view.setIsRefreshing(false);
                view.setIsRequesting(false);
            }
        });
    }

    @Override
    public void getMoreMiniPosts() {
        p++;
        getMiniPosts(profile.getId(), p, t);
    }

    @Override
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private void goToPostDetailsActivity(MiniPost miniPost, Card card){
        Activity activity = view.getActivityFromView();
        Intent intent = new Intent(activity, PostDetailsActivity.class);
        intent.putExtra(FEED_HASH_STRING_PARCELABLE, miniPost.getFeedHash());
        intent.putExtra(PROFILE_STRING_PARCELABLE, miniPost.getProfile());
        intent.putExtra(CARD_PARCELABLE_STRING, card);
        activity.startActivity(intent);
    }

    public void update() {
        adapter.addMiniPostList(new ArrayList<MiniPost>(), true);
        getMiniPosts(profile.getId(), null, null);
    }

    @Override
    public void onMiniPostItemClick(MiniPost miniPost) {
        goToPostDetailsActivity(miniPost, card);
    }

    public void getImage(ImageView imageView, String url, int placeHolderDrawable) {
        Picasso.with(imageView.getContext())
                .load(url).placeholder(placeHolderDrawable)
                .into(imageView);
    }

    public int getActualPage(){
        return this.p + 1;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
