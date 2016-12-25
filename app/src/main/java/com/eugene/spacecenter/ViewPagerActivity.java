package com.eugene.spacecenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ViewPagerActivity extends AppCompatActivity {

    FragmentAdapter mAdapter;
    ViewPager mPager;
    private int positionStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        positionStart = getIntent().getExtras().getInt("position");

        mPager=(ViewPager)findViewById(R.id.pager);
        mAdapter=new FragmentAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(positionStart);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);

    }
}
