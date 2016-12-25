package com.eugene.spacecenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Администратор on 15.09.2016.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    static final int NUMBER_FRAGMENTS = 8;
    Context context;

    public FragmentAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new NasaFragment();
            case 1:
                return new ESAFragment();
            case 2:
                return new ISSFragment();
            case 3:
                return new HubbleFragment();
            case 4:
                return new EarthFragment();
            case 5:
                return new PlanetsInfoFragment();
            case 6:
                return new ApodFragment();
            default:
                return new SoundsFragment();
        }

    }


    @Override
    public int getCount() {
        return NUMBER_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0)
            return context.getString(R.string.nasa_page_title);
        else if(position==1)
            return context.getString(R.string.esa_page_title);
        else if (position==2)
            return context.getString(R.string.iss_page_title);
        else if (position==3)
            return context.getString(R.string.hubble_page_title);
        else if (position==4)
            return context.getString(R.string.earth_page_title);
        else if (position==5)
            return context.getString(R.string.solar_system_page_title);
        else if (position==6)
            return context.getString(R.string.apod_page_title);
        else
            return context.getString(R.string.sounds_page_title);

    }


}
