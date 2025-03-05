package com.trafficlights.model;

/**
 * Represents a vehicle in the simulation.
 */
public class Vehicle {
    private final String id;
    private final Direction startRoad;
    private final Direction endRoad;

    public Vehicle(String id, Direction startRoad, Direction endRoad) {
        this.id = id;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    public String getId() {
        return id;
    }

    public Direction getStartRoad() {
        return startRoad;
    }

    public Direction getEndRoad() {
        return endRoad;
    }
}
