package com.trafficlights.service;

import com.trafficlights.model.Intersection;
import com.trafficlights.model.LightState;
import com.trafficlights.model.Road;
import com.trafficlights.model.Direction;

import java.util.List;
import java.util.Map;

/**
 * Implements a fixed-time cycle for traffic lights, switching opposite directions simultaneously.
 */
public class FixedCycleTrafficLightStrategy implements TrafficLightStrategy {
    private static final List<List<Direction>> CYCLE_ORDER = List.of(
            List.of(Direction.NORTH, Direction.SOUTH),
            List.of(Direction.EAST, Direction.WEST)
    );
    private int currentPhase = 0;

    @Override
    public void updateLights(Intersection intersection) {
        Map<Direction, Road> roads = intersection.getAllRoads();

        // Set all traffic lights to RED
        roads.values().forEach(road -> road.getTrafficLight().setState(LightState.RED));

        // Set the next phase (opposite-direction pair) to GREEN
        List<Direction> activeDirections = CYCLE_ORDER.get(currentPhase);
        activeDirections.forEach(direction -> roads.get(direction).getTrafficLight().setState(LightState.GREEN));

        // Move to the next phase in the cycle
        currentPhase = (currentPhase + 1) % CYCLE_ORDER.size();
    }
}
