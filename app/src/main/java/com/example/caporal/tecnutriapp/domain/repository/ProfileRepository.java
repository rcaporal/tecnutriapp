package com.example.caporal.tecnutriapp.domain.repository;

import android.util.Log;

import com.example.caporal.tecnutriapp.api.ApiClient;
import com.example.caporal.tecnutriapp.api.ApiInterface;
import com.example.caporal.tecnutriapp.domain.entity.MiniPost;
import com.example.caporal.tecnutriapp.domain.entity.MiniPostRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by caporal on 28/02/18.
 */

public class ProfileRepository {

    private ApiInterface service;
    private static ProfileRepository instance;

    public synchronized static ProfileRepository getInstance(){
        if(instance == null){
            instance = new ProfileRepository();
        }
        return instance;
    }

    private ProfileRepository() {
        ApiClient apiClient = new ApiClient();
        this.service = apiClient.getClient().create(ApiInterface.class);
    }

    public void getUserPosts(Long userId, Integer p, Long t, final OnGetUserPosts onGetUserPosts){

        service.findPostsByUserId(userId, p, t).enqueue(new Callback<MiniPostRequest>() {
            @Override
            public void onResponse(Call<MiniPostRequest> call, Response<MiniPostRequest> response) {
                if(response.isSuccessful()){
                    Log.d("ProfileRepository", "Success");
                    List<MiniPost> items = response.body().getItems();
                    onGetUserPosts.onGetUserPostsSuccess(items, response.body().getPage(), response.body().getTimestamp());
                }
            }

            @Override
            public void onFailure(Call<MiniPostRequest> call, Throwable t) {
                Log.d("ProfileRepository", "Failure");
                onGetUserPosts.onGetUserPostsError(t.getMessage());
            }
        });

    }

    public interface OnGetUserPosts {
        void onGetUserPostsSuccess(List<MiniPost> posts, int page, Long timestamp);

        void onGetUserPostsError(String databaseError);
    }
}
