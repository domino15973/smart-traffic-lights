package com.trafficlights.model;

/**
 * Represents a traffic light with different states.
 */
public class TrafficLight {
    private LightState state;

    public TrafficLight(LightState initialState) {
        this.state = initialState;
    }

    public LightState getState() {
        return state;
    }

    public void setState(LightState state) {
        this.state = state;
    }

    public boolean isGreen() {
        return state == LightState.GREEN;
    }

    public boolean isRed() {
        return state == LightState.RED;
    }
}
