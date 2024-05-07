import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BackendDeveloperTests {

    BackendInterface backend = new BackendPlaceholder(null); // Placeholder object for testing

    /**
     * Test to verify if the list of all locations is returned correctly.
     */
    @Test
    void testGetListOfAllLocations() {
        // Expected list of locations
        String[] expectedLocations = {"Union South", "Computer Sciences and Statistics", "Atmospheric, Oceanic and Space Sciences"};

        // Get the list of all locations from the backend
        var actualLocations = backend.getListOfAllLocations();

        // Check if the expected locations match the actual locations
        assertArrayEquals(expectedLocations, actualLocations.toArray());
    }

    /**
     * Test to verify if the shortest path between two locations is returned correctly.
     */
    @Test
    void testFindShortestPath() {
        // Test data
        String startLocation = "Union South";
        String endLocation = "Atmospheric, Oceanic and Space Sciences";

        // Get the shortest path between the start and end locations
        var shortestPath = backend.findShortestPath(startLocation, endLocation);

        // Check if the path is not empty
        assertFalse(shortestPath.isEmpty());

        // Print the shortest path (for debugging)
        System.out.println("Shortest path: " + shortestPath);
    }

    /**
     * Test to verify if the travel times on the shortest path between two locations are returned correctly.
     */
    @Test
    void testGetTravelTimesOnPath() {
        // Test data
        String startLocation = "Union South";
        String endLocation = "Atmospheric, Oceanic and Space Sciences";

        // Get the travel times on the shortest path between the start and end locations
        var travelTimes = backend.getTravelTimesOnPath(startLocation, endLocation);

        // Check if the travel times list is not empty
        assertFalse(travelTimes.isEmpty());

        // Print the travel times (for debugging)
        System.out.println("Travel times on path: " + travelTimes);
    }

    /**
     * Test to verify if the shortest path passing through a via location is returned correctly.
     */
    @Test
    void testFindShortestPathVia() {
        // Test data
        String startLocation = "Union South";
        String viaLocation = "Memorial Union";
        String endLocation = "Atmospheric, Oceanic and Space Sciences";

        // Get the shortest path passing through the via location
        var pathVia = backend.findShortestPathVia(startLocation, viaLocation, endLocation);

        // Check if the path is not empty
        assertFalse(pathVia.isEmpty());

        // Print the path passing through the via location (for debugging)
        System.out.println("Shortest path via " + viaLocation + ": " + pathVia);
    }
}
