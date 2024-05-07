import javafx.scene.layout.Pane;

public interface FrontendInterface {

  /**
   * Creates all controls in the GUI.
   * @param parent the parent pane that contains all controls
   */
  public void createAllControls(Pane parent);

  /**
   * Creates the controls for the shortest path search.
   * @param parent the parent pane that contains all controls
   */
  public void createShortestPathControls(Pane parent);

  /**
   * Creates the controls for displaying the shortest path returned by the search.
   * @param the parent pane that contains all controls
   */
  public void createPathListDisplay(Pane parent);

  /**
   * Creates controls for the two features in addition to the shortest path search.
   * @param parent parent pane that contains all controls
   */
  public void createAdditionalFeatureControls(Pane parent);

  /**
   * Creates the check box to add travel times in the result display.
   * @param parent parent pane that contains all controls
   */
  public void createTravelTimesBox(Pane parent);

  /**
   * Creates controls to allow users to add a third location for the path to go through.
   * @param parent parent pane that contains all controls
   */
  public void createOptionalLocationControls(Pane parent);

  /**
   * Creates an about and quit button.
   * @param parent parent pane that contains all controls
   */
  public void createAboutAndQuitControls(Pane parent);
  
}
