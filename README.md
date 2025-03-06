# 🚦 Smart Traffic Lights Simulation
A simulation of intelligent traffic lights at an intersection. The system dynamically adjusts traffic light cycles based on traffic density on each road, ensuring safe and efficient traffic flow. 

## Features
- Realistic representation of a four-way intersection 
- Standard traffic light cycles (green, yellow, red)  
- Adaptive traffic light system that adjusts based on real-time traffic conditions  
- Prevents conflicting movements to ensure safety  
- Supports JSON-based command input and output  
- Two traffic light control strategies:  
   - Fixed Cycle Strategy (Changes lights at predefined intervals)
   - Adaptive Strategy (Dynamically changes lights based on real-time traffic) 

## Requirements
- Java 21 (or later)
- Maven (for dependency management)
- Git (for cloning the repository)

## Installation & Running the Simulation

### 1. Clone the repository
```sh
git clone https://github.com/domino15973/smart-traffic-lights.git
```
```sh
cd smart-traffic-lights
```

### 2. Build the project using Maven
```sh
mvn clean package
```

### 3. Run the simulation
```sh
java -jar target/smart-traffic-lights-1.0-SNAPSHOT.jar input.json output.json [strategy]
```
- `input.json` → Input file with traffic commands
- `output.json` → Output file where simulation results are stored
- `strategy` (optional) → "fixed" or "adaptive" (default is "adaptive")

## JSON Input Format
The simulation accepts a JSON file with a list of commands that define actions at the intersection.
- Example `input.json`
```
{
  "commands": [
    {
      "type": "addVehicle",
      "vehicleId": "vehicle1",
      "startRoad": "south",
      "endRoad": "north"
    },
    {
      "type": "addVehicle",
      "vehicleId": "vehicle2",
      "startRoad": "north",
      "endRoad": "south"
    },
    {
      "type": "step"
    },
    {
      "type": "step"
    },
    {
      "type": "addVehicle",
      "vehicleId": "vehicle3",
      "startRoad": "west",
      "endRoad": "south"
    },
    {
      "type": "addVehicle",
      "vehicleId": "vehicle4",
      "startRoad": "west",
      "endRoad": "south"
    },
    {
      "type": "step"
    },
    {
      "type": "step"
    }
  ]
}
```
- Supported Commands
   - `addVehicle` - Adds a vehicle to the specified road
   - `step` - Moves the simulation forward by one step

## JSON Output Format
The simulation outputs a JSON file that lists which vehicles have left the intersection at each step.
- Example `output.json`
```
{
  "stepStatuses": [
    {
      "leftVehicles": [
        "vehicle1",
        "vehicle2"
      ]
    },
    {
      "leftVehicles": []
    },
    {
      "leftVehicles": [
        "vehicle3"
      ]
    },
    {
      "leftVehicles": [
        "vehicle4"
      ]
    }
  ]
}
```
- `stepStatuses` - List of steps in the simulation
- `leftVehicles` - List of vehicle IDs that left the intersection in a given step

## Traffic Light Control Strategies

### Fixed Cycle Strategy
- Traffic lights follow a fixed cycle.
- Opposite directions (NORTH-SOUTH and EAST-WEST) switch every 10 steps.
- Vehicles can pass as long as the light remains green.

How it works:
- At step 0, NORTH-SOUTH gets green for 10 steps.
- At step 10, EAST-WEST gets green for 10 steps.
- The cycle repeats indefinitely.

Best for: Predictable, evenly distributed traffic.

### Adaptive Strategy
- Dynamically selects the busiest roads to get green lights.
- If a road runs out of vehicles, the light switches earlier.
- Maximum green duration = 10 steps to prevent stagnation.

How it works:
- Finds the most congested road pair (NORTH-SOUTH or EAST-WEST).
- Sets green light for that pair.
- If vehicles still exist, keeps light green.
- If no vehicles left, switches to a new busy road.
- Ensures fairness by limiting max green light duration to 10 steps.

Best for: Real-world traffic scenarios with unpredictable congestion.