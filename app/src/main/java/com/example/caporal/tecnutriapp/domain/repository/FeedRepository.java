package com.example.caporal.tecnutriapp.domain.repository;

import android.util.Log;

import com.example.caporal.tecnutriapp.api.ApiClient;
import com.example.caporal.tecnutriapp.api.ApiInterface;
import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.domain.entity.Feed;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by caporal on 22/02/18.
 */

public class FeedRepository {

    private ApiInterface service;
    private static FeedRepository instance;

    public synchronized static FeedRepository getInstance(){
        if(instance == null){
            instance = new FeedRepository();
        }
        return instance;
    }

    private FeedRepository() {
        ApiClient apiClient = new ApiClient();
        this.service = apiClient.getClient().create(ApiInterface.class);
    }

    public void getFeed(Integer p, Long t, final OnGetFeed onGetFeed){

        service.getFeedItems(p, t).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.isSuccessful()){
                    Log.d("FeedRepository", "Success");
                    List<Card> items = response.body().getItems();
                    onGetFeed.onGetFeedSuccess(items, response.body().getPage(), response.body().getTimestamp());
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Log.d("FeedRepository", "Failure");
                onGetFeed.onGetFeedError(t.getMessage());
            }
        });
    }

    public interface OnGetFeed {
        void onGetFeedSuccess(List<Card> posts, int page, Long timestamp);

        void onGetFeedError(String databaseError);
    }

}
