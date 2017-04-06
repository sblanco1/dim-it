package com.att.a56withrice.dimit;

/**
 * Created by mr088w on 4/4/2017.
 */

public class LightFixture {

    private String name;
    private int lightValue;
    private boolean lightStatus;
    private boolean smartStatus;

    public LightFixture() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getLightStatus() {
        return this.lightStatus;
    }

    public void setLightStatus(boolean lightStatus) {
        this.lightStatus = lightStatus;
    }

    public boolean getSmartStatus() {
        return this.smartStatus;
    }

    public void setSmartStatus(boolean smartStatus) {
        this.smartStatus = smartStatus;
    }

    public int getLightValue() {
        return this.lightValue;
    }

    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }
}
