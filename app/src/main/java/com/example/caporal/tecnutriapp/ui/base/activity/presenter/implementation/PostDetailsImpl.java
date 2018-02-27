package com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation;

import com.example.caporal.tecnutriapp.domain.entity.Meal;
import com.example.caporal.tecnutriapp.domain.repository.PostDetailsRepository;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.PostAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.PostDetailsActivityPresenter;

/**
 * Created by caporal on 27/02/18.
 */

public class PostDetailsImpl implements PostDetailsActivityPresenter {

    private View view;
    private PostAdapter adapter;
    private String feedHash;

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void configAdapter() {
        adapter = new PostAdapter(view.getActivityFromView());
        getPost(feedHash);
        view.setAdapterOnRecycler(adapter);
    }

    @Override
    public void getPost(String feedHash) {
        PostDetailsRepository.getInstance().getPost(feedHash, new PostDetailsRepository.OnGetPostDetails() {
            @Override
            public void onGetPostDetailsSuccess(Meal meal) {
                adapter.setFoodList(meal.getFoods());
            }

            @Override
            public void onGetPostDetailsError(String databaseError) {

            }
        });
    }

    public void setFeedHash(String feedHash) {
        this.feedHash = feedHash;
    }
}
