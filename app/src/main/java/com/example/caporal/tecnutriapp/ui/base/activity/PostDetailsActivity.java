package com.example.caporal.tecnutriapp.ui.base.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.LikeEvent;
import com.example.caporal.tecnutriapp.domain.entity.Meal;
import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.domain.repository.LikePersistenceRepository;
import com.example.caporal.tecnutriapp.ui.base.activity.adapters.PostAdapter;
import com.example.caporal.tecnutriapp.ui.base.activity.base.BaseActivity;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.PostDetailsActivityPresenter;
import com.example.caporal.tecnutriapp.ui.base.activity.presenter.implementation.PostDetailsImpl;
import com.example.caporal.tecnutriapp.utils.DateUtils;
import com.mopub.mobileads.MoPubView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.caporal.tecnutriapp.utils.Constants.FEED_HASH_STRING_PARCELABLE;
import static com.example.caporal.tecnutriapp.utils.Constants.IS_LIKED_STRING;
import static com.example.caporal.tecnutriapp.utils.Constants.PROFILE_STRING_PARCELABLE;

/**
 * Created by caporal on 27/02/18.
 */

public class PostDetailsActivity extends BaseActivity implements PostDetailsActivityPresenter.View{

    @BindView(R.id.food_list_recycler)
    RecyclerView postDetailRecyclerView;
    @BindView(R.id.person_name)
    TextView personNameTextView;
    @BindView(R.id.person_goal)
    TextView personGoalTextView;
    @BindView(R.id.person_profile_image)
    CircleImageView personCircleImageView;
    @BindView(R.id.post_photo)
    ImageView postImageView;
    @BindView(R.id.like_btn)
    ImageView likeButton;
    @BindView(R.id.meal_tipe_text_view)
    TextView mealTypeTextView;
    @BindView(R.id.post_timestamp)
    TextView postTimeStampTextView;
    @BindView(R.id.food_description_textview)
    TextView foodDescription;
    @BindView(R.id.food_details_layout)
    LinearLayout foodDetailsLayout;
    @BindView(R.id.energy_text_view)
    TextView energyTextView;
    @BindView(R.id.carbohydrate_text_view)
    TextView carbTextView;
    @BindView(R.id.protein_text_view)
    TextView proteinTextView;
    @BindView(R.id.fat_text_view)
    TextView fatTextView;
    @BindView(R.id.card_header_layout)
    LinearLayout cardHeaderLayout;
    @BindView(R.id.item_ad)
    MoPubView moPubView;

    private PostDetailsImpl presenter;
    private PostAdapter postAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private Profile profile;
    private boolean isLiked = false;
    private String[] mealTypeArray;
    private EventBus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_post_details);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        bus = EventBus.getDefault();

        mealTypeArray = getResources().getStringArray(R.array.meal_type_array);

        presenter = new PostDetailsImpl();
        presenter.setView(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profile = extras.getParcelable(PROFILE_STRING_PARCELABLE);
            presenter.setFeedHash(extras.getString(FEED_HASH_STRING_PARCELABLE));
            isLiked = extras.getBoolean(IS_LIKED_STRING);
        }

        linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        postDetailRecyclerView.setLayoutManager(linearLayoutManager);
        presenter.configAdapter();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRefreshing(true);

        cardHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.goToProfileActivity(profile, isLiked);
            }
        });

        if(isLiked) {
            likeButton.setImageResource(R.drawable.ic_favorite_red_24dp);
        }else {
            likeButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLiked) {
                    isLiked = false;
                    LikePersistenceRepository.saveOrUpdate(new LikeEvent(presenter.getFeedHash(), isLiked));
                    likeButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    EventBus.getDefault().post(new LikeEvent(presenter.getFeedHash(), isLiked));

                }else {
                    isLiked = true;
                    LikePersistenceRepository.saveOrUpdate(new LikeEvent(presenter.getFeedHash(), isLiked));
                    likeButton.setImageResource(R.drawable.ic_favorite_red_24dp);
                    EventBus.getDefault().post(new LikeEvent(presenter.getFeedHash(), isLiked));
                }
            }
        });

        bus.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getAdsVisibility()){
            moPubView.loadAd();
            moPubView.setVisibility(View.VISIBLE);
        }else {
            moPubView.setVisibility(View.GONE);
            moPubView.destroy();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews(Meal meal) {
        mealTypeTextView.setText(mealTypeArray[meal.getMealType()]);
        postTimeStampTextView.setText(DateUtils.getDateFormated(meal.getDate()));
        personNameTextView.setText(meal.getProfile().getName());
        personGoalTextView.setText(meal.getProfile().getGeneralGoal());
        presenter.getImage(personCircleImageView, meal.getProfile().getImage(), R.drawable.ic_account_circle_black_24dp);
        presenter.getImage(postImageView, meal.getImage(), R.drawable.ic_restaurant_black_24dp);
        configMealSummaryViews(meal);
    }

    private void configMealSummaryViews(Meal meal) {
        foodDescription.setText(R.string.food_summary);
        foodDetailsLayout.setVisibility(View.GONE);
        energyTextView.setText(String.valueOf(meal.getEnergy()));
        energyTextView.setTypeface(energyTextView.getTypeface(), Typeface.BOLD);
        carbTextView.setText(String.valueOf(meal.getCarbohydrate()));
        carbTextView.setTypeface(carbTextView.getTypeface(), Typeface.BOLD);
        proteinTextView.setText(String.valueOf(meal.getProtein()));
        proteinTextView.setTypeface(proteinTextView.getTypeface(), Typeface.BOLD);
        fatTextView.setText(String.valueOf(meal.getFat()));
        fatTextView.setTypeface(fatTextView.getTypeface(), Typeface.BOLD);
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
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
    public void setAdapterOnRecycler(PostAdapter adapter) {
        this.postAdapter = adapter;
        postDetailRecyclerView.setAdapter(postAdapter);
    }

    @Override
    public void setIsRefreshing(boolean refreshing) {
        setRefreshing(refreshing);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LikeEvent event){
        if(presenter.getFeedHash().equals(event.getFeedHash()) && isLiked != event.isLiked()){
            if(event.isLiked()){
                isLiked = event.isLiked();
                likeButton.setImageResource(R.drawable.ic_favorite_red_24dp);
            }else {
                isLiked = event.isLiked();
                likeButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            }
        }
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
}
