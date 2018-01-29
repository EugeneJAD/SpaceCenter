package com.eugene.spacecenter.ui.main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Category;
import com.eugene.spacecenter.databinding.ActivityMainBinding;
import com.eugene.spacecenter.ui.base.AppNavigator;
import com.eugene.spacecenter.ui.base.BaseActivity;
import com.eugene.spacecenter.ui.base.GlideApp;
import com.eugene.spacecenter.ui.twitter.FragmentAdapter;
import com.eugene.spacecenter.utils.AppConstants;
import com.eugene.spacecenter.utils.DateUtils;
import com.eugene.spacecenter.utils.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainActivityViewModel>
        implements HasSupportFragmentInjector, CategoryRecyclerAdapter.MainMenuItemClickListener,
        RateDialogFragment.PositiveButtonClickListener{


    @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject AppNavigator appNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAndBindContentView(R.layout.activity_main);

        setSupportActionBar(binding.toolbar);

        setViews();
        runLayoutAnimation();
    }

    private void setViews() {

        binding.mainCollapsingToolbar.setTitle(getString(R.string.app_name));

        GlideApp.with(this)
                .load(R.drawable.main_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.mainImage);

        binding.mainRv.setLayoutManager(new GridLayoutManager(this,2));
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(this);
        binding.mainRv.setAdapter(adapter);

        if(viewModel.getCategories()==null)
            viewModel.setCategories(getCategoryList());

        adapter.replace(viewModel.getCategories());
    }

    private void runLayoutAnimation() {
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.grid_layout_animation_from_bottom);
        binding.mainRv.setLayoutAnimation(controller);
    }

    private List<Category> getCategoryList() {

        ArrayList<Category> categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(0,R.drawable.ic_planet_1,getRippleDrawable(R.color.category_back_1, R.color.cyan_800), R.string.apod_categoty_title));
        categoryArrayList.add(new Category(1,R.drawable.ic_asteroids,getRippleDrawable(R.color.category_back_2, R.color.pink_800), R.string.asteroids_categoty_title));
        categoryArrayList.add(new Category(2,R.drawable.ic_esa,getRippleDrawable(R.color.category_back_3, R.color.purple_700), R.string.esa_categoty_title));
        categoryArrayList.add(new Category(3,R.drawable.ic_telescope,getRippleDrawable(R.color.category_back_4, R.color.green_700), R.string.hubble_categoty_title));
        categoryArrayList.add(new Category(4,R.drawable.ic_iss,getRippleDrawable(R.color.category_back_1, R.color.cyan_800), R.string.iss_categoty_title));
        categoryArrayList.add(new Category(5,R.drawable.ic_nasa,getRippleDrawable(R.color.category_back_2,R.color.pink_800), R.string.nasa_categoty_title));
        categoryArrayList.add(new Category(6,R.drawable.ic_earth,getRippleDrawable(R.color.category_back_3, R.color.purple_700), R.string.solar_system_page_title));
        categoryArrayList.add(new Category(7,R.drawable.ic_alien,getRippleDrawable(R.color.category_back_4, R.color.green_700), R.string.sounds_categoty_title));
        categoryArrayList.add(new Category(8,R.drawable.ic_spacex,getRippleDrawable(R.color.category_back_1, R.color.cyan_800), R.string.spacex_categoty_title));
        categoryArrayList.add(new Category(9,R.drawable.ic_planet_2,getRippleDrawable(R.color.category_back_2,R.color.pink_800), R.string.apod_list_categoty_title));
        return categoryArrayList;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onItemClick(int categoryId) {
        navigateTo(categoryId);
    }

    private void navigateTo(int categoryId) {

        switch (categoryId){
            case 0:
                appNavigator.navigateToAPOD(DateUtils.getFormattedCurrentDate());
                break;
            case 1:
                appNavigator.navigateToAsteroids();
                break;
            case 2:
                appNavigator.navigateToTwitter(FragmentAdapter.ESA_FRAGMENT_POSITION);
                break;
            case 3:
                appNavigator.navigateToTwitter(FragmentAdapter.HUBBLE_FRAGMENT_POSITION);
                break;
            case 4:
                appNavigator.navigateToTwitter(FragmentAdapter.ISS_FRAGMENT_POSITION);
                break;
            case 5:
                appNavigator.navigateToTwitter(FragmentAdapter.NASA_FRAGMENT_POSITION);
                break;
            case 6:
                appNavigator.navigateToSolar();
                break;
            case 7:
                appNavigator.navigateToSounds();
                break;
            case 8:
                appNavigator.navigateToTwitter(FragmentAdapter.SPACEX_FRAGMENT_POSITION);
                break;
            case 9:
                appNavigator.navigateToTopList();
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
        shareIntent.setType("text/plain");

        mShareActionProvider.setShareIntent(shareIntent);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_rate:
                appNavigator.showRateDialog();
                break;
            case R.id.action_send:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                if (sendIntent.resolveActivity(getPackageManager())!=null) {
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.email_address)});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject_text));
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } else
                    SnackbarUtils.showSnackbar(binding.getRoot(),getString(R.string.email_send_app_not_found));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public Drawable getRippleDrawable(int color, int tintColor){
        return new RippleDrawable(ColorStateList.valueOf(getResources().getColor(tintColor)),
                new ColorDrawable(getResources().getColor(color)),null);
    }

    @Override
    public void onRateButtonClick() {
        Intent rateIntent = new Intent();
        rateIntent.setAction(Intent.ACTION_VIEW);
        if (rateIntent.resolveActivity(getPackageManager())!=null) {
            rateIntent.setData(Uri.parse(AppConstants.APP_GOOGLE_STORE_URL));
            startActivity(rateIntent);
        } else
            SnackbarUtils.showSnackbar(binding.getRoot(),getString(R.string.google_play_not_found));
    }
}


