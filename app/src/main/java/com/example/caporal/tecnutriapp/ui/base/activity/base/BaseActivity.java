package com.example.caporal.tecnutriapp.ui.base.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.caporal.tecnutriapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caporal on 18/02/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Log.d("BaseActivity", "isWorking");
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void setRefreshing(boolean isRefreshing){
        this.swipeRefreshLayout.setRefreshing(isRefreshing);
    }
}
