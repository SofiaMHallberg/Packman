package pacman.entries.pacman;

import pacman.game.Constants;

import java.util.HashMap;
import java.util.LinkedList;

public class Node {
    private Attribute attribute;
    private Constants.MOVE move;
    private boolean isLeaf;
    private HashMap<String,Node> childNodes;
    private int nodeNbr;


    public Node() {
        childNodes=new HashMap<>();
    }

    public Node(int nodeNbr) {
        this.nodeNbr=nodeNbr;
        childNodes=new HashMap<>();
    }

    public int getNodeNbr() {
        return nodeNbr;
    }

    public void addChild(String value, Node child) {
        childNodes.put(value, child);
    }

    public Node getChild(String value) {
        return childNodes.get(value);
    }

    public HashMap<String, Node> getChildNodes() {
        return childNodes;
    }

    public void setMove(Constants.MOVE move) {
        this.move=move;
        isLeaf = true;
        //System.out.println("move is set to "+String.valueOf(move));
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }


    public boolean isLeaf() {
        return isLeaf;
    }

    public boolean isLeafNode() {
        if(childNodes.isEmpty())
            return true;
        else
            return false;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Constants.MOVE getMove() {
        return move;
    }

    public int getNbrOfChildren() {
        return childNodes.size();
    }

}
