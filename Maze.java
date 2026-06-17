import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Maze {
    private Graph graph;
    private GraphNode entrance;
    private GraphNode exit;
    private int coins;
    private List<GraphNode> path;
	
    // Constructor
    public Maze(String inputFile) throws MazeException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            readInput(reader);
            reader.close();
        } catch (IOException | GraphException e) {
            throw new MazeException("Error reading maze file: " + e.getMessage());
        }
    }
	// Get the graph
	public Graph getGraph() throws MazeException {
		if (graph == null) {
			throw new MazeException("Graph is null");
		}
		return graph;
	}

	// Solve the maze using DFS
    public Iterator<GraphNode> solve() throws MazeException {
        // Reset all node marks
        try {
            path = new ArrayList<>();
            Iterator<GraphNode> result = DFS(coins, entrance);
            if (result != null) {
                return result;
            }
        } catch (GraphException e) {
            return null;
        }
        return null;
    }

    private Iterator<GraphNode> DFS(int remainingCoins, GraphNode current) throws GraphException, MazeException {
        // Mark current node as visited
        current.mark(true);
        path.add(current);
        
        // Base case: reached exit
        if (current == exit) {
            return path.iterator();
        }
        
        // Get all edges connected to current node
        Iterator<GraphEdge> edges = graph.incidentEdges(current);
        if (edges != null) {
            while (edges.hasNext()) {
                GraphEdge edge = edges.next();
                GraphNode next = edge.firstEndpoint() == current ? 
                    edge.secondEndpoint() : edge.firstEndpoint();
                
                // Skip if already visited
                if (next.isMarked()) {
                    continue;
                }
                
                // Calculate coins needed for this edge
                int coinsNeeded = edge.getLabel().equals("door") ? edge.getType() : 0;
                
                // Only proceed if we have enough coins
                if (coinsNeeded <= remainingCoins) {
                    Iterator<GraphNode> result = DFS(remainingCoins - coinsNeeded, next);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        
        // Backtrack: unmark node and remove from path
        current.mark(false);
        path.remove(path.size() - 1);
        return null;
    }

    private void readInput(BufferedReader reader) throws IOException, GraphException, MazeException {
        // Read maze parameters
        int scale = Integer.parseInt(reader.readLine());
        int width = Integer.parseInt(reader.readLine());
        int length = Integer.parseInt(reader.readLine());
        coins = Integer.parseInt(reader.readLine());
        
        // Initialize graph
        graph = new Graph(width * length);
        
        // Read maze structure
        char[][] maze = new char[2 * length - 1][2 * width - 1];
        for (int i = 0; i < 2 * length - 1; i++) {
            String line = reader.readLine();
            for (int j = 0; j < 2 * width - 1; j++) {
                maze[i][j] = line.charAt(j);
                
                // Find entrance and exit
                if (maze[i][j] == 's') {
                    entrance = graph.getNode(i/2 * width + j/2);
                } else if (maze[i][j] == 'x') {
                    exit = graph.getNode(i/2 * width + j/2);
                }
            }
        }
        
        // Process horizontal connections
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width - 1; j++) {
                char connection = maze[2*i][2*j + 1];
                if (connection != 'w') {
                    String label = Character.isDigit(connection) ? "door" : "corridor";
                    int type = Character.isDigit(connection) ? 
                        Character.getNumericValue(connection) : 0;
                    insertEdge(i * width + j, i * width + j + 1, type, label);
                }
            }
        }
        
        // Process vertical connections
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < width; j++) {
                char connection = maze[2*i + 1][2*j];
                if (connection != 'w') {
                    String label = Character.isDigit(connection) ? "door" : "corridor";
                    int type = Character.isDigit(connection) ? 
                        Character.getNumericValue(connection) : 0;
                    insertEdge(i * width + j, (i + 1) * width + j, type, label);
                }
            }
        }
    }

    private void insertEdge(int node1, int node2, int linkType, String label) 
            throws GraphException, MazeException {
        GraphNode n1 = graph.getNode(node1);
        GraphNode n2 = graph.getNode(node2);
        graph.insertEdge(n1, n2, linkType, label);
    }
}
