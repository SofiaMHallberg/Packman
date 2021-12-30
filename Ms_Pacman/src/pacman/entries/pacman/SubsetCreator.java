package pacman.entries.pacman;

import dataRecording.DataTuple;

import java.util.LinkedList;

public class SubsetCreator {
    private LinkedList<Tuple> subSet;
    private LinkedList<LinkedList<Tuple>> subSetList;

    public SubsetCreator(LinkedList<Tuple> dataSet, Attribute attribute) {
        subSetList = new LinkedList<>();
        if (attribute == Attribute.BLINKY_EDIBLE || attribute == Attribute.INKY_EDIBLE
                ||attribute == Attribute.PINKY_EDIBLE ||attribute == Attribute.SUE_EDIBLE) {
            createBooleanTagSubSet(dataSet, attribute);
        }
        else createDiscreteTagSubSet(dataSet, attribute);
    }

    private void createDiscreteTagSubSet(LinkedList<Tuple> dataSet, Attribute attribute) {
        LinkedList<Tuple> subSetVeryLow = new LinkedList<>();
        LinkedList<Tuple> subSetLow = new LinkedList<>();
        LinkedList<Tuple> subSetMedium = new LinkedList<>();
        LinkedList<Tuple> subSetHigh = new LinkedList<>();
        LinkedList<Tuple> subSetVeryHigh = new LinkedList<>(); //förlåt...

        for (int i = 0; i < dataSet.size(); i++) {
            Tuple tup = dataSet.get(i);
            switch (tup.getDiscreteValue(attribute)) {
                case VERY_LOW:
                    subSetVeryLow.add(tup);
                    break;
                case LOW:
                    subSetLow.add(tup);
                    break;
                case MEDIUM:
                    subSetMedium.add(tup);
                    break;
                case HIGH:
                    subSetHigh.add(tup);
                    break;
                case VERY_HIGH:
                    subSetVeryHigh.add(tup);
                    break;
                case NONE:
                    System.out.println("HÄR VAR DET KNAS. SubsetCreator createDiscreteTagSubset.");

            }
        }
        subSetList.add(subSetVeryLow);
        subSetList.add(subSetLow);
        subSetList.add(subSetMedium);
        subSetList.add(subSetHigh);
        subSetList.add(subSetVeryHigh);
    }

    private void createBooleanTagSubSet(LinkedList<Tuple> dataSet, Attribute attribute) {
        LinkedList<Tuple> subSetTrue = new LinkedList<>();
        LinkedList<Tuple> subSetFalse = new LinkedList<>();

        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i).getBooleanValue(attribute)) {
                subSetTrue.add(dataSet.get(i));
            } else subSetFalse.add(dataSet.get(i));
        }
        subSetList.add(subSetFalse);
        subSetList.add(subSetTrue);
    }

    public LinkedList<LinkedList<Tuple>> getSubSetList() {
        return subSetList;
    }
}
