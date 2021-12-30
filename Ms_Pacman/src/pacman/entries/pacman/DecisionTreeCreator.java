package pacman.entries.pacman;

import pacman.game.Constants;

import java.util.LinkedList;

public class DecisionTreeCreator {
    private Dataset dataset;
    private LinkedList<Tuple> trainingDataSet;
    private LinkedList<Tuple> testDataSet;
//    private AttributeList attributeList;


    public DecisionTreeCreator() {
        dataset = new Dataset();
        trainingDataSet = dataset.getTrainingDataSet();
        testDataSet = dataset.getTestDataSet();
//        attributeList = new AttributeList();
        buildTree(trainingDataSet, new AttributeList());
    }

    public Node buildTree(LinkedList<Tuple> dataSet, AttributeList attributeList) {
        Node node = new Node();
        Constants.MOVE move = multipleClasses(dataSet);
        if (move != null) {
            node.setMove(move);
            return node;
        }

        if (attributeList.isEmpty()) {
            node.setMove(majorityClass(dataSet));
            return node;
        }

        AttributeSelector selector = new AttributeSelector(dataSet, attributeList);
        Attribute thisAttribute = selector.selectAttribute();
        node.setAttribute(thisAttribute);
        attributeList.removeAttribute(thisAttribute);


        SubsetCreator creator = new SubsetCreator(dataSet, thisAttribute);
        LinkedList<LinkedList<Tuple>> subSetList = creator.getSubSetList();

        for (int i = 0; i < subSetList.size(); i++) {
            Node childNode = new Node();
            Edge edge = new Edge(node, childNode, thisAttribute)
            if (subSetList.get(i).isEmpty()) {
                childNode.setMove(majorityClass(subSetList.get(i)));
            }
            else {
                childNode = buildTree(subSetList.get(i), attributeList);

            }
        }

        return null;
    }


    private Constants.MOVE majorityClass(LinkedList<Tuple> dataSet) {
        int nbrOfUp = 0;
        int nbrOfDown = 0;
        int nbrOfRight = 0;
        int nbrOfLeft = 0;

        for (int i = 0; i < dataSet.size(); i++) {
            switch (dataSet.get(i).getMoveClass()) {
                case LEFT:
                    nbrOfLeft++;
                    break;
                case RIGHT:
                    nbrOfRight++;
                    break;
                case UP:
                    nbrOfUp++;
                    break;
                case DOWN:
                    nbrOfDown++;
                    break;
            }
        }
        if (nbrOfUp >= nbrOfDown && nbrOfUp >= nbrOfRight && nbrOfUp >= nbrOfLeft)
            return Constants.MOVE.UP;
        if (nbrOfDown >= nbrOfUp && nbrOfDown >= nbrOfRight && nbrOfDown >= nbrOfLeft)
            return Constants.MOVE.DOWN;
        if (nbrOfRight >= nbrOfDown && nbrOfRight >= nbrOfUp && nbrOfRight >= nbrOfLeft)
            return Constants.MOVE.RIGHT;
        if (nbrOfLeft >= nbrOfDown && nbrOfLeft >= nbrOfRight && nbrOfLeft >= nbrOfUp)
            return Constants.MOVE.UP;

        return null;
    }

    private Constants.MOVE multipleClasses(LinkedList<Tuple> dataSet) {
        Constants.MOVE myClass = dataSet.getFirst().getMoveClass();
        for (int i = 1; i < dataSet.size(); i++) {
            if (dataSet.get(i).getMoveClass() != myClass)
                return null;
        }
        return myClass;
    }
}
