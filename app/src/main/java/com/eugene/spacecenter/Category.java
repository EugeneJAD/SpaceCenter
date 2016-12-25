package com.eugene.spacecenter;

/**
 * Created by Администратор on 08.10.2016.
 */
public class Category {

    private int imageResID;
    private int categoryNameResID;

    public Category(int imageResID, int categoryNameResID)
    {
        this.imageResID=imageResID;
        this.categoryNameResID=categoryNameResID;
    }

    public int getImageResID() {
        return imageResID;
    }

    public int getCategoryName() {
        return categoryNameResID;
    }
}
