// === CS400 File Header Information ===
// Name: Yeongjun Jeong
// Email: yjeong62@wisc.edu
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.util.PriorityQueue;
import java.util.List;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new PlaceholderMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // implement in step 5.3
        Node startNode = nodes.get(start);
        Node endNode = nodes.get(end);

        // Check if start and end nodes exist in the graph
        if (startNode == null || endNode == null) {
            throw new NoSuchElementException("That key is not in the graph");
        }

        // Initialize the PlaceholderMap to keep track of visited nodes
        PlaceholderMap<NodeType, SearchNode> visitedNodes = new PlaceholderMap<>();

        // Initialize the PriorityQueue to keep track of the nodes to visit
        PriorityQueue<SearchNode> pathQueue = new PriorityQueue<>();
        // Add the start node to the PriorityQueue
        pathQueue.add(new SearchNode(nodes.get(start), 0, null));

        while (!pathQueue.isEmpty()) {
            SearchNode currentPath = pathQueue.poll();
            Node currentNode = currentPath.node;

            // Check if the current node is the end node
            if (currentNode == endNode) {
                return currentPath;
            }

            // Check if the current node has not been visited, then add it to visitedNodes
            if (!visitedNodes.containsKey(currentNode.data)) {
                visitedNodes.put(currentNode.data, currentPath);

                // Iterate through the edges leaving the current node
                for (Edge edge : currentNode.edgesLeaving) {
                    Node nextNode = edge.successor;
                    double newCost = currentPath.cost + edge.data.doubleValue();

                    // Check if the next node has not been visited, then add it to the PathQueue
                    if (!visitedNodes.containsKey(nextNode.data)) {
                        pathQueue.add(new SearchNode(nextNode, newCost, currentPath));
                    }
                }
            }
        }
        
        throw new NoSuchElementException("No path from " + start.toString() + " to " + end.toString());
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // implement in step 5.4

        // Use the computeShortestPath method to find the shortest path
        SearchNode shortestPath = computeShortestPath(start, end);

        if (shortestPath == null) {
            // If no path is found, throw an exception
            throw new NoSuchElementException("No path exists from start to end.");
        }

        // Create a list to store the data values of the nodes along the shortest path
        LinkedList<NodeType> pathData = new LinkedList<>();

        // Iterate through the path and add the data to the pathData LinkedList
        while (shortestPath != null) {
            // Add the data value of the current node to the beginning of the list
            pathData.add(0, shortestPath.node.data);
            // Move to the predecessor of the current node
            shortestPath = shortestPath.predecessor;
        }

        return pathData;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // implement in step 5.4
        return computeShortestPath(start, end).cost;
    }


    // TODO: implement 3+ tests in step 4.1
    /*
     * The test that makes use of an example that you traced through in lecture, 
     * and confirm that the results of your implementation match what you previously computed by hand.
     */
    @Test
    public void testLectureExample() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        Assertions.assertEquals(8, graph.shortestPathCost("A", "E"));
        Assertions.assertEquals("[A, B, D, E]", graph.shortestPathData("A", "E").toString());

        Assertions.assertEquals(5, graph.shortestPathCost("A", "F"));
        Assertions.assertEquals("[A, B, D, F]", graph.shortestPathData("A", "F").toString());
    }

    /*
     * The test using the same graph as you did for the test above, 
     * but check the cost and sequence of data along the shortest path between a different start and end node.
     */
    @Test
    public void testDifferentStart() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        Assertions.assertEquals(4, graph.shortestPathCost("B", "E"));
        Assertions.assertEquals("[B, D, E]", graph.shortestPathData("B", "E").toString());
    }

    /*
     * The test that checks the behavior of your implementation when the nodes 
     * that you are searching for a path between existing nodes in the graph, 
     * but there is no sequence of directed edges that connects them from the start to the end.
     */
    @Test
    public void testNoPath() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        Assertions.assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath("B", "A"));
    }
}
