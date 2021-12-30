package pacman.entries.pacman;

import java.util.LinkedList;

public class SubsetCreator {
    private LinkedList<String[]> subSet;
    private LinkedList<LinkedList<String[]>> subSetList;
    private Attribute attribute;

    public SubsetCreator(LinkedList<String[]> dataSet, Attribute attribute) {
        switch (attribute) {
            case PACMAN_POSITION:
                createDiscreteTagSubSet(dataSet,1, Attribute.PACMAN_POSITION);
            case BLINKY_EDIBLE:

            case INKY_EDIBLE:

            case PINKY_EDIBLE:

            case SUE_EDIBLE:

            case BLINKY_DISTANCE:

            case INKY_DISTANCE:

            case PINKY_DISTANCE:

            case SUE_DISTANCE:
        }
    }

    private void createDiscreteTagSubSet(LinkedList<String[]> dataSet, int column, Attribute attribute) {
        for (int i=0; i<dataSet.size(); i++) {
            String[] tuple=dataSet.get(i);
            if(tuple[column].equals(attribute.toString())) {
                subSet.add(tuple);
            }
        }
    }

    private void createSubset(LinkedList<String[]> dataSet, Attribute attribute, int column) {
        for (int i=0; i<dataSet.size(); i++) {
            Attribute attr tuple=dataSet.get(i)[column];
            if(dataSet.get(i)[column].equals(attribute.toString())) {
                subSet.add(dataSet.get(i))
            }
        }

    }
}
