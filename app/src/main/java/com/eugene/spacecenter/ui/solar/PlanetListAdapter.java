package com.eugene.spacecenter.ui.solar;


import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Planet;
import com.eugene.spacecenter.databinding.ListItemSolarBinding;
import com.eugene.spacecenter.ui.base.DataBoundListAdapter;

public class PlanetListAdapter extends DataBoundListAdapter<Planet,ListItemSolarBinding>{

    private SolarObjectClickListener clickCallback;


    public PlanetListAdapter(SolarObjectClickListener clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    protected ListItemSolarBinding createBinding(ViewGroup parent) {
        ListItemSolarBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_item_solar,parent,false);
        binding.solarItemMoreButton.setOnClickListener(view -> clickCallback.onItemClick(binding.getPlanet()));
        return binding;
    }

    @Override
    protected void bind(ListItemSolarBinding binding, Planet item) {binding.setPlanet(item);}

    @Override
    protected boolean areItemsTheSame(Planet oldItem, Planet newItem) {return false;}

    @Override
    protected boolean areContentsTheSame(Planet oldItem, Planet newItem) {return false;}

    public interface SolarObjectClickListener {
        void onItemClick(Planet planet);
    }
}
