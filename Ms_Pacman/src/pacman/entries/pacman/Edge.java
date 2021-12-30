package pacman.entries.pacman;

public class Edge {
    private Node parent;
    private Node child;
    private String value;

    public Edge(Node parent, Node child, String value) {
        this.parent = parent;
        this.child = child;
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public Node getChild() {
        return child;
    }

    public String getValue() {
        return value;
    }

}
