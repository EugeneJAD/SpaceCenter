package com.eugene.spacecenter.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Category;
import com.eugene.spacecenter.databinding.ActivityMainBinding;
import com.eugene.spacecenter.ui.base.AppNavigator;
import com.eugene.spacecenter.ui.base.BaseActivity;
import com.eugene.spacecenter.ui.base.GlideApp;
import com.eugene.spacecenter.ui.twitter.FragmentAdapter;
import com.eugene.spacecenter.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainActivityViewModel>
        implements HasSupportFragmentInjector, CategoryRecyclerAdapter.MainMenuItemClickCallback{


    @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject AppNavigator appNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAndBindContentView(R.layout.activity_main);


        setViews();
    }

    private void setViews() {

        binding.mainCollapsingToolbar.setTitle(getString(R.string.app_name));

        GlideApp.with(this)
                .load(R.drawable.main_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.mainImage);

        binding.mainRv.setLayoutManager(new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false));
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(this);
        binding.mainRv.setHasFixedSize(true);
        binding.mainRv.setAdapter(adapter);

        if(viewModel.getCategories()==null)
            viewModel.setCategories(getCategoryList());

        adapter.replace(viewModel.getCategories());

    }

    private List<Category> getCategoryList() {

        ArrayList<Category> categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(0,R.drawable.ic_planet_1,getResources().getColor(R.color.category_back_1), R.string.apod_categoty_title));
        categoryArrayList.add(new Category(1,R.drawable.ic_asteroids,getResources().getColor(R.color.category_back_2), R.string.asteroids_categoty_title));
        categoryArrayList.add(new Category(2,R.drawable.ic_esa,getResources().getColor(R.color.category_back_3), R.string.esa_categoty_title));
        categoryArrayList.add(new Category(3,R.drawable.ic_telescope,getResources().getColor(R.color.category_back_4), R.string.hubble_categoty_title));
        categoryArrayList.add(new Category(4,R.drawable.ic_iss,getResources().getColor(R.color.category_back_1), R.string.iss_categoty_title));
        categoryArrayList.add(new Category(5,R.drawable.ic_nasa,getResources().getColor(R.color.category_back_2), R.string.nasa_categoty_title));
        categoryArrayList.add(new Category(6,R.drawable.ic_earth,getResources().getColor(R.color.category_back_3), R.string.solar_system_page_title));
        categoryArrayList.add(new Category(7,R.drawable.ic_alien,getResources().getColor(R.color.category_back_4), R.string.sounds_categoty_title));
        categoryArrayList.add(new Category(8,R.drawable.ic_spacex,getResources().getColor(R.color.category_back_1), R.string.spacex_categoty_title));
        categoryArrayList.add(new Category(9,R.drawable.ic_planet_2,getResources().getColor(R.color.category_back_2), R.string.apod_list_categoty_title));
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


}
