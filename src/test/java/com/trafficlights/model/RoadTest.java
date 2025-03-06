package com.trafficlights.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {
    @Test
    void shouldAddAndRemoveVehiclesCorrectly() {
        Road road = new Road(Direction.NORTH, new TrafficLight(LightState.RED));
        Vehicle vehicle = new Vehicle("V1", Direction.NORTH, Direction.SOUTH);

        road.addVehicle(vehicle);
        assertTrue(road.hasVehicles());

        Vehicle removed = road.removeVehicle();
        assertEquals("V1", removed.getId());
        assertFalse(road.hasVehicles());
    }
}
