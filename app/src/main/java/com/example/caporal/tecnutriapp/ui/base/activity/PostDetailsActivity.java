package com.example.caporal.tecnutriapp.ui.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.PostAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.base.BaseActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.PostDetailsActivityPresenter;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation.PostDetailsImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.caporal.tecnutriapp.utils.Constants.FEED_HASH_STRING_PARCELABLE;

/**
 * Created by caporal on 27/02/18.
 */

public class PostDetailsActivity extends BaseActivity implements PostDetailsActivityPresenter.View{

    @BindView(R.id.food_list_recycler)
    RecyclerView postDetailRecyclerView;

    private PostDetailsImpl presenter;
    private PostAdapter postAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_post_details);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new PostDetailsImpl();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            presenter.setFeedHash(extras.getString(FEED_HASH_STRING_PARCELABLE));
        }

        presenter.setView(this);
        presenter.configAdapter();

        setRefreshing(true);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public Activity getActivityFromView() {
        return this;
    }

    @Override
    public void showSnackBar(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapterOnRecycler(PostAdapter adapter) {
        this.postAdapter = adapter;
        postDetailRecyclerView.setAdapter(postAdapter);
    }

    @Override
    public void setIsRefreshing(boolean refreshing) {
        setRefreshing(refreshing);
    }
}
