package com.example.caporal.tecnutriapp.api;

import com.example.caporal.tecnutriapp.domain.entity.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by caporal on 22/02/18.
 */

public interface ApiInterface {

    @GET("feed")
    Call<Feed> getFeedItems(@Query("p") Integer p, @Query("t") Long t);

}
