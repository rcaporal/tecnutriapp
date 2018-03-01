package com.example.caporal.tecnutriapp.ui.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.MiniPostAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.base.BaseActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.ProfileActivityPresenter;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation.ProfileImpl;
import com.example.caporal.tecnutriapp.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.caporal.tecnutriapp.utils.Constants.PROFILE_STRING_PARCELABLE;

/**
 * Created by caporal on 28/02/18.
 */

public class ProfileActivity extends BaseActivity implements ProfileActivityPresenter.View {

    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_goal)
    TextView profileGoal;
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.profile_recycler)
    RecyclerView profileRecycler;

    private ProfileImpl presenter;
    private MiniPostAdapter miniPostAdapter;
    private Profile profile;
    private boolean isRequesting = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new ProfileImpl();
        presenter.setView(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profile = extras.getParcelable(PROFILE_STRING_PARCELABLE);
            presenter.setProfile(profile);
        }

        profileRecycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        presenter.configAdapter();
        configViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRefreshing(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void configViews() {
        profileName.setText(profile.getName());
        if(profile.getGeneralGoal() != null) {
            profileGoal.setText(profile.getGeneralGoal());
        }else {
            profileGoal.setVisibility(View.GONE);
        }
        presenter.getImage(profileImage, profile.getImage(), R.drawable.ic_account_circle_black_24dp);
    }

    @Override
    public void onRefresh() {
        presenter.update();
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
    public void setAdapterOnRecycler(MiniPostAdapter adapter) {
        this.miniPostAdapter = adapter;
        profileRecycler.setAdapter(miniPostAdapter);
    }

    @Override
    public void setIsRefreshing(boolean refreshing) {
        setRefreshing(refreshing);
    }

    @Override
    public void setIsRequesting(boolean requesting) {
        this.isRequesting = requesting;
    }
}
