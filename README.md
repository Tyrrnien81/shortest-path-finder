# Shortest Path Finder

## Overview

The **Shortest Path Finder** is a Java-based program that calculates the shortest path between nodes in a graph structure using Dijkstra's Algorithm. It is designed to manage graph data, process user inputs, and provide visual feedback for the calculated shortest path. This project is ideal for those interested in understanding graph theory, algorithm implementation, and Java programming.

## Features

-   **Graph Structure Management**: Easily create and manage graph structures, including nodes and edges.
-   **Shortest Path Calculation**: Uses Dijkstra's Algorithm to efficiently compute the shortest path between nodes.
-   **User Interface**: Includes a frontend that interacts with the user and visualizes the calculated shortest path.
-   **Modular Design**: The code is organized in a way that separates backend and frontend logic, making it easy to maintain.
-   **Unit Testing**: Includes test cases to verify the accuracy and stability of both backend and frontend components.

## Project Structure

The project is organized as follows:

### `/src`

Contains the main source code.

-   **App.java**: Entry point for the application. Initializes the program and manages user interaction.
-   **GraphADT.java, BaseGraph.java, DijkstraGraph.java**: Manages graph structures and operations, with `DijkstraGraph.java` implementing Dijkstra's Algorithm.
-   **Backend.java, BackendInterface.java**: Responsible for backend logic and data handling.
-   **Frontend.java, FrontendInterface.java**: Manages the user interface and data visualization.
-   **Test Files**: Includes `BackendDeveloperTests.java`, `FrontendDeveloperTests.java`, and other test files to validate core functionalities.

### `/bin`

Contains compiled Java class files.

### `/lib`

Holds external libraries required for the project, including JavaFX and JUnit. Make sure to download and place the necessary `.jar` files here.

### `.vscode`

Includes workspace settings for VSCode. Ensure `settings.json` is configured with relative paths to JavaFX and JUnit libraries for seamless integration.

## Prerequisites

-   **Java JDK 17** or higher
-   **JavaFX SDK 17**: Download from [Gluon JavaFX](https://gluonhq.com/products/javafx/).
-   **JUnit** for running unit tests

## Setup and Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/Tyrrnien81/shortest_path_finder.git
    ```
2. **Navigate to the project directory**:
    ```bash
    cd Shortest_Path_Finder
    ```
3. **Set up the `lib` folder**:
   Download the required JavaFX and JUnit `.jar` files, and place them in the `lib` folder. Ensure your `.vscode/settings.json` includes the following:

    ```json
    {
        "java.project.sourcePaths": ["src"],
        "java.project.outputPath": "bin",
        "java.project.referencedLibraries": ["lib/*.jar"]
    }
    ```

4. **Compile the project**:
   Use the following command to compile the project files:
    ```bash
    javac -d bin -cp lib/* src/*.java
    ```
5. **Run the application**:
    ```bash
    java --module-path lib --add-modules javafx.controls,javafx.fxml -cp bin App
    ```

## Running Tests

You can run the unit tests to verify the application¡¯s functionality:

1. Compile and run the test files:
    ```bash
    javac -d bin -cp lib/*:src src/*.java
    java -cp bin:lib/* org.junit.runner.JUnitCore TestClassName
    ```
2. The test results will indicate if all components are functioning correctly.

## Usage

When you run the application, you will be prompted to input nodes, edges, and select a start and end point for the shortest path calculation. The application then uses Dijkstra's Algorithm to determine and display the optimal route.

## Contributing

If you'd like to contribute, please fork the repository, make your changes in a feature branch, and submit a pull request.

### Steps to contribute:

1. **Fork** the repository.
2. **Clone** your forked repository:
    ```bash
    git clone https://github.com/Tyrrnien81/shortest_path_finder.git
    ```
3. **Create a new branch** for your changes:
    ```bash
    git checkout -b feature-branch
    ```
4. **Commit your changes**:
    ```bash
    git commit -m "Description of changes"
    ```
5. **Push your branch** to your forked repository:
    ```bash
    git push origin feature-branch
    ```
6. Submit a pull request on GitHub.

## How to Exclude the `lib` Folder Contents When Pushing to the Remote Repository

If you want to push your local repository to a remote repository while excluding the contents of the `lib` folder (but keeping an empty `lib` folder), follow these steps:

1. **Edit the `.gitignore` file**: Open the `.gitignore` file in the root directory of your repository. Add the following lines to exclude all files within the `lib` folder, but keep the folder itself:

    ```plaintext
    lib/*
    !lib/.gitkeep
    ```

    This configuration will ignore all files inside `lib` but will allow a placeholder file (`.gitkeep`) to be tracked, so the folder can be pushed as empty.

2. **Create the `.gitkeep` file**: To ensure the `lib` folder itself is included in the push, add a `.gitkeep` file within it. You can do this by running:

    ```bash
    touch lib/.gitkeep
    ```

3. **Remove the existing `lib` folder contents from the remote repository**: If the remote repository already has files in the `lib` folder, you¡¯ll need to remove them. Use the following commands to remove these files from tracking, then commit the changes:

    ```bash
    git rm -r --cached lib
    git add .
    git commit -m "Remove lib contents and keep empty folder"
    ```

    This process removes the `lib` folder contents from the staging area so that the changes can be pushed to the remote repository.

4. **Push the changes to the remote repository**: Finally, push the committed changes to the remote repository:

    ```bash
    git push origin main
    ```

After completing these steps, the remote repository will have an empty `lib` folder, while the contents remain locally but are ignored from future pushes.

## License

This project is licensed under the MIT License. See the [LICENSE](https://en.wikipedia.org/wiki/MIT_License) file for details.
