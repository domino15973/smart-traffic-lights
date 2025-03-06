package com.trafficlights.service;

import com.trafficlights.model.Intersection;
import com.trafficlights.model.LightState;
import com.trafficlights.model.Road;
import com.trafficlights.model.Direction;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * Implements an adaptive traffic light strategy that prioritizes the busiest opposite-direction roads.
 * The green light duration dynamically adjusts based on traffic intensity and automatically switches
 * when the current roads are empty.
 */
public class AdaptiveTrafficLightStrategy implements TrafficLightStrategy {
    private static final int MAX_GREEN_DURATION = 10;  // Maximum steps before forcing a switch
    private Direction currentGreenDirection = null;
    private int stepsElapsed = 0;

    @Override
    public void updateLights(Intersection intersection) {
        Map<Direction, Road> roads = intersection.getAllRoads();

        // If current green roads are still occupied, continue
        if (currentGreenDirection != null && stepsElapsed < MAX_GREEN_DURATION) {
            Road roadA = roads.get(currentGreenDirection);
            Road roadB = roads.get(getOppositeDirection(currentGreenDirection));

            // Keep green if vehicles are still present
            if (roadA.hasVehicles() || roadB.hasVehicles()) {
                stepsElapsed++;
                return;
            }
        }

        // Reset step counter when changing lights
        stepsElapsed = 0;

        // Set all lights to RED
        roads.values().forEach(road -> road.getTrafficLight().setState(LightState.RED));

        // Find the busiest opposite-direction pair
        Optional<Direction> busiestPair = roads.keySet().stream()
                .max(Comparator.comparingInt(dir -> roads.get(dir).getWaitingVehicles().size() +
                        roads.get(getOppositeDirection(dir)).getWaitingVehicles().size()));

        // If no vehicles remain, keep all lights red
        if (busiestPair.isEmpty() || (roads.get(busiestPair.get()).getWaitingVehicles().isEmpty() &&
                roads.get(getOppositeDirection(busiestPair.get())).getWaitingVehicles().isEmpty())) {
            currentGreenDirection = null;
            return;
        }

        // Set the new green light and start counting steps
        busiestPair.ifPresent(direction -> {
            roads.get(direction).getTrafficLight().setState(LightState.GREEN);
            roads.get(getOppositeDirection(direction)).getTrafficLight().setState(LightState.GREEN);
            currentGreenDirection = direction;
        });
    }

    /**
     * Returns the opposite direction for a given direction.
     *
     * @param direction The given direction
     * @return The opposite direction
     */
    private Direction getOppositeDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> Direction.SOUTH;
            case SOUTH -> Direction.NORTH;
            case EAST -> Direction.WEST;
            case WEST -> Direction.EAST;
        };
    }
}
