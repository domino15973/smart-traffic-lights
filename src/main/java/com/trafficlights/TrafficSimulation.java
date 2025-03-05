package com.trafficlights;

import com.trafficlights.controller.SimulationManager;
import com.trafficlights.service.AdaptiveTrafficLightStrategy;
import com.trafficlights.service.FixedCycleTrafficLightStrategy;
import com.trafficlights.service.TrafficLightStrategy;

import java.io.IOException;

/**
 * Main class for running the smart traffic light simulation.
 */
public class TrafficSimulation {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar smart-traffic-lights.jar <input.json> <output.json> [strategy]");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String strategyType = args.length > 2 ? args[2] : "adaptive";

        // Select the strategy based on input argument
        TrafficLightStrategy strategy = selectStrategy(strategyType);

        // Initialize and run the simulation
        SimulationManager simulationManager = new SimulationManager(strategy);
        try {
            simulationManager.loadCommands(inputFilePath);
            simulationManager.runSimulation();
            simulationManager.saveResults(outputFilePath);
            System.out.println("Simulation completed successfully. Results saved to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error during simulation: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Selects a traffic light strategy based on user input.
     *
     * @param strategyType Strategy type as a string
     * @return Chosen TrafficLightStrategy implementation
     */
    private static TrafficLightStrategy selectStrategy(String strategyType) {
        return switch (strategyType.toLowerCase()) {
            case "fixed" -> new FixedCycleTrafficLightStrategy();
            case "adaptive" -> new AdaptiveTrafficLightStrategy();
            default -> {
                System.err.println("Unknown strategy type: " + strategyType + ". Defaulting to adaptive strategy.");
                yield new AdaptiveTrafficLightStrategy();
            }
        };
    }
}
