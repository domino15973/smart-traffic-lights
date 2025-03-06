package com.trafficlights.service;

import com.trafficlights.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AdaptiveTrafficLightStrategyTest {
    private AdaptiveTrafficLightStrategy strategy;
    private Intersection intersection;

    @BeforeEach
    void setUp() {
        strategy = new AdaptiveTrafficLightStrategy();
        intersection = new Intersection(Map.of(
                Direction.NORTH, new Road(Direction.NORTH, new TrafficLight(LightState.RED)),
                Direction.SOUTH, new Road(Direction.SOUTH, new TrafficLight(LightState.RED)),
                Direction.EAST, new Road(Direction.EAST, new TrafficLight(LightState.RED)),
                Direction.WEST, new Road(Direction.WEST, new TrafficLight(LightState.RED))
        ));

        intersection.getRoad(Direction.NORTH).addVehicle(new Vehicle("V1", Direction.NORTH, Direction.SOUTH));
        intersection.getRoad(Direction.SOUTH).addVehicle(new Vehicle("V2", Direction.SOUTH, Direction.NORTH));
    }

    @Test
    void shouldPrioritizeBusiestRoads() {
        strategy.updateLights(intersection);

        assertEquals(LightState.GREEN, intersection.getRoad(Direction.NORTH).getTrafficLight().getState());
        assertEquals(LightState.GREEN, intersection.getRoad(Direction.SOUTH).getTrafficLight().getState());

        // Simulate vehicles passing through the intersection
        intersection.getRoad(Direction.NORTH).removeVehicle();
        intersection.getRoad(Direction.SOUTH).removeVehicle();

        // Run additional steps to trigger the light change
        for (int i = 0; i < 5; i++) {
            strategy.updateLights(intersection);
        }

        // Now lights should be red since there are no vehicles left
        assertEquals(LightState.RED, intersection.getRoad(Direction.NORTH).getTrafficLight().getState());
        assertEquals(LightState.RED, intersection.getRoad(Direction.SOUTH).getTrafficLight().getState());
    }
}
