package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.game.Constants.MOVE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import static pacman.entries.pacman.Attribute.*;

/**
 * The class responsible for selecting attributes and calculating the gain of each of the attributes according to the dataset provided
 *
 * @author Sofia Hallberg, Oscar Kareld
 * 04/01-2022
 */
public class AttributeSelector {
    private LinkedList<DataTuple> dataSet;
    private ArrayList<AttributeObject>  attributeList;
    private double nbrOfTuples;
    private double nbrOfUp;
    private double nbrOfDown;
    private double nbrOfRight;
    private double nbrOfLeft;

    public AttributeSelector(LinkedList<DataTuple> dataSet, ArrayList<AttributeObject> attributeList) {
        this.dataSet=dataSet;
        this.attributeList=attributeList;
        //System.out.println("A new Attribute selector is created for a list with "+attributeList.size()+" elements");
    }

    public Attribute selectAttribute() {
        //System.out.println("selectAttribute() is called");
        nbrOfTuples=dataSet.size();
        nbrOfUp=0;
        nbrOfDown=0;
        nbrOfRight=0;
        nbrOfLeft=0;

        for (DataTuple tuple:dataSet) {
            switch (tuple.getMove()) {
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

//        System.out.println("nbrOfTuples: "+nbrOfTuples);
//        System.out.println("nbrOfUp: "+nbrOfUp);
//        System.out.println("nbrOfDown: "+nbrOfDown);
//        System.out.println("nbrOfRight: "+nbrOfRight);
//        System.out.println("nbrOfLeft: "+nbrOfLeft);

        double averageInfo=getAverageInfo();
        //System.out.println("averageInfo: "+averageInfo);

        for (AttributeObject attributeObject:attributeList) {
            switch(attributeObject.getAttribute()) {
                case PACMAN_POSITION:
                    double infoInPosition=getDiscreteTagInfo(PACMAN_POSITION);
                    double gainPosition=averageInfo-infoInPosition;
                    setGain(gainPosition, PACMAN_POSITION);
                    break;
                case BLINKY_EDIBLE:
                    double infoInBlinkyEdible=getBooleanTagInfo(BLINKY_EDIBLE);
                    double gainBlinkyEdible=averageInfo-infoInBlinkyEdible;
                    setGain(gainBlinkyEdible, BLINKY_EDIBLE);
                    break;
                case INKY_EDIBLE:
                    double infoInInkyEdible=getBooleanTagInfo(INKY_EDIBLE);
                    double gainInkyEdible=averageInfo-infoInInkyEdible;
                    setGain(gainInkyEdible, INKY_EDIBLE);
                    break;
                case PINKY_EDIBLE:
                    double infoInPinkyEdible=getBooleanTagInfo(Attribute.PINKY_EDIBLE);
                    double gainPinkyEdible=averageInfo-infoInPinkyEdible;
                    setGain(gainPinkyEdible, Attribute.PINKY_EDIBLE);
                    break;
                case SUE_EDIBLE:
                    double infoInSueEdible=getBooleanTagInfo(Attribute.SUE_EDIBLE);
                    double gainSueEdible=averageInfo-infoInSueEdible;
                    setGain(gainSueEdible, Attribute.SUE_EDIBLE);
                    break;
                case BLINKY_DISTANCE:
                    double infoInBlinkyDistance=getDiscreteTagInfo(Attribute.BLINKY_DISTANCE);
                    double gainBlinkyDist=averageInfo-infoInBlinkyDistance;
                    setGain(gainBlinkyDist, Attribute.BLINKY_DISTANCE);
                    break;
                case INKY_DISTANCE:
                    double infoInInkyDistance=getDiscreteTagInfo(Attribute.INKY_DISTANCE);
                    double gainInkyDist=averageInfo-infoInInkyDistance;
                    setGain(gainInkyDist, Attribute.INKY_DISTANCE);
                    break;
                case PINKY_DISTANCE:
                    double infoInPinkyDistance=getDiscreteTagInfo(Attribute.PINKY_DISTANCE);
                    double gainPinkyDist=averageInfo-infoInPinkyDistance;
                    setGain(gainPinkyDist, Attribute.PINKY_DISTANCE);
                    break;
                case SUE_DISTANCE:
                    double infoInSueDistance=getDiscreteTagInfo(Attribute.SUE_DISTANCE);
                    double gainSueDist=averageInfo-infoInSueDistance;
                    setGain(gainSueDist, Attribute.SUE_DISTANCE);
                    break;
                case BLINKY_DIR:
                    double infoInBlinkyDir=getGhostDirInfo(Attribute.BLINKY_DIR);
                    double gainBlinkyDir=averageInfo-infoInBlinkyDir;
                    setGain(gainBlinkyDir, Attribute.BLINKY_DIR);
                    break;
                case INKY_DIR:
                    double infoInInkyDir=getGhostDirInfo(Attribute.INKY_DIR);
                    double gainInkyDir=averageInfo-infoInInkyDir;
                    setGain(gainInkyDir, Attribute.INKY_DIR);
                    break;
                case PINKY_DIR:
                    double infoInPinkyDir=getGhostDirInfo(Attribute.PINKY_DIR);
                    double gainPinkyDir=averageInfo-infoInPinkyDir;
                    setGain(gainPinkyDir, Attribute.PINKY_DIR);
                    break;
                case SUE_DIR:
                    double infoInSueDir=getGhostDirInfo(Attribute.SUE_DIR);
                    double gainSueDir=averageInfo-infoInSueDir;
                    setGain(gainSueDir, Attribute.SUE_DIR);
                    break;
            }
        }

        Collections.sort(attributeList);
        return attributeList.remove(0).getAttribute();
    }

    private void setGain(double gain, Attribute attribute) {
        for (AttributeObject attributeObject:attributeList) {
            if (attributeObject.getAttribute() == attribute) {
                attributeObject.setGain(gain);
                break;
            }
        }
    }

    private double getBooleanTagInfo(Attribute attribute) {
        double nbrOfTrue=0;
        double nbrOfFalse=0;

        switch (attribute) {
            case BLINKY_EDIBLE:
                for (DataTuple tuple:dataSet) {
                    if(tuple.getBlinkyEdible())
                        nbrOfTrue++;
                    else
                        nbrOfFalse++;
                }
                break;
            case INKY_EDIBLE:
                for (DataTuple tuple:dataSet) {
                    if(tuple.getInkyEdible())
                        nbrOfTrue++;
                    else
                        nbrOfFalse++;
                }
                break;
            case PINKY_EDIBLE:
                for (DataTuple tuple:dataSet) {
                    if(tuple.getPinkyEdible())
                        nbrOfTrue++;
                    else
                        nbrOfFalse++;
                }
                break;
            case SUE_EDIBLE:
                for (DataTuple tuple:dataSet) {
                    if(tuple.getSueEdible())
                        nbrOfTrue++;
                    else
                        nbrOfFalse++;
                }
                break;
        }
        double infoInTag=nbrOfTrue/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfTrue))+
                         nbrOfFalse/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfFalse));

        return infoInTag;
    }

    private double getGhostDirInfo(Attribute attribute) {
        double nbrOfGhostUp = 0;
        double nbrOfGhostDown = 0;
        double nbrOfGhostLeft = 0;
        double nbrOfGhostRight = 0;

        switch (attribute) {
            case BLINKY_DIR:
                for (DataTuple tuple:dataSet) {
                    if (tuple.getBlinkyDir() == MOVE.UP)
                        nbrOfGhostUp++;
                    else if (tuple.getBlinkyDir() == MOVE.DOWN)
                        nbrOfGhostDown++;
                    else if (tuple.getBlinkyDir() == MOVE.LEFT)
                        nbrOfGhostLeft++;
                    else
                        nbrOfGhostRight++;
                }
                break;
            case INKY_DIR:
                for (DataTuple tuple:dataSet) {
                    if (tuple.getInkyDir() == MOVE.UP)
                        nbrOfGhostUp++;
                    else if (tuple.getInkyDir() == MOVE.DOWN)
                        nbrOfGhostDown++;
                    else if (tuple.getInkyDir() == MOVE.LEFT)
                        nbrOfGhostLeft++;
                    else
                        nbrOfGhostRight++;
                }
                break;
            case PINKY_DIR:
                for (DataTuple tuple:dataSet) {
                    if (tuple.getPinkyDir() == MOVE.UP)
                        nbrOfGhostUp++;
                    else if (tuple.getPinkyDir() == MOVE.DOWN)
                        nbrOfGhostDown++;
                    else if (tuple.getPinkyDir() == MOVE.LEFT)
                        nbrOfGhostLeft++;
                    else
                        nbrOfGhostRight++;
                }
                break;
            case SUE_DIR:
                for (DataTuple tuple:dataSet) {
                    if (tuple.getSueDir() == MOVE.UP)
                        nbrOfGhostUp++;
                    else if (tuple.getSueDir() == MOVE.DOWN)
                        nbrOfGhostDown++;
                    else if (tuple.getSueDir() == MOVE.LEFT)
                        nbrOfGhostLeft++;
                    else
                        nbrOfGhostRight++;
                }
                break;
        }

        double infoInTag=nbrOfGhostUp/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfGhostUp))+
                         nbrOfGhostDown/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfGhostDown))+
                         nbrOfGhostLeft/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfGhostLeft))+
                         nbrOfGhostRight/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfGhostRight));

        return infoInTag;
    }

    private double getDiscreteTagInfo(Attribute attribute) {
        double nbrOfVeryLow=0;
        double nbrOfLow=0;
        double nbrOfMedium=0;
        double nbrOfHigh=0;
        double nbrOfVeryHigh=0;

        switch (attribute) {
            case PACMAN_POSITION:
                for (DataTuple tuple:dataSet) {
                    if(tuple.getPosition()==DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    else if(tuple.getPosition()==DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    else if(tuple.getPosition()==DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    else if(tuple.getPosition()==DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    else
                        nbrOfVeryHigh++;
                }
                break;
            case BLINKY_DISTANCE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    else if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    else if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    else if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    else
                        nbrOfVeryHigh++;

                }
                break;
            case INKY_DISTANCE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    else if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    else if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    else if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    else
                        nbrOfVeryHigh++;
                }
                break;
            case PINKY_DISTANCE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    else if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    else if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    else if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    else
                        nbrOfVeryHigh++;
                }
                break;
            case SUE_DISTANCE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    else if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    else if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    else if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    else
                        nbrOfVeryHigh++;

                }
                break;
        }

        double infoInTag=nbrOfVeryLow/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfVeryLow))+
                         nbrOfLow/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfLow))+
                         nbrOfMedium/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfMedium))+
                         nbrOfHigh/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfHigh))+
                         nbrOfVeryHigh/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfVeryHigh));

        return infoInTag;
    }

    private double infoInDiscreteTagInfo(double nbrOfTags) {

        double tagUp=(nbrOfUp/nbrOfTags)*log2(nbrOfUp/nbrOfTags);
        double tagDown=(nbrOfDown/nbrOfTags)*log2(nbrOfDown/nbrOfTags);
        double tagRight=(nbrOfRight/nbrOfTags)*log2(nbrOfRight/nbrOfTags);
        double tagLeft=(nbrOfLeft/nbrOfTags)*log2(nbrOfLeft/nbrOfTags);

        return -tagUp-tagDown-tagRight-tagLeft;
    }

    private double log2(double value) {
        return  Math.log(value)/Math.log(2);
    }

    private double getAverageInfo() {
        double infoInLeft=(nbrOfLeft/nbrOfTuples)*log2(nbrOfLeft/nbrOfTuples);
        double infoInRight=(nbrOfRight/nbrOfTuples)*log2(nbrOfRight/nbrOfTuples);
        double infoInUp=-(nbrOfUp/nbrOfTuples)*log2(nbrOfUp/nbrOfTuples);
        double infoInDown=(nbrOfDown/nbrOfTuples)*log2(nbrOfDown/nbrOfTuples);

        return -infoInLeft-infoInRight-infoInUp-infoInDown;
    }
}
