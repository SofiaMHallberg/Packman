package pacman.entries.pacman;

import dataRecording.DataTuple;
import java.util.LinkedList;

/**
 * This class is used when generating the decision tree to create a subset of a bigger dataset. There is one
 * funtion for each of the possible subset groups.
 *
 * @author Sofia Hallberg, Oscar Kareld
 * 04/01-2022
 */
public class SubsetCreator {

    private LinkedList<DataTuple> subSetVeryLow;
    private LinkedList<DataTuple> subSetLow;
    private LinkedList<DataTuple> subSetMedium;
    private LinkedList<DataTuple> subSetHigh;
    private LinkedList<DataTuple> subSetVeryHigh;
    private LinkedList<DataTuple> subSetNone;
    private LinkedList<DataTuple> subSetTrue;
    private LinkedList<DataTuple> subSetFalse;

    private LinkedList<DataTuple> subSetUp;
    private LinkedList<DataTuple> subSetDown;
    private LinkedList<DataTuple> subSetLeft;
    private LinkedList<DataTuple> subSetRight;
    private LinkedList<DataTuple> subSetNeutral;

    public SubsetCreator(LinkedList<DataTuple> dataSet, Attribute attribute) {
        if (attribute == Attribute.BLINKY_EDIBLE || attribute == Attribute.INKY_EDIBLE
                ||attribute == Attribute.PINKY_EDIBLE ||attribute == Attribute.SUE_EDIBLE) {
            createBooleanTagSubSet(dataSet, attribute);
        }
        else if (attribute == Attribute.BLINKY_DIR || attribute == Attribute.INKY_DIR
                ||attribute == Attribute.PINKY_DIR ||attribute == Attribute.SUE_DIR) {
            createGhostDirTagSubSet(dataSet, attribute);
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
            case "NONE":
                return subSetNone;
            case "UP":
                return subSetUp;
            case "DOWN":
                return subSetDown;
            case "LEFT":
                return subSetLeft;
            case "RIGHT":
                return subSetRight;
            default:
                return subSetNeutral;
        }
    }

    private void createDiscreteTagSubSet(LinkedList<DataTuple> dataSet, Attribute attribute) {
        subSetVeryLow = new LinkedList<>();
        subSetLow = new LinkedList<>();
        subSetMedium = new LinkedList<>();
        subSetHigh = new LinkedList<>();
        subSetVeryHigh = new LinkedList<>();
        subSetNone = new LinkedList<>();

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
                    subSetNone.add(tuple);

            }
        }
    }

    private void createGhostDirTagSubSet(LinkedList<DataTuple> dataSet, Attribute attribute) {
        subSetUp = new LinkedList<>();
        subSetDown = new LinkedList<>();
        subSetLeft = new LinkedList<>();
        subSetRight = new LinkedList<>();
        subSetNeutral = new LinkedList<>();

        for (int i = 0; i < dataSet.size(); i++) {
            DataTuple tuple = dataSet.get(i);
            switch (tuple.getGhostDir(attribute)) {
                case UP:
                    subSetUp.add(tuple);
                    break;
                case DOWN:
                    subSetDown.add(tuple);
                    break;
                case LEFT:
                    subSetLeft.add(tuple);
                    break;
                case RIGHT:
                    subSetRight.add(tuple);
                    break;
                case NEUTRAL:
                    subSetNeutral.add(tuple);

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
