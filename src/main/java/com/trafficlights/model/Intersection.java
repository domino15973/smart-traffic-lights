package com.trafficlights.model;

import java.util.Map;

/**
 * Represents a traffic intersection with multiple roads and traffic lights.
 */
public class Intersection {
    private final Map<Direction, Road> roads;

    public Intersection(Map<Direction, Road> roads) {
        this.roads = roads;
    }

    public Road getRoad(Direction direction) {
        return roads.get(direction);
    }

    public Map<Direction, Road> getAllRoads() {
        return roads;
    }
}
