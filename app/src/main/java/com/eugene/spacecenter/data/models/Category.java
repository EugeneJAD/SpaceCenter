package com.eugene.spacecenter.data.models;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

/**
 * Created by Администратор on 08.10.2016.
 */
public class Category {

    private int id;
    private int imageResId;
    private int name;
    private int bgColorId;

    public Category(int id, @DrawableRes int imageResId, @ColorInt int bgResId, int name) {
        this.id = id;
        this.imageResId = imageResId;
        this.name = name;
        this.bgColorId = bgResId;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getImageResId() {return imageResId;}
    public void setImageResId(int imageResId) {this.imageResId = imageResId;}

    public int getName() {return name;}
    public void setName(int name) {this.name = name;}

    public int getBgColorId() {return bgColorId;}
    public void setBgColorId(int bgColorId) {this.bgColorId = bgColorId;}
}
