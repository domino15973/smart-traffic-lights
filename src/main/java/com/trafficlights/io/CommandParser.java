package com.trafficlights.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trafficlights.model.Direction;
import com.trafficlights.model.Vehicle;
import com.trafficlights.controller.SimulationCommand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a JSON file containing simulation commands.
 */
public class CommandParser {
    private final ObjectMapper objectMapper;

    public CommandParser() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Parses a JSON file and returns a list of simulation commands.
     *
     * @param filePath Path to the JSON file
     * @return List of simulation commands
     * @throws IOException If an error occurs while reading the file
     */
    public List<SimulationCommand> parseCommands(String filePath) throws IOException {
        File file = new File(filePath);
        JsonNode rootNode = objectMapper.readTree(file);
        List<SimulationCommand> commands = new ArrayList<>();

        JsonNode commandsNode = rootNode.get("commands");
        if (commandsNode != null && commandsNode.isArray()) {
            for (JsonNode commandNode : commandsNode) {
                String type = commandNode.get("type").asText();
                if ("addVehicle".equals(type)) {
                    String vehicleId = commandNode.get("vehicleId").asText();
                    Direction startRoad = Direction.valueOf(commandNode.get("startRoad").asText().toUpperCase());
                    Direction endRoad = Direction.valueOf(commandNode.get("endRoad").asText().toUpperCase());
                    commands.add(new SimulationCommand.AddVehicle(new Vehicle(vehicleId, startRoad, endRoad)));
                } else if ("step".equals(type)) {
                    commands.add(new SimulationCommand.Step());
                }
            }
        }
        return commands;
    }
}
