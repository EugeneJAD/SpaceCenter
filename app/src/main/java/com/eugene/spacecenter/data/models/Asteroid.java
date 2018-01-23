package com.eugene.spacecenter.data.models;

/**
 * Created by Администратор on 22.03.2017.
 */

public class Asteroid {

    private String name;
    private String diameterMeters;
    private String closeApproachData;
    private String velocity;
    private String missDistance;
    private boolean isHazardousAsteroid;
    private int imageRes;

    public Asteroid (String name, String diameterMeters, String closeApproachData, String velocity,
                     String missDistance, boolean isHazardousAsteroid, int imageRes) {
        this.name = name;
        this.diameterMeters = diameterMeters;
        this.closeApproachData = closeApproachData;
        this.velocity = velocity;
        this.missDistance = missDistance;
        this.isHazardousAsteroid = isHazardousAsteroid;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public String getDiameterMeters() {
        return diameterMeters;
    }

    public String getCloseApproachData() {
        return closeApproachData;
    }

    public String getVelocity() {
        return velocity;
    }

    public String getMissDistance() {
        return missDistance;
    }

    public boolean isHazardousAsteroid() {
        return isHazardousAsteroid;
    }

    public int getImageRes() {
        return imageRes;
    }
}
