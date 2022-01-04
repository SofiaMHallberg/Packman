package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.game.Constants;

import java.util.*;

import static pacman.entries.pacman.Attribute.*;

public class DecisionTreeCreator {

    private Dataset dataset;
    private LinkedList<DataTuple> trainingDataSet;
    private LinkedList<DataTuple> testDataSet;
    private int nodeNbr;
    private LinkedList<Node> nodeList;


    public DecisionTreeCreator() throws CloneNotSupportedException {
        dataset = new Dataset();
        trainingDataSet = dataset.getTrainingDataSet();
        testDataSet = dataset.getTestDataSet();
        nodeNbr=0;
        nodeList=new LinkedList<>();
    }

    public void printAttributeList(AttributeList list) {
        System.out.println("AttributeList:");
        for (int i=0; i<list.getSize();i++) {
            System.out.println(String.valueOf(list.getList().get(i).getAttribute()));
        }
        System.out.println();
    }

    public void printNodeList() {
        for(int i=0; i<nodeList.size(); i++) {
            System.out.println("nodeNbr: "+nodeList.get(i).getNodeNbr()+", attribute: "+String.valueOf(nodeList.get(i).getAttribute())+", class: "+String.valueOf(nodeList.get(i).getMove()));
        }
    }

    public Node buildTree(LinkedList<DataTuple> dataSet, ArrayList<AttributeObject> attributeList) throws CloneNotSupportedException {
        Node node=new Node(nodeNbr++);
        nodeList.add(node);

        if(oneClass(dataSet)) {
            Constants.MOVE move=dataSet.get(0).getMove();
            node.setMove(move);
            return node;
        }

        if(attributeList.isEmpty()) {
            Constants.MOVE majorityMove=majorityClass(dataSet);
            node.setMove(majorityMove);
            return node;
        }

        AttributeSelector2 selector = new AttributeSelector2(dataSet, attributeList);
        Attribute thisAttribute = selector.selectAttribute();
        node.setAttribute(thisAttribute);
        attributeList.remove(thisAttribute);
        LinkedList<String> values=getValues(thisAttribute);

        for (String thisValue:values) {
            ArrayList<AttributeObject> copyOfAttributeList=(ArrayList<AttributeObject>) attributeList.clone();
            SubsetCreator creator = new SubsetCreator(dataSet, thisAttribute);
            LinkedList<DataTuple> subSet = creator.getSubSet(thisValue);

            if(subSet.isEmpty()) {
                Node childNode=new Node(nodeNbr++);
                nodeList.add(childNode);
                childNode.setMove(majorityClass(dataSet));
                node.addChild(thisValue,childNode);
            }
            else {
                node.addChild(thisValue,buildTree(subSet,copyOfAttributeList));
            }
        }
        return node;
    }

    private boolean oneClass(LinkedList<DataTuple> dataSet) {
        Constants.MOVE theMove=dataSet.get(0).getMove();
        for (DataTuple tuple:dataSet) {
            if(tuple.getMove()!=theMove)
                return false;
        }
        return true;
    }

    private Constants.MOVE majorityClass(LinkedList<DataTuple> dataSet) {
        HashMap<Constants.MOVE, Integer> nbrOfMoves=new HashMap<>();
        nbrOfMoves.put(Constants.MOVE.LEFT, 0);
        nbrOfMoves.put(Constants.MOVE.RIGHT, 0);
        nbrOfMoves.put(Constants.MOVE.UP, 0);
        nbrOfMoves.put(Constants.MOVE.DOWN, 0);

        for (DataTuple tuple:dataSet) {
            nbrOfMoves.put(tuple.getMove(),nbrOfMoves.get(tuple.getMove())+1);
        }

        Constants.MOVE majorityMove=null;
        int maxNbrOfMoves= Collections.max(nbrOfMoves.values());
        for (Map.Entry<Constants.MOVE, Integer> entry:nbrOfMoves.entrySet()) {
            if(entry.getValue()==maxNbrOfMoves)
                majorityMove=entry.getKey();
        }

        return majorityMove;
    }

    public LinkedList<DataTuple> getTrainingDataSet() {
        return trainingDataSet;
    }

    public LinkedList<DataTuple> getTestDataSet() {
        return testDataSet;
    }

    public LinkedList<String> getValues(Attribute attribute) {
        LinkedList<String> discreteList=new LinkedList<>();
        discreteList.add("VERY_LOW");
        discreteList.add("LOW");
        discreteList.add("MEDIUM");
        discreteList.add("HIGH");
        discreteList.add("VERY_HIGH");
        LinkedList<String> booleanList=new LinkedList<>();
        booleanList.add("true");
        booleanList.add("false");

        if(attribute==BLINKY_EDIBLE || attribute==INKY_EDIBLE || attribute==PINKY_EDIBLE || attribute==SUE_EDIBLE)
            return booleanList;
        else
            return discreteList;
    }
}
