package com.trafficlights.controller;

import com.trafficlights.io.CommandParser;
import com.trafficlights.io.SimulationResultWriter;
import com.trafficlights.model.Intersection;
import com.trafficlights.model.Road;
import com.trafficlights.model.TrafficLight;
import com.trafficlights.model.Direction;
import com.trafficlights.service.TrafficLightStrategy;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages the execution of the traffic simulation.
 */
public class SimulationManager {
    private final Intersection intersection;
    private final TrafficLightController trafficLightController;
    private final List<List<String>> simulationResults;
    private final Queue<SimulationCommand> commandQueue;

    public SimulationManager(TrafficLightStrategy strategy) {
        this.intersection = createDefaultIntersection();
        this.trafficLightController = new TrafficLightController(intersection, strategy);
        this.simulationResults = new ArrayList<>();
        this.commandQueue = new LinkedList<>();
    }

    /**
     * Loads simulation commands from a JSON file.
     *
     * @param filePath Path to the input JSON file
     * @throws IOException If an error occurs while reading the file
     */
    public void loadCommands(String filePath) throws IOException {
        CommandParser parser = new CommandParser();
        commandQueue.addAll(parser.parseCommands(filePath));
    }

    /**
     * Runs the simulation step by step based on the queued commands.
     */
    public void runSimulation() {
        while (!commandQueue.isEmpty()) {
            SimulationCommand command = commandQueue.poll();
            if (command instanceof SimulationCommand.AddVehicle addVehicleCommand) {
                intersection.getRoad(addVehicleCommand.vehicle().getStartRoad()).addVehicle(addVehicleCommand.vehicle());
            } else if (command instanceof SimulationCommand.Step) {
                executeSimulationStep();
            }
        }
    }

    /**
     * Executes a single step of the simulation.
     */
    private void executeSimulationStep() {
        // Update traffic lights
        trafficLightController.updateTrafficLights();

        // Process vehicle movements
        List<String> leftVehicles = new ArrayList<>();
        for (Road road : intersection.getAllRoads().values()) {
            if (road.getTrafficLight().isGreen() && road.hasVehicles()) {
                leftVehicles.add(road.removeVehicle().getId());
            }
        }
        simulationResults.add(leftVehicles);
    }

    /**
     * Saves the simulation results to a JSON file.
     *
     * @param outputFilePath Path to the output JSON file
     * @throws IOException If an error occurs while writing the file
     */
    public void saveResults(String outputFilePath) throws IOException {
        SimulationResultWriter writer = new SimulationResultWriter();
        writer.writeResults(outputFilePath, simulationResults);
    }

    /**
     * Creates a default intersection with four roads and traffic lights.
     *
     * @return Configured intersection
     */
    private Intersection createDefaultIntersection() {
        Map<Direction, Road> roads = Arrays.stream(Direction.values())
                .collect(Collectors.toMap(
                        direction -> direction,
                        direction -> new Road(direction, new TrafficLight(com.trafficlights.model.LightState.RED))
                ));
        return new Intersection(roads);
    }
}
