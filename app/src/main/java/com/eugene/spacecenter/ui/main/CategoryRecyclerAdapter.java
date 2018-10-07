package com.eugene.spacecenter.ui.main;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Category;
import com.eugene.spacecenter.databinding.ListItemCategoryBinding;
import com.eugene.spacecenter.ui.base.DataBoundListAdapter;

/**
 * Created by Eugene on 19.01.2018.
 */

public class CategoryRecyclerAdapter extends DataBoundListAdapter<Category, ListItemCategoryBinding> {

    private MainMenuItemClickListener clickCallback;

    public CategoryRecyclerAdapter(MainMenuItemClickListener clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    protected ListItemCategoryBinding createBinding(ViewGroup parent) {
        ListItemCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_item_category, parent, false);
        binding.getRoot().setOnClickListener(v -> clickCallback.onItemClick(binding.getCategory().getId()));
        return binding;
    }

    @Override
    protected void bind(ListItemCategoryBinding binding, Category item) {
        binding.setCategory(item);
    }

    @Override
    protected boolean areItemsTheSame(Category oldItem, Category newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(Category oldItem, Category newItem) {
        return false;
    }

    public interface MainMenuItemClickListener {
        void onItemClick(int categoryId);
    }

}
