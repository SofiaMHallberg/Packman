package pacman.entries.pacman;

import pacman.game.Constants;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class stores all information necessary for the nodes in the decision tree.
 *
 * @author Sofia Hallberg, Oscar Kareld
 * 04/01-2022
 */
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

    public void startPrinting() {
        print("");
    }
    private void print(String branch) {
        if (this.isLeaf) {
            System.out.print(branch);
            System.out.println("---(LEAF) RETURN: " + getMove());
        }
        Map.Entry<String, Node>[] childrenNodes = childNodes.entrySet().toArray(new Map.Entry[0]);
        for (int i = 0; i < childrenNodes.length; i++) {
            System.out.print(branch);
            if (i == childrenNodes.length - 1 && getMove() != null) {
                System.out.println("---" + getMove() + " = " + childrenNodes[i].getKey() + ":");
                childrenNodes[i].getValue().print(branch + "    ");
            } else {
                System.out.println("---" + getAttribute() + " = " + childrenNodes[i].getKey() + ":");
                childrenNodes[i].getValue().print(branch + "|" +"   ");
            }
        }
    }

}
