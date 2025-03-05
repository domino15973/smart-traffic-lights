package com.trafficlights.controller;

import com.trafficlights.model.Intersection;
import com.trafficlights.service.TrafficLightStrategy;

/**
 * Controls the traffic lights at an intersection using a chosen strategy.
 */
public class TrafficLightController {
    private final Intersection intersection;
    private final TrafficLightStrategy strategy;

    public TrafficLightController(Intersection intersection, TrafficLightStrategy strategy) {
        this.intersection = intersection;
        this.strategy = strategy;
    }

    /**
     * Updates traffic lights based on the selected strategy.
     */
    public void updateTrafficLights() {
        strategy.updateLights(intersection);
    }
}
