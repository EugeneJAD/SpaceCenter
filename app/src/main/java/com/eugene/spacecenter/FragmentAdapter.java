package com.eugene.spacecenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Администратор on 15.09.2016.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_FRAGMENTS = 5;
    public static final int NASA_FRAGMENT_POSITION = 0;
    public static final int ESA_FRAGMENT_POSITION = 1;
    public static final int ISS_FRAGMENT_POSITION = 2;
    public static final int SPACEX_FRAGMENT_POSITION = 3;
    public static final int HUBBLE_FRAGMENT_POSITION = 4;
    private Context context;

    public FragmentAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case NASA_FRAGMENT_POSITION:
                return newTwitterFragment("NASA");
            case ESA_FRAGMENT_POSITION:
                return newTwitterFragment("esa");
            case ISS_FRAGMENT_POSITION:
                return newTwitterFragment("Space_Station");
            case SPACEX_FRAGMENT_POSITION:
                return newTwitterFragment("SpaceX");
            case HUBBLE_FRAGMENT_POSITION:
                return newTwitterFragment("NASA_Hubble");
            default:
                return newTwitterFragment("NASA");
        }

    }


    @Override
    public int getCount() {
        return NUMBER_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==NASA_FRAGMENT_POSITION)
            return context.getString(R.string.nasa_page_title);
        else if(position==ESA_FRAGMENT_POSITION)
            return context.getString(R.string.esa_page_title);
        else if (position==ISS_FRAGMENT_POSITION)
            return context.getString(R.string.iss_page_title);
        else if (position==SPACEX_FRAGMENT_POSITION)
            return context.getString(R.string.spacex_page_title);
        else if (position==HUBBLE_FRAGMENT_POSITION)
            return context.getString(R.string.hubble_page_title);
        else
            return context.getString(R.string.nasa_page_title);

    }

    private TwitterFragment newTwitterFragment(String s){

        TwitterFragment twitterFragment = new TwitterFragment();
        Bundle args = new Bundle();
        args.putString("twitterAccount",s);
        twitterFragment.setArguments(args);

        return twitterFragment;
    }


}
