package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.game.Constants;

import java.util.*;

import static pacman.entries.pacman.Attribute.*;

/**
 * This class creates the decision tree used by the AI agent.
 *
 * @author Sofia Hallberg, Oscar Kareld
 * 04/01-2022
 */
public class DecisionTreeCreator {

    private Dataset dataset;
    private LinkedList<DataTuple> trainingDataSet;
    private LinkedList<DataTuple> testDataSet;
    private int nodeNbr;
    private LinkedList<Node> nodeList;
    private LinkedList<String> discreteList;
    private LinkedList<String> booleanList;
    private LinkedList<String> ghostDirList;


    public DecisionTreeCreator() throws CloneNotSupportedException {
        generateValueLists();
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

    /**
     * This recursive function follows the instructions of the provided material in the course to generate a decision
     * tree.
     * @param dataSet all the tuples left in the dataset
     * @param attributeList the list of all attributes left to choose from.
     * @return the root node of the tree.
     * @throws CloneNotSupportedException
     */
    public Node buildTree(LinkedList<DataTuple> dataSet, ArrayList<AttributeObject> attributeList) throws CloneNotSupportedException {
        Node node=new Node(nodeNbr++);                      //Create Node N.
        nodeList.add(node);

        if(oneClass(dataSet)) {                             //If every tuple in the dataset has the same class C, return
            Constants.MOVE move=dataSet.get(0).getMove();   //N as a leaf node labeled as C.
            node.setMove(move);
            return node;
        }

        if(attributeList.isEmpty()) {                               //Otherwise, if the attribute list is empty,
            Constants.MOVE majorityMove=majorityClass(dataSet);     // return N as a leaf node labeled with the majority class in D.
            node.setMove(majorityMove);
            return node;
        }

        //Otherwise
        AttributeSelector selector =
                new AttributeSelector(dataSet, attributeList); //Call the attribute selection method on D and the attribute list,
        Attribute thisAttribute = selector.selectAttribute();   //in order to choose the current attribute A
        node.setAttribute(thisAttribute);                       //Label N as A
        attributeList.remove(thisAttribute);                    //Remove A from the attribute list.
        LinkedList<String> values=getValues(thisAttribute);

        // For each value in attribute A
        //Separate all tuples in D so that attribute A takes the value a(j), creating the subset D(j)
        //If D(j) is empty, add a child node to N labeled with the majority class in D.
        //Otherwise, add the resulting node from calling Generate_Tree(D(j), attribute) as a child node to N.
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

    /**
     * Checks if the dataSet provided only contains one class.
     * @param dataSet
     * @return true if there is one class, false if there are more.
     */
    private boolean oneClass(LinkedList<DataTuple> dataSet) {
        Constants.MOVE theMove=dataSet.get(0).getMove();
        for (DataTuple tuple:dataSet) {
            if(tuple.getMove()!=theMove)
                return false;
        }
        return true;
    }

    /**
     * Finds out what the majority class for the provided dataset is
     * @param dataSet
     * @return the move corresponding to the majority class in the dataset
     */
    private Constants.MOVE majorityClass(LinkedList<DataTuple> dataSet) {
        HashMap<Constants.MOVE, Integer> nbrOfMoves=new HashMap<>();
        nbrOfMoves.put(Constants.MOVE.LEFT, 0);
        nbrOfMoves.put(Constants.MOVE.RIGHT, 0);
        nbrOfMoves.put(Constants.MOVE.UP, 0);
        nbrOfMoves.put(Constants.MOVE.DOWN, 0);
        nbrOfMoves.put(Constants.MOVE.NEUTRAL, 0);

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


    private void generateValueLists() {
        discreteList=new LinkedList<>();
        booleanList=new LinkedList<>();
        ghostDirList = new LinkedList<>();

        discreteList.add("VERY_LOW");
        discreteList.add("LOW");
        discreteList.add("MEDIUM");
        discreteList.add("HIGH");
        discreteList.add("VERY_HIGH");
        discreteList.add("NONE");
        booleanList.add("true");
        booleanList.add("false");
        ghostDirList.add("UP");
        ghostDirList.add("DOWN");
        ghostDirList.add("LEFT");
        ghostDirList.add("RIGHT");
        ghostDirList.add("NEUTRAL");
    }

    public LinkedList<String> getValues(Attribute attribute) {
        if(attribute==BLINKY_EDIBLE || attribute==INKY_EDIBLE || attribute==PINKY_EDIBLE || attribute==SUE_EDIBLE)
            return booleanList;
        else if (attribute==BLINKY_DIR || attribute==INKY_DIR || attribute==PINKY_DIR || attribute==SUE_DIR)
            return ghostDirList;
        else
            return discreteList;
    }
}
