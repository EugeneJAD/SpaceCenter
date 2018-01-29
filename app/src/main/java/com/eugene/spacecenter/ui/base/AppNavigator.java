package com.eugene.spacecenter.ui.base;


import android.app.AlertDialog;
import android.os.Bundle;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.ui.apod.APODtodayActivity;
import com.eugene.spacecenter.ui.apod.ApodHdFragment;
import com.eugene.spacecenter.ui.apod.ApodTodayFragment;
import com.eugene.spacecenter.ui.apods.APODsActivity;
import com.eugene.spacecenter.ui.asteroids.AsteroidsActivity;
import com.eugene.spacecenter.ui.main.MainActivity;
import com.eugene.spacecenter.ui.main.RateDialogFragment;
import com.eugene.spacecenter.ui.solar.SolarListFragment;
import com.eugene.spacecenter.ui.solar.SolarSystemActivity;
import com.eugene.spacecenter.ui.solar.WikiActivity;
import com.eugene.spacecenter.ui.sounds.SoundsActivity;
import com.eugene.spacecenter.ui.splash.SplashFragment;
import com.eugene.spacecenter.ui.twitter.TwitterActivity;

import javax.inject.Inject;

public class AppNavigator {

    private final NavigationController navigationController;

    @Inject
    public AppNavigator(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public void popUpBackStack(){navigationController.popupBackStack();}

    private Bundle putStringToBundle(String key, String value){
        Bundle args = new Bundle();
        args.putString(key,value);
        return args;
    }

    //Splash navigation

    public void navigateToSplashFragment(Bundle args) {
        navigationController.replaceFragment(R.id.fragment_container, new SplashFragment(), args);}


    public void navigateToMain() {
        navigationController.startActivity(MainActivity.class);
        navigationController.finishActivity();
    }

    //Main Navigation

    public void navigateToAPOD(String date) {
        navigationController.startActivity(APODtodayActivity.class, putStringToBundle(APODtodayActivity.KEY_DATE,date));
    }

    public void navigateToAsteroids() {navigationController.startActivity(AsteroidsActivity.class);}

    public void navigateToTwitter(int fragmentPosition) {
        Bundle args = new Bundle();
        args.putInt(TwitterActivity.KEY_EXTRA_POSITION,fragmentPosition);
        navigationController.startActivity(TwitterActivity.class,args);
    }

    public void navigateToSolar() {navigationController.startActivity(SolarSystemActivity.class);}

    public void navigateToSounds() {navigationController.startActivity(SoundsActivity.class);}

    public void navigateToTopList() {navigationController.startActivity(APODsActivity.class);}

    //Dialogs
    public void showDatePickerDialog(AlertDialog dialog) {dialog.show();}

    public void navigateToApodTodayFragment(String date) {
        navigationController.replaceFragment(R.id.fragment_container, new ApodTodayFragment(),
                putStringToBundle(APODtodayActivity.KEY_DATE,date));
    }

    public void navigateToApodHdFragment(String hdurl) {
        navigationController.replaceFragmentBackStack(R.id.fragment_container,new ApodHdFragment(),
                ApodHdFragment.class.getSimpleName(),
                putStringToBundle(ApodHdFragment.KEY_URL,hdurl),
                ApodHdFragment.class.getSimpleName());
    }

    //Solar Navigation

    public void navigateToSolarListFragment(Bundle args) {
        navigationController.replaceFragment(R.id.fragment_container,new SolarListFragment(), args);
    }

    public void navigateToWiki(String url) {
        navigationController.startActivity(WikiActivity.class,putStringToBundle(WikiActivity.KEY_WIKI_URL,url));
    }

    public void showRateDialog(){navigationController.showDialog(new RateDialogFragment());}
}
