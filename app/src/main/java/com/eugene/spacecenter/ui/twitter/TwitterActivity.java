package com.eugene.spacecenter.ui.twitter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.eugene.spacecenter.R;

public class TwitterActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_POSITION = "position";

    FragmentAdapter mAdapter;
    ViewPager mPager;
    private int positionStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        if(getIntent().getExtras()!=null)
            positionStart = getIntent().getExtras().getInt(KEY_EXTRA_POSITION);
        else
            positionStart=0;

        mPager=(ViewPager)findViewById(R.id.pager);
        mAdapter=new FragmentAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(positionStart);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);

    }
}
