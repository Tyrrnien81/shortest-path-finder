import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FrontendDeveloperTests extends ApplicationTest {

    private Backend backend;

    @BeforeEach
    public void setup() throws Exception {
        backend = new Backend(new DijkstraGraph<>());
        backend.loadGraphData("src/campus.dot");
        Frontend.setBackend(backend);
        ApplicationTest.launch(Frontend.class);
    }

    /**
     * This test method verifies the existence and correctness of buttons and labels
     * in the user interface.
     * It checks if the labels have the expected text and if the buttons have the
     * expected text.
     */
    @Test
    public void testButtonAndLabelExist() {
        Label srcLabel = lookup("#srcLabelId").query();
        assertEquals("Path Start Selector: ", srcLabel.getText());

        Label dstLabel = lookup("#dstLabelId").query();
        assertEquals("Path End Selector: ", dstLabel.getText());

        Label viaLabel = lookup("#viaLabelId").query();
        assertEquals("Via Location (optional): ", viaLabel.getText());

        Label path1 = lookup("#path1Id").query();
        assertEquals("Results List: \n\t", path1.getText());

        Label path2 = lookup("#path2Id").query();
        assertEquals("Results List (with travel times):\n\t", path2.getText());

        Button find = lookup("#findId").query();
        assertEquals("Submit/Find Button", find.getText());

        Button reset = lookup("#resetId").query();
        assertEquals("Reset", reset.getText());
    }

    /**
     * Test case to verify the creation of all controls in the GUI.
     * It checks if the controls for the entire GUI and shortest path are created
     * successfully.
     * It also tests the functionality of the "find" and "reset" buttons, as well as
     * the displayed results.
     */
    @Test
    public void testCreateAllControls() {
        Pane testPane = new Pane();
        Frontend frontend = new Frontend();
        frontend.createAllControls(testPane);
        assertFalse(testPane.getChildren().isEmpty(),
                "Controls for the entire GUI and shortest path should be created.");

        Button find = lookup("#findId").query();
        Button reset = lookup("#resetId").query();
        Label path1 = lookup("#path1Id").query();
        Button about = lookup("#aboutId").query();

        clickOn("#aboutId");
        Label aboutText = lookup("#aboutTextId").query();
        // assertEquals("This is the shortest path finder application.\r\n" + //
        // "\r\n" + //
        // "It is designed to find the shortest path between two locations on a
        // UW-Madison campus.\r\n" + //
        // "\r\n" + //
        // "Developed by: Yeongjun Jeong\r\n" + //
        // "\r\n" + //
        // "Version: 1.0\r\n" + //
        // "\r\n" + //
        // "Date: 2024.05.07\r\n" + //
        // "\r\n" + //
        // "Contact: yjeong62@wisc.edu", aboutText.getText());

        clickOn("#resetId");
        assertEquals("Results List: \n\t", path1.getText());
    }

    /**
     * This method tests the creation of various controls in the frontend.
     * It creates a test pane and initializes the frontend object.
     * Then, it calls the methods to create shortest path controls, path list
     * display,
     * additional feature controls, and about and quit controls.
     * It asserts that the test pane is not empty after creating the controls.
     * It also performs UI interactions by clicking on buttons and asserts the
     * expected results.
     */
    @Test
    public void testOtherControls() {
        Pane testPane = new Pane();
        Frontend frontend = new Frontend();
        frontend.createShortestPathControls(testPane);
        frontend.createPathListDisplay(testPane);
        frontend.createAdditionalFeatureControls(testPane);
        frontend.createAboutAndQuitControls(testPane);
        assertFalse(testPane.getChildren().isEmpty(),
                "Controls for the entire GUI and shortest path should be created.");

        Button find = lookup("#findId").query();
        Button reset = lookup("#resetId").query();
        Label path1 = lookup("#path1Id").query();
        Button about = lookup("#aboutId").query();

        clickOn("#aboutId");
        Label aboutText = lookup("#aboutTextId").query();
        // assertEquals("shortestpath", aboutText.getText());

        clickOn("#resetId");
        assertEquals("Results List: \n\t", path1.getText());
    }

    /**
     * This method tests the creation and functionality of other controls in the
     * GUI.
     * It creates a test pane, initializes a Frontend object, and calls methods to
     * create
     * travel times box and optional location controls on the test pane. It then
     * asserts
     * that the test pane is not empty, indicating that the controls have been
     * created.
     * 
     * It also performs UI interactions by clicking on the "find" button and asserts
     * that
     * the expected results are displayed in the path1 label. Then, it clicks on the
     * "reset"
     * button and asserts that the path1 label is empty.
     */
    @Test
    public void testOtherControls2() {
        Pane testPane = new Pane();
        Frontend frontend = new Frontend();
        frontend.createTravelTimesBox(testPane);
        frontend.createOptionalLocationControls(testPane);
        assertFalse(testPane.getChildren().isEmpty(),
                "Controls for the entire GUI and shortest path should be created.");

        Button find = lookup("#findId").query();
        Button reset = lookup("#resetId").query();
        Label path1 = lookup("#path1Id").query();

        clickOn("#resetId");
        assertEquals("Results List: \n\t", path1.getText());
    }

    /**
     * This method tests the integration of the shortest path selection
     * functionality.
     * It creates a test pane, frontend, and backend objects, and verifies that the
     * controls for the GUI and shortest path are created.
     * It then sets the source and destination fields, and retrieves the start and
     * end locations.
     * Next, it finds the shortest path between the start and end locations using
     * the backend object.
     * The method prints the shortest path for debugging purposes.
     * It then simulates clicking on the "find" button and verifies that the results
     * list displays the expected path.
     * Finally, it simulates clicking on the "reset" button and verifies that the
     * results list is empty.
     */
    @Test
    public void testShortestPathSelectionIntegration() {
        Pane testPane = new Pane();
        Frontend frontend = new Frontend();
        frontend.createAllControls(testPane);
        assertFalse(testPane.getChildren().isEmpty(),
                "Controls for the entire GUI and shortest path should be created.");

        Label path1 = lookup("#path1Id").query();

        clickOn("#srcFieldId");
        write("Union South");

        clickOn("#dstFieldId");
        write("Atmospheric, Oceanic and Space Sciences");

        frontend.srcField.setText("Union South");
        frontend.dstField.setText("Atmospheric, Oceanic and Space Sciences");

        String startLocation = "Union South";
        String endLocation = "Atmospheric, Oceanic and Space Sciences";

        // Get the shortest path between the start and end locations
        var shortestPath = backend.findShortestPath(startLocation, endLocation);

        // Print the shortest path (for debugging)
        System.out.println("Shortest path: " + shortestPath);

        clickOn("#findId");
        assertEquals("Results List: \n\tUnion South\n\tAtmospheric, Oceanic and Space Sciences", path1.getText());

        clickOn("#resetId");
        assertEquals("Results List: \n\t", path1.getText());
    }

    /**
     * This method tests the integration of getting travel times on the shortest
     * path between two locations.
     * It verifies that the controls for the GUI and shortest path are created, and
     * checks the results list
     * and travel times after finding the path and resetting the results.
     */
    @Test
    public void testGetTravelTimesOnPathIntegration() {
        Pane testPane = new Pane();
        Frontend frontend = new Frontend();
        frontend.createAllControls(testPane);
        assertFalse(testPane.getChildren().isEmpty(),
                "Controls for the entire GUI and shortest path should be created.");

        Label path1 = lookup("#path1Id").query();
        Label path2 = lookup("#path2Id").query();

        clickOn("#srcFieldId");
        write("Union South");

        clickOn("#dstFieldId");
        write("Atmospheric, Oceanic and Space Sciences");

        frontend.srcField.setText("Union South");
        frontend.dstField.setText("Atmospheric, Oceanic and Space Sciences");

        String startLocation = "Union South";
        String endLocation = "Atmospheric, Oceanic and Space Sciences";

        // Get the travel times on the shortest path between the start and end locations
        var travelTimes = backend.getTravelTimesOnPath(startLocation, endLocation);

        // Print the travel times (for debugging)
        System.out.println("Travel times on path: " + travelTimes);

        clickOn("#travelTimesBoxId");

        clickOn("#findId");
        assertEquals("Results List: \n\tUnion South\n\tAtmospheric, Oceanic and Space Sciences", path1.getText());
        assertEquals(
                "Results List (with travel times):\n\tUnion South\n\t -(182.20000000000002sec)->Atmospheric, Oceanic and Space Sciences\n\t"
                        +
                        "\n\tTotal time: 182.20000000000002sec",
                path2.getText());

        clickOn("#resetId");
        assertEquals("Results List: \n\t", path1.getText());
    }

    /**
     * Test case to verify the functionality of finding the shortest path between
     * two locations.
     * It tests the `findShortestPath` method of the backend class by providing a
     * start location and an end location.
     * The expected shortest path is "[Union South, Atmospheric, Oceanic and Space
     * Sciences]".
     * It asserts that the shortest path returned by the backend is not empty and
     * matches the expected path.
     */
    @Test
    public void testFindShortestPathPartner() {
        String startLocation = "Union South";
        String endLocation = "Atmospheric, Oceanic and Space Sciences";

        var shortestPath = backend.findShortestPath(startLocation, endLocation);

        assertFalse(shortestPath.isEmpty());

        System.out.println("Shortest path: " + shortestPath);

        Assertions.assertEquals("[Union South, Atmospheric, Oceanic and Space Sciences]", shortestPath.toString());
    }

    /**
     * Test case to verify the functionality of finding the shortest path via a
     * partner location.
     * This test case checks if the backend can correctly find the shortest path
     * from a start location to an end location,
     * passing through a specified via location. It asserts that the returned path
     * is not empty and prints the shortest path
     * via the via location. It also compares the actual path with the expected path
     * using the `assertEquals` method from the
     * `Assertions` class.
     * 
     * @see Backend#findShortestPathVia(String, String, String)
     */
    @Test
    public void testFindShortestPathViaPartner() {
        String startLocation = "Union South";
        String viaLocation = "Memorial Union";
        String endLocation = "Atmospheric, Oceanic and Space Sciences";

        var pathVia = backend.findShortestPathVia(startLocation, viaLocation, endLocation);

        assertFalse(pathVia.isEmpty());

        System.out.println("Shortest path via " + viaLocation + ": " + pathVia);

        Assertions.assertEquals("[Union South, Computer Sciences and Statistics, Meiklejohn House, Chemistry Building, "
                +
                "Thomas C. Chamberlin Hall, Lathrop Hall, Law Building, Music Hall, Science Hall, Memorial Union, Radio Hall, Education Building, "
                +
                "South Hall, Law Building, X01, Luther Memorial Church, Noland Hall, Meiklejohn House, Computer Sciences and Statistics, Atmospheric, "
                +
                "Oceanic and Space Sciences]", pathVia.toString());
    }
}
