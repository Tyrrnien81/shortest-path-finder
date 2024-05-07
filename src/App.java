import java.io.IOException;

import javafx.application.Application;

public class App {
  public static void main(String[] args) throws IOException {
    System.out.println("v0.1");
    Backend backend = new Backend(new DijkstraGraph<>());
    backend.loadGraphData("src/campus.dot");
    Frontend.setBackend(backend);
    Application.launch(Frontend.class, args);
  }
}
