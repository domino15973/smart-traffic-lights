package com.trafficlights.service;

import com.trafficlights.model.Intersection;

/**
 * Defines a strategy for managing traffic lights at an intersection.
 */
public interface TrafficLightStrategy {
    /**
     * Updates the state of traffic lights based on the strategy.
     *
     * @param intersection The intersection to manage
     */
    void updateLights(Intersection intersection);
}
