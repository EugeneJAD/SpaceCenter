package com.eugene.spacecenter.data.models;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

/**
 * Created by Администратор on 08.10.2016.
 */
public class Category {

    private int id;
    private int imageResId;
    private int name;
    private Drawable rippleDrawable;

    public Category(int id, @DrawableRes int imageResId, Drawable rippleDrawable, int name) {
        this.id = id;
        this.imageResId = imageResId;
        this.name = name;
        this.rippleDrawable = rippleDrawable;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getImageResId() {return imageResId;}
    public void setImageResId(int imageResId) {this.imageResId = imageResId;}

    public int getName() {return name;}
    public void setName(int name) {this.name = name;}

    public Drawable getRippleDrawable() {
        return rippleDrawable;
    }

    public void setRippleDrawable(Drawable rippleDrawable) {
        this.rippleDrawable = rippleDrawable;
    }

}
