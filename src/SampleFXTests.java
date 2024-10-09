import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Application;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

/**
 * This class demonstrates how you can write junit tests for JavaFX GUIs. It
 * uses the TestFX library's FxRobot class to simluate inputs from a user while
 * your test runs. Documentation for the TestFX library can be found here:
 * https://testfx.github.io/TestFX/docs/javadoc/testfx-core/javadoc/org.testfx/module-summary.html
 * This jar files for this library have been packaged into a junit5fx.jar file
 * that can be downloaded using the instructions below, and can be used in place
 * of our old junit5.jar file going forward.
 *
 * To create your own tests like this:
 * 1) define your test class to extend ApplicationTest.
 * 2) copy-paste the @BeforeEach public void setup... method defined below into
 * your own test class, and change the SampleApp reference inside to the be
 * the name of your own class that extends javafx.application.Application.
 * 3) set the ids for any controls that you'd like your tests to be able to
 * access: either to interact with or to check the updated state within.
 * 4) use the FxRobot API to simulate interaction with your GUI in your tests:
 * https://testfx.github.io/TestFX/docs/javadoc/testfx-core/javadoc/org.testfx/org/testfx/api/FxRobot.html
 *
 * You can find examples of #3 in the SampleApp class below, and examples of #4
 * in the sample test methods that follow below.
 *
 * To compile and run the sample tests below:
 * 0) If you don't already have a javafx folder with the appropriate version
 * of javafx right outside of your assignment folder, follow the instructions
 * in A07 to download/create that folder.
 * 1) Download and use junit5fx.jar instead of our old junit5.jar file by
 * running the following command inside your assignment folder:
 * wget -P .. https://pages.cs.wisc.edu/~cs400/junit5fx.jar
 * 2) To compile this file, you'll need both include the javafx modules, and
 * include this new junit5fx.jar file in your classpath, as follows:
 * javac --module-path ../javafx/lib --add-modules javafx.controls -cp
 * .:../junit5fx.jar SampleFXTests.java
 * 3) Similar arguments are sent to java to run this sample application:
 * java --module-path ../javafx/lib --add-modules javafx.controls -cp
 * .:../junit5fx.jar SampleFXTests
 * 4) You'll also need these arguments to run the test runner, along with one
 * additional argument (add-opens) to help our tests access javafx details:
 * java --module-path ../javafx/lib --add-modules javafx.controls --add-opens
 * javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar
 * -cp . -c SampleFXTests
 * 5) Sit back and watch the robot complete each of your tests.
 * You can update the above commands to work with your own tests by replacing
 * references to SampleFXTests with the name of your own test class.
 */
public class SampleFXTests extends ApplicationTest {

    /**
     * This method launches the JavaFX application that you would like to test
     * BeforeEach of your @Test methods are run. Copy and paste this into your
     * own Test class, and change the SampleApp.class reference to instead
     * refer to your own application class that extends Application.
     */
    @BeforeEach
    public void setup() throws Exception {
        ApplicationTest.launch(SampleApp.class);
    }

    /**
     * Here is a simple sample JavaFX application that demonstrates how this
     * test utility class can be used for testing. See tests below.
     */
    public static class SampleApp extends Application {
        /**
         * This sample program has a single label and button. Whenever the
         * button is clicked, extra text is appended to the end of the label.
         */
        public void start(Stage stage) {
            Label label = new Label("Waiting:");
            label.setId("onlyLabelId"); // reference label from test below
            Button button = new Button("click me");
            button.setId("onlyButtonId"); // to reference button from test
            button.setOnAction(actionEvent -> label.setText(
                    label.getText() + " clicked"));
            stage.setScene(new Scene(new VBox(label, button), 200, 100));
            stage.show();
        }
    }

    /**
     * This test finds the label and button with the given ids, and confirms
     * that the text in each has been properly initialized.
     */
    @Test
    public void testButtonAndLabelExist() {
        Label label = lookup("#onlyLabelId").query();
        assertEquals("Waiting:", label.getText());
        Button button = lookup("#onlyButtonId").query();
        assertEquals("click me", button.getText());
    }

    /**
     * This test simulates clicking on the only button in this application twice
     * and ensuring that the label's text is updated appropriately each time.
     */
    @Test
    public void testButtonClicks() {
        Label label = lookup("#onlyLabelId").query();
        Button buton = lookup("#onlyButtonId").query();

        clickOn("#onlyButtonId");
        assertEquals("Waiting: clicked", label.getText());

        clickOn("#onlyButtonId");
        assertEquals("Waiting: clicked clicked", label.getText());
    }

    /**
     * To demonstrate the code being tested, you can run the SampleApp above
     * as a JavaFX application through the following entry point.
     */
    public static void main(String[] args) {
        Application.launch(SampleApp.class);
    }
}
