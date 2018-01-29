package com.eugene.spacecenter.ui.apods;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Apod;
import com.eugene.spacecenter.databinding.ListItemApodBinding;
import com.eugene.spacecenter.ui.base.DataBoundListAdapter;
import com.eugene.spacecenter.utils.Objects;

/**
 * Created by Eugene on 23.01.2018.
 */

public class ApodRVAdapter extends DataBoundListAdapter<Apod,ListItemApodBinding> {

    private ApodItemClickListener clickCallback;

    public ApodRVAdapter(ApodItemClickListener clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    protected ListItemApodBinding createBinding(ViewGroup parent) {
        ListItemApodBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_item_apod, parent, false);
        binding.getRoot().setOnClickListener(v-> clickCallback.onItemClick(binding.getApod().getDate()));
        return binding;
    }

    @Override
    protected void bind(ListItemApodBinding binding, Apod item) {binding.setApod(item);}

    @Override
    protected boolean areItemsTheSame(Apod oldItem, Apod newItem) {return Objects.equals(oldItem.getDate(),newItem.getDate());}

    @Override
    protected boolean areContentsTheSame(Apod oldItem, Apod newItem) {return Objects.equals(oldItem.getUrl(),newItem.getUrl());}

    public interface ApodItemClickListener {void onItemClick(String date);}
}
