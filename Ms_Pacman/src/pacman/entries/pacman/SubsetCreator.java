package pacman.entries.pacman;

import dataRecording.DataTuple;

import java.util.LinkedList;

public class SubsetCreator {

    private LinkedList<DataTuple> subSetVeryLow;
    private LinkedList<DataTuple> subSetLow;
    private LinkedList<DataTuple> subSetMedium;
    private LinkedList<DataTuple> subSetHigh;
    private LinkedList<DataTuple> subSetVeryHigh;
    private LinkedList<DataTuple> subSetTrue;
    private LinkedList<DataTuple> subSetFalse;

    public SubsetCreator(LinkedList<DataTuple> dataSet, Attribute attribute) {
        if (attribute == Attribute.BLINKY_EDIBLE || attribute == Attribute.INKY_EDIBLE
                ||attribute == Attribute.PINKY_EDIBLE ||attribute == Attribute.SUE_EDIBLE) {
            createBooleanTagSubSet(dataSet, attribute);
        }
        else
            createDiscreteTagSubSet(dataSet, attribute);
    }

    public LinkedList<DataTuple> getSubSet(String value) {
        switch (value) {
            case "VERY_LOW":
                return subSetVeryLow;
            case "LOW":
                return subSetLow;
            case "MEDIUM":
                return subSetMedium;
            case "HIGH":
                return subSetHigh;
            case "VERY_HIGH":
                return subSetVeryHigh;
            case "true":
                return subSetTrue;
            case "false":
                return subSetFalse;
           default:
                return null;
        }
    }

    private void createDiscreteTagSubSet(LinkedList<DataTuple> dataSet, Attribute attribute) {
        subSetVeryLow = new LinkedList<>();
        subSetLow = new LinkedList<>();
        subSetMedium = new LinkedList<>();
        subSetHigh = new LinkedList<>();
        subSetVeryHigh = new LinkedList<>();

        for (int i = 0; i < dataSet.size(); i++) {
            DataTuple tuple = dataSet.get(i);
            switch (tuple.getDiscreteValue(attribute)) {
                case VERY_LOW:
                    subSetVeryLow.add(tuple);
                    break;
                case LOW:
                    subSetLow.add(tuple);
                    break;
                case MEDIUM:
                    subSetMedium.add(tuple);
                    break;
                case HIGH:
                    subSetHigh.add(tuple);
                    break;
                case VERY_HIGH:
                    subSetVeryHigh.add(tuple);
                    break;
                case NONE:
                    subSetMedium.add(tuple);

            }
        }
    }

    private void createBooleanTagSubSet(LinkedList<DataTuple> dataSet, Attribute attribute) {
        subSetTrue = new LinkedList<>();
        subSetFalse = new LinkedList<>();

        for (int i = 0; i < dataSet.size(); i++) {
            DataTuple tuple=dataSet.get(i);
            if (tuple.getBooleanValue(attribute))
                subSetTrue.add(tuple);

            else
                subSetFalse.add(tuple);
        }
    }
}
