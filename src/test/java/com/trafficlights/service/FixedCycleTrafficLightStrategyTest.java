package com.trafficlights.service;

import com.trafficlights.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FixedCycleTrafficLightStrategyTest {
    private FixedCycleTrafficLightStrategy strategy;
    private Intersection intersection;

    @BeforeEach
    void setUp() {
        strategy = new FixedCycleTrafficLightStrategy();
        intersection = new Intersection(Map.of(
                Direction.NORTH, new Road(Direction.NORTH, new TrafficLight(LightState.RED)),
                Direction.SOUTH, new Road(Direction.SOUTH, new TrafficLight(LightState.RED)),
                Direction.EAST, new Road(Direction.EAST, new TrafficLight(LightState.RED)),
                Direction.WEST, new Road(Direction.WEST, new TrafficLight(LightState.RED))
        ));
    }

    @Test
    void shouldCycleLightsCorrectly() {
        strategy.updateLights(intersection);

        assertEquals(LightState.GREEN, intersection.getRoad(Direction.NORTH).getTrafficLight().getState());
        assertEquals(LightState.GREEN, intersection.getRoad(Direction.SOUTH).getTrafficLight().getState());

        for (int i = 0; i < 10; i++) {
            strategy.updateLights(intersection);
        }

        assertEquals(LightState.GREEN, intersection.getRoad(Direction.EAST).getTrafficLight().getState());
        assertEquals(LightState.GREEN, intersection.getRoad(Direction.WEST).getTrafficLight().getState());
    }
}
