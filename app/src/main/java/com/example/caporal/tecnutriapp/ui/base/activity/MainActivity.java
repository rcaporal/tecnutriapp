package com.example.caporal.tecnutriapp.ui.base.activity;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.LikeEvent;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.FeedAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.base.BaseActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.MainActivityPresenter;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation.MainImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class MainActivity extends BaseActivity implements MainActivityPresenter.View{

    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;


    private FeedAdapter feedAdapter;
    private MainImpl presenter;
    private boolean isRequesting;
    private EventBus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
        Realm.init(getApplicationContext());

        bus = EventBus.getDefault();
        presenter = new MainImpl();
        presenter.setView(this);

        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.configAdapter();

        setRefreshing(true);

        mainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


        super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached && !isRequesting) {
                    isRequesting = true;
                    presenter.getMoreCards();
                }
            }
        });
        bus.register(this);
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
    public void setAdapterOnRecycler(FeedAdapter adapter) {
        this.feedAdapter = adapter;
        mainRecyclerView.setAdapter(feedAdapter);
    }

    @Override
    public void setIsRefreshing(boolean refreshing) {
        setRefreshing(refreshing);
    }

    @Override
    public void setIsRequesting(boolean requesting) {
        this.isRequesting = requesting;
    }


    @Override
    public void onRefresh() {
        presenter.reloadAdapterList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LikeEvent event){
        presenter.changeCardLikeStatusByHash(event.getFeedHash(), event.isLiked());
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
}
