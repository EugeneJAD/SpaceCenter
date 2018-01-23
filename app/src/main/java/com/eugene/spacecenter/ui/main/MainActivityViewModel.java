package com.eugene.spacecenter.ui.main;

import android.arch.lifecycle.ViewModel;

import com.eugene.spacecenter.data.models.Category;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Eugene on 19.01.2018.
 */

public class MainActivityViewModel extends ViewModel{

    private List<Category> categories;

    @Inject
    public MainActivityViewModel() {}

    public List<Category> getCategories() {return categories;}
    public void setCategories(List<Category> categories) {this.categories = categories;}
}
