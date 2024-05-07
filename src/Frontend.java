import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Frontend extends Application implements FrontendInterface {

    List<String> path = new LinkedList<String>();
    List<Double> times = new LinkedList<Double>();

    Button find = new Button();
    Button reset = new Button();

    TextField srcField = new TextField();
    TextField dstField = new TextField();
    TextField viaField = new TextField();

    Label path1 = new Label("Results List: \n\t");
    Label path2 = new Label("Results List (with travel times):\n\t");
    Label aboutText = new Label("shortestpath");

    boolean showTravelTimes = false;
    boolean useViaLocation = false;

    private static Backend back;

    public static void setBackend(Backend back) {
        Frontend.back = back;
    }

    public void start(Stage stage) {
        Pane root = new Pane();
    
        createAllControls(root);
    
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("UW-Madison Shortest Path Finder");
        stage.show();
    }

    @Override
    public void createAllControls(Pane parent) {
        // Composite function that delegates to other methods to create all controls
        createShortestPathControls(parent);
        createPathListDisplay(parent);
        createAdditionalFeatureControls(parent);
        createAboutAndQuitControls(parent);
    }

    @Override
    public void createShortestPathControls(Pane parent) {
        // GUI elements specific for shortest path search
        Label srcLabel = new Label("Path Start Selector: ");
        srcLabel.setId("srcLabelId");
        srcLabel.setLayoutX(32);
        srcLabel.setLayoutY(16);
        parent.getChildren().add(srcLabel);

        srcField.setPromptText("Enter Start Location");
        srcField.setId("srcFieldId");
        srcField.setLayoutX(150);
        srcField.setLayoutY(16);
        parent.getChildren().add(srcField);

        Label dstLabel = new Label("Path End Selector: ");
        dstLabel.setId("dstLabelId");
        dstLabel.setLayoutX(32);
        dstLabel.setLayoutY(48);
        parent.getChildren().add(dstLabel);

        dstField.setPromptText("Enter End Location");
        dstField.setId("dstFieldId");
        dstField.setLayoutX(150);
        dstField.setLayoutY(48);
        parent.getChildren().add(dstField);

        find = new Button("Submit/Find Button");
        find.setId("findId");
        find.setLayoutX(32);
        find.setLayoutY(80);
        parent.getChildren().add(find);

        reset = new Button("Reset");
        reset.setId("resetId");
        reset.setLayoutX(350);
        reset.setLayoutY(80);
        parent.getChildren().add(reset);
    }

    @Override
    public void createPathListDisplay(Pane parent) {
        // To do not create new Labels, seperate the creation of the labels from the setting of the text
        path1.setLayoutX(32);
        path1.setLayoutY(112);
        path1.setId("path1Id");
        parent.getChildren().add(path1);

        path2.setLayoutX(332);
        path2.setLayoutY(112);
        path2.setId("path2Id");
        parent.getChildren().add(path2);

        // Click action for the find button
        find.setOnAction(e -> {
            // If check useViaLocation is false, find shortest path without via location
            if (useViaLocation == false) {
                String startLocation = srcField.getText();
                String endLocation = dstField.getText();

                path = back.findShortestPath(startLocation, endLocation);
                times = back.getTravelTimesOnPath(startLocation, endLocation);
                
                System.out.println("Path: " + path);
                System.out.println("Times: " + times);

                // Display path in GUI
                path1.setText("Results List: \n\t" + String.join("\n\t", path));

                // If check showTravelTimes is true, display travel times
                if (showTravelTimes) {
                    path2.setText("Results List (with travel times):\n\t");
                    for (int i = 0; i < path.size(); i++) {
                        path2.setText(path2.getText() + path.get(i) + "\n\t");
                        if (i < path.size() - 1) {
                            path2.setText(path2.getText() + " -(" + times.get(i) + "sec)->");
                        }
                    }
                    // Display total time in GUI
                    path2.setText(path2.getText() + "\n\tTotal time: " + times.stream().mapToDouble(Double::doubleValue).sum() + "sec");
                }
            }
            // If check useViaLocation is true, find shortest path with via location
            else {
                String startLocation = srcField.getText();
                String endLocation = dstField.getText();
                String viaLocation = viaField.getText();

                path = back.findShortestPathVia(startLocation, viaLocation, endLocation);
                times = back.getTravelTimesOnPathVia(startLocation, viaLocation, endLocation);
                
                System.out.println("Path: " + path);
                System.out.println("Times: " + times);

                // Display path in GUI
                path1.setText("Results List: \n\t" + String.join("\n\t", path));

                // If check showTravelTimes is true, display travel times
                if (showTravelTimes) {
                    path2.setText("Results List (with travel times):\n\t");
                    for (int i = 0; i < path.size(); i++) {
                        path2.setText(path2.getText() + path.get(i) + "\n\t");
                        if (i < path.size() - 1) {
                            path2.setText(path2.getText() + " -(" + times.get(i) + "sec)->");
                        }
                    }
                    // Display total time in GUI
                    path2.setText(path2.getText() + "\n\tTotal time: " + times.stream().mapToDouble(Double::doubleValue).sum() + "sec");
                }
            }
        });

        // Click action for the reset button to clear results
        reset.setOnAction(e -> {
            srcField.clear(); // Clear the start location
            dstField.clear(); // Clear the end location
            viaField.clear(); // Clear the via location
            path1.setText("Results List: \n\t"); // Clear the path results
            path2.setText("Results List (with travel times):\n\t"); // Clear the path results with travel times
        });
    }

    @Override
    public void createAdditionalFeatureControls(Pane parent) {
        // Additional controls apart from the shortest path
        createTravelTimesBox(parent);
        createOptionalLocationControls(parent);
    }

    @Override
    public void createTravelTimesBox(Pane parent) {
        // Checkbox to control display of travel times
        CheckBox travelTimesBox = new CheckBox("Show Travel Times");
        travelTimesBox.setId("travelTimesBoxId");
        travelTimesBox.setLayoutX(200);
        travelTimesBox.setLayoutY(80);
        parent.getChildren().add(travelTimesBox);

        travelTimesBox.setOnAction(e -> {
            showTravelTimes = travelTimesBox.isSelected();
            System.out.println("Show travel times: " + showTravelTimes);
        });
    }

    @Override
    public void createOptionalLocationControls(Pane parent) {
        // Controls for optional via location in path search
        Label viaLabel = new Label("Via Location (optional): ");
        viaLabel.setLayoutX(450);
        viaLabel.setLayoutY(16);
        viaLabel.setId("viaLabelId");
        parent.getChildren().add(viaLabel);

        viaField.setPromptText("Enter Via Location");
        viaField.setId("viaFieldId");
        viaField.setLayoutX(580);
        viaField.setLayoutY(16);
        parent.getChildren().add(viaField);

        // Checkbox to control display of Via location
        CheckBox viaBox = new CheckBox("Use Above Location in Path");
        viaBox.setId("viaBoxId");
        viaBox.setLayoutX(450);
        viaBox.setLayoutY(48);
        parent.getChildren().add(viaBox);

        viaBox.setOnAction(e -> {
            useViaLocation = viaBox.isSelected();
            System.out.println("Use via Location: " + useViaLocation);
        });
    }

    @Override
    public void createAboutAndQuitControls(Pane parent) {
        // About and Quit buttons
        Button about = new Button("About");
        about.setId("aboutId");
        about.setLayoutX(680);
        about.setLayoutY(560);
        parent.getChildren().add(about);

        // About button action
        about.setOnAction(e -> {
            Stage aboutStage = new Stage();  // Create a new stage for the About window

            Pane aboutPane = new Pane();  // Create a pane for content

            // Create a label with the text
            aboutText.setText("This is the shortest path finder application.\n\n"
                    + "It is designed to find the shortest path between two locations on a UW-Madison campus.\n\n"
                    + "Developed by: Yeongjun Jeong\n\n"
                    + "Version: 1.0\n\n"
                    + "Date: 2024.05.07\n\n"
                    + "Contact: yjeong62@wisc.edu");
            aboutText.setId("aboutTextId");
            aboutText.setLayoutX(10);
            aboutText.setLayoutY(10);
            aboutPane.getChildren().add(aboutText);  // Add the label to the pane
    
            Scene aboutScene = new Scene(aboutPane, 500, 250);  // Create a scene with the pane
            aboutStage.setScene(aboutScene);  // Set the scene on the stage
            aboutStage.setTitle("About");  // Title for the new window
            aboutStage.show();  // Show the stage, making the window visible
        });

        Button quit = new Button("Quit");
        quit.setLayoutX(740);
        quit.setLayoutY(560);
        parent.getChildren().add(quit);

        // Quit button action
        quit.setOnAction(e -> System.exit(0)); 
    }
}
