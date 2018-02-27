package com.example.caporal.tecnutriapp.domain.repository;

import android.util.Log;

import com.example.caporal.tecnutriapp.api.ApiClient;
import com.example.caporal.tecnutriapp.api.ApiInterface;
import com.example.caporal.tecnutriapp.domain.entity.Meal;
import com.example.caporal.tecnutriapp.domain.entity.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by caporal on 27/02/18.
 */

public class PostDetailsRepository {

    private ApiInterface service;
    private static PostDetailsRepository instance;

    public synchronized static PostDetailsRepository getInstance(){
        if(instance == null){
            instance = new PostDetailsRepository();
        }
        return instance;
    }

    private PostDetailsRepository() {
        ApiClient apiClient = new ApiClient();
        this.service = apiClient.getClient().create(ApiInterface.class);
    }

    public void getPost(String feedHash, final OnGetPostDetails onGetPostDetails){

        service.getPostDetailsByHash(feedHash).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Log.d("PostDetailsRepository", "Success");
                    Meal meal = response.body().getItem();
                    onGetPostDetails.onGetPostDetailsSuccess(meal);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("PostDetailsRepository", "Failure");
                onGetPostDetails.onGetPostDetailsError(t.getMessage());
            }
        });
    }

    public interface OnGetPostDetails {
        void onGetPostDetailsSuccess(Meal meal);

        void onGetPostDetailsError(String databaseError);
    }
}
