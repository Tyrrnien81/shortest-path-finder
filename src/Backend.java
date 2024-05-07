import java.io.IOException;
import java.util.List;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.HashSet;
import java.util.Set;

/**
 * Backend class implementing the BackendInterface.
 * Provides mock implementations for graph-related operations.
 */
public class Backend implements BackendInterface {

    public DijkstraGraph<String, Double> graph;
    private Set<String> allNodes; // Data structure to store all nodes

    /**
     * Constructor for Backend class.
     * @param graph the graph object to store the backend's graph data
     */
    public Backend(DijkstraGraph<String, Double> graph) {
        this.graph = graph;
        this.allNodes = new HashSet<>();
    }

    /**
     * Loads graph data from a dot file.
     * @param filename the path to a dot file to read graph data from
     * @throws IOException if there was a problem reading in the specified file
     */
    public void loadGraphData(String filename) throws IOException {
        // Open the dot file for reading
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Define the pattern to match each line in the dot file
            Pattern pattern = Pattern.compile("\"([^\"]+)\" -> \"([^\"]+)\" \\[seconds=([0-9.]+)\\];");
            // Read each line in the dot file
            while ((line = reader.readLine()) != null) {
                // Match the line with the defined pattern
                Matcher matcher = pattern.matcher(line);
                // If the pattern matches
                if (matcher.find()) {
                    // Extract the source node, destination node, and edge weight from the matched groups
                    String sourceNode = matcher.group(1);
                    String destinationNode = matcher.group(2);
                    double seconds = Double.parseDouble(matcher.group(3));
                    // Insert the edge into the graph
                    graph.insertNode(sourceNode);
                    graph.insertNode(destinationNode);
                    graph.insertEdge(sourceNode, destinationNode, seconds);
                }
            }
        }
        System.out.println("Graph data loaded successfully from " + filename);
    }


    /**
     * Returns a list of all locations (nodes) available on the backend's graph.
     * @return list of all location names
     */
    public List<String> getListOfAllLocations() {
        // Check if the graph is initialized
        if (graph != null) {
            // If the graph is initialized, return a new ArrayList containing all nodes
            return new ArrayList<>(allNodes);
        } else {
            // If the graph is not initialized, return null
            return null;
        }
    }


    /**
     * Returns the sequence of locations along the shortest path from startLocation to endLocation, or
     * an empty list if no such path exists.
     * @param startLocation the start location of the path
     * @param endLocation the end location of the path
     * @return a list with the nodes along the shortest path from startLocation to endLocation, or
     *         an empty list if no such path exists
     */
    public List<String> findShortestPath(String startLocation, String endLocation) {
        // Check if the graph is initialized
        if (graph == null) {
            // If the graph is not initialized, return an empty list
            return new ArrayList<>();
        }
        System.out.println("Start: " + startLocation);

        // Use Dijkstra's algorithm to find the shortest path
        List<String> shortestPath = graph.shortestPathData(startLocation, endLocation);
        System.out.println("Path: " + shortestPath.toString());

        // If no path exists, return an empty list
        if (shortestPath.isEmpty()) {
            return new ArrayList<>();
        }

        return shortestPath;
    }


    /**
     * Returns the walking times in seconds between each two nodes on the shortest path from startLocation
     * to endLocation, or an empty list of no such path exists.
     * @param startLocation the start location of the path
     * @param endLocation the end location of the path
     * @return a list with the walking times in seconds between two nodes along the shortest path from
     *         startLocation to endLocation, or an empty list if no such path exists
     */
    public List<Double> getTravelTimesOnPath(String startLocation, String endLocation) {
        // Check if the graph is initialized
        if (graph == null) {
            // If the graph is not initialized, return an empty list
            return new ArrayList<>();
        }

        // Use Dijkstra's algorithm to find the shortest path
        List<String> shortestPath = graph.shortestPathData(startLocation, endLocation);

        // If no path exists, return an empty list
        if (shortestPath.isEmpty()) {
            return new ArrayList<>();
        }

        // Initialize the list to store travel times
        List<Double> travelTimes = new ArrayList<>();

        // Iterate through the shortest path and retrieve the travel times between consecutive nodes
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            String currentLocation = shortestPath.get(i);
            String nextLocation = shortestPath.get(i + 1);

            // Retrieve the travel time between the current and next locations from the graph
            Double time = graph.getEdge(currentLocation, nextLocation);
            travelTimes.add(time);
        }

        return travelTimes;
    }


    /**
     * Returns the sequence of locations along the shortest path from startLocation to endLocation including
     * the third location viaLocation, or an empty list if no such path exists.
     * en empty list if no such path exists.
     * @param startLocation the start location of the path
     * @param viaLocation a location that the path show lead through
     * @param endLocation the end location of the path
     * @return a list with the nodes along the shortest path from startLocation to endLocation including
     *         viaLocation, or an empty list if no such path exists
     */
    public List<String> findShortestPathVia(String startLocation, String viaLocation, String endLocation) {
        // Check if the graph is initialized
        if (graph == null) {
            // If the graph is not initialized, return an empty list
            return new ArrayList<>();
        }

        // Find the shortest path from startLocation to viaLocation
        List<String> pathFromStartToVia = graph.shortestPathData(startLocation, viaLocation);

        // Find the shortest path from viaLocation to endLocation
        List<String> pathFromViaToEnd = graph.shortestPathData(viaLocation, endLocation);

        // If either path is empty, it means there is no path from startLocation to endLocation via viaLocation
        if (pathFromStartToVia.isEmpty() || pathFromViaToEnd.isEmpty()) {
            return new ArrayList<>();
        }

        // Combine the two paths to form the final path
        List<String> finalPath = new ArrayList<>(pathFromStartToVia);
        finalPath.addAll(pathFromViaToEnd.subList(1, pathFromViaToEnd.size())); // Exclude the first node from the second path

        return finalPath;
    }


    /**
     * Returns the walking times in seconds between each two nodes on the shortest path from startLocation
     * to endLocation through viaLocation, or an empty list of no such path exists.
     * @param startLocation the start location of the path
     * @param viaLocation a location that the path show lead through
     * @param endLocation the end location of the path
     * @return a list with the walking times in seconds between two nodes along the shortest path from
     *         startLocation to endLocationthrough viaLocation, or an empty list if no such path exists
     */
    public List<Double> getTravelTimesOnPathVia(String startLocation, String viaLocation, String endLocation) {
        // Check if the graph is initialized
        if (graph == null) {
            // If the graph is not initialized, return an empty list
            return new ArrayList<>();
        }

        // Find the shortest path from startLocation to viaLocation
        List<String> pathFromStartToVia = graph.shortestPathData(startLocation, viaLocation);

        // Find the shortest path from viaLocation to endLocation
        List<String> pathFromViaToEnd = graph.shortestPathData(viaLocation, endLocation);

        // If either path is empty, it means there is no path from startLocation to endLocation via viaLocation
        if (pathFromStartToVia.isEmpty() || pathFromViaToEnd.isEmpty()) {
            return new ArrayList<>();
        }

        // Combine the two paths to form the final path
        List<String> finalPath = new ArrayList<>(pathFromStartToVia);
        finalPath.addAll(pathFromViaToEnd.subList(1, pathFromViaToEnd.size())); // Exclude the first node from the second path

        // Initialize the list to store travel times
        List<Double> travelTimes = new ArrayList<>();

        // Iterate through the final path and calculate the travel times between consecutive nodes
        for (int i = 0; i < finalPath.size() - 1; i++) {
            String currentNode = finalPath.get(i);
            String nextNode = finalPath.get(i + 1);
            Double time = graph.getEdge(currentNode, nextNode);
            travelTimes.add(time);
        }

        return travelTimes;
    }

}
