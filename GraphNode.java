public class GraphNode {
    // Instance variables
    private int name;    // Stores the node's identifier
    private boolean mark; // Tracks if node has been visited
    
   // Constructor
    public GraphNode(int name) {
        this.name = name;
        this.mark = false; // Initially unmarked
    }
    
	// Mark or unmark the node
    public void mark(boolean mark) {
        this.mark = mark;
    }
    
	// Check if the node is marked
    public boolean isMarked() {
        return mark;
    }

	// Get the name of the node
    public int getName() {
        return name;
    }
}
