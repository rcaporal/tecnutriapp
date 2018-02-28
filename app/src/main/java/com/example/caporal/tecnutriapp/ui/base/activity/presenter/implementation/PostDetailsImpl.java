package com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation;

import android.widget.ImageView;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.Food;
import com.example.caporal.tecnutriapp.domain.entity.Meal;
import com.example.caporal.tecnutriapp.domain.repository.PostDetailsRepository;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.PostAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.PostDetailsActivityPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
                view.initViews(meal);
                adapter.setFoodList(meal.getFoods());
                adapter.notifyDataSetChanged();
                view.setIsRefreshing(false);
            }

            @Override
            public void onGetPostDetailsError(String databaseError) {
                view.showSnackBar(view.getActivityFromView().getString(R.string.post_load_error));
                view.setIsRefreshing(false);
            }
        });
    }

    public void setFeedHash(String feedHash) {
        this.feedHash = feedHash;
    }

    public void refresh() {
        adapter.setFoodList(new ArrayList<Food>());
        getPost(feedHash);
    }

    public void getImage(ImageView imageView, String url, int placeHolderDrawable) {
        Picasso.with(imageView.getContext())
                .load(url).placeholder(placeHolderDrawable)
                .into(imageView);
    }
}
