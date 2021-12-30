package pacman.entries.pacman;

import pacman.game.Constants;

import java.util.LinkedList;

public class Node {
    private Attribute attribute;
    private Constants.MOVE move;
    private LinkedList<Edge> edges;
    private boolean isLeaf = false;


    public Node() {
        edges = new LinkedList<>();
    }

    public void setMove(Constants.MOVE move) {
        this.move=move;
        isLeaf = true;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
    public boolean isLeaf() {
        return isLeaf;
    }

}
