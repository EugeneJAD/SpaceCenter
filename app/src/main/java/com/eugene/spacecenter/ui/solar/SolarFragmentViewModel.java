package com.eugene.spacecenter.ui.solar;

import android.arch.lifecycle.ViewModel;

import com.eugene.spacecenter.data.models.Planet;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Eugene on 25.01.2018.
 */

public class SolarFragmentViewModel extends ViewModel{

    private List<Planet> solarObjects;
    private int position=0;

    @Inject
    public SolarFragmentViewModel() {}

    public List<Planet> getSolarObjects() {return solarObjects;}
    public void setSolarObjects(List<Planet> solarObjects) {this.solarObjects = solarObjects;}

    public int getPosition() {return position;}
    public void setPosition(int position) {this.position = position;}
}
