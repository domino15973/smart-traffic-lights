package com.trafficlights.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Writes the simulation results to a JSON file.
 */
public class SimulationResultWriter {
    private final ObjectMapper objectMapper;

    public SimulationResultWriter() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Writes the simulation results to a JSON file.
     *
     * @param filePath      Path to the output JSON file
     * @param stepsStatuses List of vehicles that left the intersection in each step
     * @throws IOException If an error occurs while writing the file
     */
    public void writeResults(String filePath, List<List<String>> stepsStatuses) throws IOException {
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode stepStatusesNode = rootNode.putArray("stepStatuses");

        for (List<String> leftVehicles : stepsStatuses) {
            ObjectNode stepNode = objectMapper.createObjectNode();
            ArrayNode vehiclesNode = stepNode.putArray("leftVehicles");
            for (String vehicleId : leftVehicles) {
                vehiclesNode.add(vehicleId);
            }
            stepStatusesNode.add(stepNode);
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), rootNode);
    }
}
