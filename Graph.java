import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph implements GraphADT {
	
	// Store nodes array and adjacency list
	private GraphNode[] nodes;
	private Map<GraphNode, List<GraphEdge>> adjacencyList;
	
	public Graph(int n) {
		// Initialize nodes array
		nodes = new GraphNode[n];
		for (int i = 0; i < n; i++) {
			nodes[i] = new GraphNode(i);
		}
		
		// Initialize adjacency list
		adjacencyList = new HashMap<>();
		for (GraphNode node : nodes) {
			adjacencyList.put(node, new ArrayList<>());
		}
	}
	
	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) 
			throws GraphException {
		// Check if nodes exist
		if (!adjacencyList.containsKey(nodeu) || !adjacencyList.containsKey(nodev)) {
			throw new GraphException("Node does not exist");
		}
		
		// Check if edge already exists
		if (areAdjacent(nodeu, nodev)) {
			throw new GraphException("Edge already exists");
		}
		
		// Create edge and add to both nodes' lists
		GraphEdge edge = new GraphEdge(nodeu, nodev, type, label);
		adjacencyList.get(nodeu).add(edge);
		adjacencyList.get(nodev).add(edge);
	}

	@Override
	public GraphNode getNode(int u) throws GraphException {
		// Check if node index is valid
		if (u < 0 || u >= nodes.length) {
			throw new GraphException("Invalid node index");
		}
		return nodes[u];
	}

	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		// Check if node exists
		if (!adjacencyList.containsKey(u)) {
			throw new GraphException("Node does not exist");
		}
		
		List<GraphEdge> edges = adjacencyList.get(u);
		return edges.isEmpty() ? null : edges.iterator();
	}

	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		// Check if nodes exist
		if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)) {
			throw new GraphException("Node does not exist");
		}
		
		// Get edges incident to u
		List<GraphEdge> edges = adjacencyList.get(u);
		
		// Find edge connecting to v
		for (GraphEdge edge : edges) {
			if ((edge.firstEndpoint() == u && edge.secondEndpoint() == v) ||
				(edge.firstEndpoint() == v && edge.secondEndpoint() == u)) {
				return edge;
			}
		}
		
		// No edge found
		throw new GraphException("Edge does not exist");
	}

	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		try {
			// Use getEdge to check if edge exists
			getEdge(u, v);
			return true;
		} catch (GraphException e) {
			return false;
		}
	}
}
