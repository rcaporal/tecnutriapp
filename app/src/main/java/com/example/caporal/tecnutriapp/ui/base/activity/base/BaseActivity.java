package com.example.caporal.tecnutriapp.ui.base.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.caporal.tecnutriapp.BuildConfig;
import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.utils.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mopub.mobileads.MoPubView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.caporal.tecnutriapp.utils.Constants.MOPUB_ADS_VISIBILITY;

/**
 * Created by caporal on 18/02/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.item_ad)
    MoPubView moPubView;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Log.d("BaseActivity", "isWorking");

        moPubView.setAdUnitId(Constants.AD_UNIT_ID);

        // Get Remote Config instance.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mFirebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build());

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put(MOPUB_ADS_VISIBILITY, true);

        // [START set_default_values]
        mFirebaseRemoteConfig.setDefaults(defaults);
        Task<Void> fetch = mFirebaseRemoteConfig.fetch(0);
        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mFirebaseRemoteConfig.activateFetched();
                configAdsVisibility();
            }
        });
    }

    private void configAdsVisibility() {
        if (mFirebaseRemoteConfig.getBoolean(MOPUB_ADS_VISIBILITY)) {
            moPubView.loadAd();
            moPubView.setVisibility(View.VISIBLE);
        } else {
            moPubView.setVisibility(View.GONE);
            moPubView.destroy();
        }
    }

    public void setRefreshing(boolean isRefreshing){
        this.swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moPubView.destroy();
    }
}
