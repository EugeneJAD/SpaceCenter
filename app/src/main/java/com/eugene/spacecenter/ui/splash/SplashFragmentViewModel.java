package com.eugene.spacecenter.ui.splash;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.DrawableRes;

import javax.inject.Inject;

public class SplashFragmentViewModel extends ViewModel{

    public int backgroundResId;
    public int cosmonautResId;
    @Inject
    public SplashFragmentViewModel() {}

    public int getBackgroundResId() {return backgroundResId;}
    public void setBackgroundResId(@DrawableRes int backgroundResId) {this.backgroundResId = backgroundResId;}

    public int getCosmonautResId() {return cosmonautResId;}
    public void setCosmonautResId(int cosmonautResId) {this.cosmonautResId = cosmonautResId;}
}
