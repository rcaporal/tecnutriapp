package com.example.caporal.tecnutriapp.ui.base.activity;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.FeedAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.base.BaseActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.MainActivityPresenter;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation.MainActivityImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainActivityPresenter.View{

    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;

    private FeedAdapter feedAdapter;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new MainActivityImpl();
        presenter.setView(this);

        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        feedAdapter = new FeedAdapter();
        mainRecyclerView.setAdapter(feedAdapter);
    }

    @Override
    public Activity getActivityFromView() {
        return this;
    }

    @Override
    public void showSnackBar(String message) {

    }
}
