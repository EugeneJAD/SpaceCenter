package com.eugene.spacecenter.ui.solar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Planet;
import com.eugene.spacecenter.databinding.FragmentSolarListBinding;
import com.eugene.spacecenter.di.Injectable;
import com.eugene.spacecenter.ui.base.AppNavigator;
import com.eugene.spacecenter.ui.base.BaseFragment;
import com.eugene.spacecenter.utils.JsonUtils;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SolarListFragment extends BaseFragment<FragmentSolarListBinding, SolarFragmentViewModel>
        implements Injectable, PlanetListAdapter.SolarObjectClickListener {

    @Inject AppNavigator appNavigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setAndBindContentView(inflater,container,savedInstanceState,R.layout.fragment_solar_list);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.solarRv.setLayoutManager(new LinearLayoutManager(getContext()));
        PlanetListAdapter adapter = new PlanetListAdapter(this);
        binding.solarRv.setAdapter(adapter);
        binding.solarRv.setNestedScrollingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.line_colored));
        binding.solarRv.addItemDecoration(dividerItemDecoration);

        if (viewModel.getSolarObjects() == null)
            viewModel.setSolarObjects(JsonUtils.getAllSolarObjects(getContext()));

        adapter.replace(viewModel.getSolarObjects());
    }

    @Override
    public void onItemClick(Planet planet) {
        appNavigator.navigateToWiki(planet.getUrl());}


}
