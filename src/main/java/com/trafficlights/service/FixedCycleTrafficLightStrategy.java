package com.trafficlights.service;

import com.trafficlights.model.Intersection;
import com.trafficlights.model.LightState;
import com.trafficlights.model.Road;
import com.trafficlights.model.Direction;

import java.util.List;
import java.util.Map;

/**
 * Implements a fixed-time cycle for traffic lights, switching opposite directions simultaneously every N steps.
 */
public class FixedCycleTrafficLightStrategy implements TrafficLightStrategy {
    private static final List<List<Direction>> CYCLE_ORDER = List.of(
            List.of(Direction.NORTH, Direction.SOUTH),
            List.of(Direction.EAST, Direction.WEST)
    );
    private static final int FIXED_DURATION_STEPS = 10; // Lights remain green for 10 steps before switching
    private int currentPhase = 0;
    private int stepCounter = 0;

    @Override
    public void updateLights(Intersection intersection) {
        Map<Direction, Road> roads = intersection.getAllRoads();

        // Change lights only every FIXED_DURATION_STEPS steps
        if (stepCounter == 0) {
            // Set all lights to RED
            roads.values().forEach(road -> road.getTrafficLight().setState(LightState.RED));

            // Set green light for the current phase
            List<Direction> activeDirections = CYCLE_ORDER.get(currentPhase);
            activeDirections.forEach(direction -> roads.get(direction).getTrafficLight().setState(LightState.GREEN));

            // Move to the next phase after FIXED_DURATION_STEPS
            currentPhase = (currentPhase + 1) % CYCLE_ORDER.size();
        }

        // Increment step counter and reset after FIXED_DURATION_STEPS
        stepCounter = (stepCounter + 1) % FIXED_DURATION_STEPS;
    }
}
