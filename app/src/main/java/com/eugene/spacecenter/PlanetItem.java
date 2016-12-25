package com.eugene.spacecenter;


import android.content.Context;

/**
 * Created by Администратор on 18.09.2016.
 */
public class PlanetItem {

    private int imageResourceId;
    private int planetName;
    private int planetInfo;
    private int urlResId;
    Context context;

    public PlanetItem(Context context, int imageResourceId, int planetName, int planetInfo, int urlResId)
    {
        this.context=context;
        this.imageResourceId=imageResourceId;
        this.planetName=planetName;
        this.planetInfo=planetInfo;
        this.urlResId=urlResId;

    }

    public int getImageResourceId(){return imageResourceId;}
    public int getPlanetName(){return planetName;}
    public int getPlanetInfo(){return planetInfo;}

    public String getURL() {

        String str = context.getString(urlResId);
        return str;

    }
}
