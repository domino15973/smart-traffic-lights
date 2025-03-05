package com.trafficlights.controller;

import com.trafficlights.model.Vehicle;

/**
 * Represents a command in the traffic simulation.
 */
public sealed interface SimulationCommand permits SimulationCommand.AddVehicle, SimulationCommand.Step {

    /**
     * Command to add a vehicle to the simulation.
     */
    record AddVehicle(Vehicle vehicle) implements SimulationCommand {
    }

    /**
     * Command to execute a single simulation step.
     */
    record Step() implements SimulationCommand {
    }
}

