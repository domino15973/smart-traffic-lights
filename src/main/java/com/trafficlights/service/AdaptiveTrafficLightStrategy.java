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
 */
public class AdaptiveTrafficLightStrategy implements TrafficLightStrategy {

    @Override
    public void updateLights(Intersection intersection) {
        Map<Direction, Road> roads = intersection.getAllRoads();

        // Set all traffic lights to RED
        roads.values().forEach(road -> road.getTrafficLight().setState(LightState.RED));

        // Determine which opposite-direction pair is the busiest
        Optional<Direction> busiestPair = roads.keySet().stream()
                .max(Comparator.comparingInt(dir -> roads.get(dir).getWaitingVehicles().size() +
                        roads.get(getOppositeDirection(dir)).getWaitingVehicles().size()));

        // Set the green light for the selected pair
        busiestPair.ifPresent(direction -> {
            roads.get(direction).getTrafficLight().setState(LightState.GREEN);
            roads.get(getOppositeDirection(direction)).getTrafficLight().setState(LightState.GREEN);
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
