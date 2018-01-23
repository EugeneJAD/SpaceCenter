package com.eugene.spacecenter.ui.splash;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.databinding.FragmentSplashBinding;
import com.eugene.spacecenter.di.Injectable;
import com.eugene.spacecenter.ui.base.AppNavigator;
import com.eugene.spacecenter.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends BaseFragment<FragmentSplashBinding,SplashFragmentViewModel> implements Injectable{

    @Inject AppNavigator appNavigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setAndBindContentView(inflater,container,savedInstanceState,R.layout.fragment_splash);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState==null) {
            viewModel.setBackgroundResId(R.drawable.splash_back);
            viewModel.setCosmonautResId(R.drawable.ic_cosmonaut);
        }

        binding.setVm(viewModel);

        animateViews();
    }

    private void animateViews() {

        ScaleAnimation scaleAnimation = new ScaleAnimation(0f,1f,0f,1f);
        RotateAnimation rotateAnimation = new RotateAnimation(0f,360f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setDuration(2500);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {appNavigator.navigateToMain();}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        binding.cosmonaut.startAnimation(animationSet);

    }


}
