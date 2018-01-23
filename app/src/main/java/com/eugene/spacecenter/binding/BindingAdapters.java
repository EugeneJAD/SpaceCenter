package com.eugene.spacecenter.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eugene.spacecenter.ui.base.GlideApp;

/**
 * Created by E.Iatsenko on 17.01.2018.
 */

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void loadImage(AppCompatImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @BindingAdapter("imageUrlCC")
    public static void loadImageCC(AppCompatImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(new ColorDrawable(Color.DKGRAY))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @BindingAdapter("imageFromRes")
    public static void loadResImage(AppCompatImageView imageView, @DrawableRes int res) {
        GlideApp.with(imageView.getContext())
                .load(res)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @BindingAdapter("imageFromResCC")
    public static void loadResImageCenterCrop(AppCompatImageView imageView, @DrawableRes int res) {
        GlideApp.with(imageView.getContext())
                .load(res)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @BindingAdapter("fieldVisibility")
    public static void setFieldVisibility(View view, String data) {
        view.setVisibility(!TextUtils.isEmpty(data) ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("fieldVisibilityNotGone")
    public static void setFieldVisibilityNotGone(View view, String data) {
        view.setVisibility(!TextUtils.isEmpty(data) ? View.VISIBLE : View.INVISIBLE);
    }

}
