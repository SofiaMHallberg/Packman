package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Game;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This is our implementation of a Pacman agent, that creates a decision tree based on the algorithm provided for the
 * assignment in the DA272A - Artificial Intelligence course.
 *
 * @author Sofia Hallberg, Oscar Kareld
 * 04/01-2022
 */
public class MyPacMan extends Controller<Constants.MOVE>
{
    private DecisionTreeCreator dtCreator;
    private Node root;
    private LinkedList<DataTuple> trainingDataSet;
    private LinkedList<DataTuple> testDataSet;


    public MyPacMan() throws CloneNotSupportedException {
        dtCreator = new DecisionTreeCreator();
        trainingDataSet= dtCreator.getTrainingDataSet();
        testDataSet= dtCreator.getTestDataSet();
        buildTree();
//        dtCreator.printNodeList();
    }

    /**
     * Prints the tree.
     * @param node
     */
    private void printTree(Node node) {
        System.out.println("Next node:");
        if(node.isLeafNode())
            System.out.println("Node class: "+String.valueOf(node.getMove()));
        else {
            System.out.println("Node attribute: " + String.valueOf(node.getAttribute()));
            System.out.println("Nbr of children: "+node.getNbrOfChildren());
            HashMap<String, Node> children=node.getChildNodes();
            Map.Entry<String, Node>[] nodes = children.entrySet().toArray(new Map.Entry[0]);
            for (Map.Entry<String, Node> N:nodes) {
                System.out.println(String.valueOf(N.getKey()));

            }

            LinkedList<String> values=dtCreator.getValues(node.getAttribute());
            for (String thisValue:values) {
                System.out.println("Value "+String.valueOf(thisValue)+":");
                printTree(node.getChildNodes().get(thisValue));
            }
        }
    }

    private void buildTree() throws CloneNotSupportedException {
        ArrayList<AttributeObject> attributeList=createAttributeList();
        root=dtCreator.buildTree(trainingDataSet, attributeList);
    }

    private ArrayList<AttributeObject> createAttributeList() {
        ArrayList<AttributeObject> list=new ArrayList<>();
        list.add(new AttributeObject(Attribute.PACMAN_POSITION));
        list.add(new AttributeObject(Attribute.BLINKY_EDIBLE));
        list.add(new AttributeObject(Attribute.INKY_EDIBLE));
        list.add(new AttributeObject(Attribute.PINKY_EDIBLE));
        list.add(new AttributeObject(Attribute.SUE_EDIBLE));
        list.add(new AttributeObject(Attribute.BLINKY_DISTANCE));
        list.add(new AttributeObject(Attribute.INKY_DISTANCE));
        list.add(new AttributeObject(Attribute.PINKY_DISTANCE));
        list.add(new AttributeObject(Attribute.SUE_DISTANCE));
        list.add(new AttributeObject(Attribute.BLINKY_DIR));
        list.add(new AttributeObject(Attribute.INKY_DIR));
        list.add(new AttributeObject(Attribute.PINKY_DIR));
        list.add(new AttributeObject(Attribute.SUE_DIR));
        return list;
    }

    /**
     * The method called by the controller for collecting the chosen move by the AI agent.
     * @param game A copy of the current game
     * @param timeDue The time the next move is due
     * @return the selected move
     */
    public Constants.MOVE getMove(Game game, long timeDue)
    {
        DataTuple newTuple= trainingDataSet.removeFirst(); //new DataTuple(game,null);
        return traverseTree(root, newTuple);
    }

    /**
     * This method traverses the generated decision tree.
     * @param node the root node.
     * @param tuple the
     * @return
     */
    private Constants.MOVE traverseTree(Node node, DataTuple tuple) {
        Constants.MOVE nextMove=null;
//        System.out.println("testing if for node nbr "+node.getNodeNbr());
        if(node.isLeaf()) {
            nextMove=node.getMove();
        }
        else {
            Attribute nodeAttribute=node.getAttribute();
            String valueInTuple=getAttributeValue(nodeAttribute, tuple);
            Node nextNode=node.getChild(valueInTuple); // blir tydligen null
            nextMove=traverseTree(nextNode, tuple);
        }
//        System.out.println("next move is "+String.valueOf(nextMove));
        return nextMove;
    }

    private String getAttributeValue(Attribute attribute, DataTuple tuple) {
        if(attribute==Attribute.BLINKY_EDIBLE || attribute==Attribute.INKY_EDIBLE
           || attribute==Attribute.PINKY_EDIBLE || attribute==Attribute.SUE_EDIBLE) {
            return String.valueOf(tuple.getBooleanValue(attribute));
        }
        else if(attribute==Attribute.BLINKY_DIR || attribute==Attribute.INKY_DIR
                || attribute==Attribute.PINKY_DIR || attribute==Attribute.SUE_DIR) {
            return String.valueOf(tuple.getGhostDir(attribute));
        }
        else
            return String.valueOf(tuple.getDiscreteValue(attribute));
    }

    public void validateTrainingSet() {
        int nbrOfTrainingTuples=trainingDataSet.size();
        double nbrOfTests=0;
        double nbrOfMatches=0;
        for(int i=0; i<nbrOfTrainingTuples; i++) {
            nbrOfTests++;
            Constants.MOVE decisionTreeMove=traverseTree(root, trainingDataSet.get(i));
            Constants.MOVE dataSetMove=trainingDataSet.get(i).getMove();
            if(decisionTreeMove==dataSetMove)
                nbrOfMatches++;

        }
        double hitRate=nbrOfMatches/nbrOfTests;
        DecimalFormat df=new DecimalFormat("###.##");
        System.out.println("Training set data hitrate: "+df.format(hitRate*100)+"%");

    }

    public void validateTestSet() {
        int nbrOfTestTuples=testDataSet.size();
        double nbrOfTests=0;
        double nbrOfMatches=0;
        for(int i=0; i<nbrOfTestTuples; i++) {
            nbrOfTests++;
            Constants.MOVE decisionTreeMove=traverseTree(root, testDataSet.get(i));
            Constants.MOVE dataSetMove=testDataSet.get(i).getMove();
            if(decisionTreeMove==dataSetMove)
                nbrOfMatches++;
        }
        double hitRate=nbrOfMatches/nbrOfTests;
        DecimalFormat df=new DecimalFormat("###.##");
        System.out.println("Test set data hitrate: "+df.format(hitRate*100)+"%");
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        MyPacMan pacMan=new MyPacMan();
        pacMan.validateTrainingSet();
        pacMan.validateTestSet();
    }
}
