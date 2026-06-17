public class GraphEdge {
    // Edge endpoints
    private GraphNode node1;
    private GraphNode node2;
    private int type;
    private String label;
    
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.node1 = u;
        this.node2 = v;
        this.type = type;
        this.label = label;
    }
    
    // Returns first endpoint
    public GraphNode firstEndpoint() {
        return node1;
    }
    
    // Returns second endpoint
    public GraphNode secondEndpoint() {
        return node2;
    }
    
    // Returns edge type (number of coins needed)
    public int getType() {
        return type;
    }
    
    // Sets edge type
    public void setType(int type) {
        this.type = type;
    }
    
    // Returns edge label ("corridor" or "door")
    public String getLabel() {
        return label;
    }
    
    // Sets edge label
    public void setLabel(String label) {
        this.label = label;
    }
}
