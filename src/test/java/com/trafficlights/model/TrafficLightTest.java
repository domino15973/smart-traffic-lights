package com.trafficlights.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightTest {
    @Test
    void shouldSwitchStatesCorrectly() {
        TrafficLight light = new TrafficLight(LightState.RED);

        light.setState(LightState.GREEN);
        assertEquals(LightState.GREEN, light.getState());

        light.setState(LightState.YELLOW);
        assertEquals(LightState.YELLOW, light.getState());
    }
}
