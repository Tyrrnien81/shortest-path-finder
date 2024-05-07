import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BackendPlaceholder implements BackendInterface {

  public BackendPlaceholder(GraphADT<String,Double> graph) { }

  public void loadGraphData(String filename) throws IOException {}

  public List<String> getListOfAllLocations() {
    return Arrays.asList("Union South", "Computer Sciences and Statistics", "Atmospheric, Oceanic and Space Sciences");
  }

  public List<String> findShortestPath(String startLocation, String endLocation) {
    return Arrays.asList("Union South", "Computer Sciences and Statistics", "Atmospheric, Oceanic and Space Sciences");
  }

  public List<Double> getTravelTimesOnPath(String startLocation, String endLocation) {
    return Arrays.asList(176.0, 80.0);
  }

  public List<String> findShortestPathVia(String startLocation, String via, String endLocation) {
    return Arrays.asList("Memorial Union", "Science Hall", "Radio Hall");
  }

  public List<Double> getTravelTimesOnPathVia(String startLocation, String via, String endLocation) {
    return Arrays.asList(146.0, 30.0);
  }

}
