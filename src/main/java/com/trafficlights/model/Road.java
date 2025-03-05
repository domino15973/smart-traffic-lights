package com.trafficlights.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a road leading to an intersection, with a queue of waiting vehicles.
 */
public class Road {
    private final Direction direction;
    private final Queue<Vehicle> waitingVehicles;
    private TrafficLight trafficLight;

    public Road(Direction direction, TrafficLight trafficLight) {
        this.direction = direction;
        this.trafficLight = trafficLight;
        this.waitingVehicles = new LinkedList<>();
    }

    public Direction getDirection() {
        return direction;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    public void addVehicle(Vehicle vehicle) {
        waitingVehicles.add(vehicle);
    }

    public Vehicle removeVehicle() {
        return waitingVehicles.poll();
    }

    public boolean hasVehicles() {
        return !waitingVehicles.isEmpty();
    }

    public Queue<Vehicle> getWaitingVehicles() {
        return waitingVehicles;
    }
}
