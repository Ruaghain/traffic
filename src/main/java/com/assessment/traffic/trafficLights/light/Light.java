package com.assessment.traffic.trafficLights.light;

public class Light {

    private LightColour colour;
    private boolean isOn;

    public Light(LightColour colour, boolean isOn) {
        this.colour = colour;
        this.isOn = isOn;
    }

    public LightColour getColour() {
        return colour;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
