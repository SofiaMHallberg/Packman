package pacman.entries.pacman;

import dataRecording.DataTuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import static pacman.entries.pacman.Attribute.*;
import static pacman.entries.pacman.Attribute.INKY_EDIBLE;

public class AttributeSelector2 {
    private LinkedList<DataTuple> dataSet;
    private ArrayList<AttributeObject>  attributeList;
    private double nbrOfTuples;
    private double nbrOfUp;
    private double nbrOfDown;
    private double nbrOfRight;
    private double nbrOfLeft;

    public AttributeSelector2(LinkedList<DataTuple> dataSet, ArrayList<AttributeObject> attributeList) {
        this.dataSet=dataSet;
        this.attributeList=attributeList;
    }

    public Attribute selectAttribute() {
        nbrOfTuples=dataSet.size();
        nbrOfUp=0;
        nbrOfDown=0;
        nbrOfRight=0;
        nbrOfLeft=0;

        for (DataTuple tuple:dataSet) {
            switch (tuple.DirectionChosen) {
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

        double averageInfo=getAverageInfo(nbrOfTuples, nbrOfLeft, nbrOfRight, nbrOfUp, nbrOfDown);
        //System.out.println("average info: "+averageInfo);

        ArrayList<AttributeObject> list=attributeList;
        for (AttributeObject attributeObject:list) {
            switch(attributeObject.getAttribute()) {
                case PACMAN_POSITION:
                    double infoInPosition=getDiscreteTagInfo(PACMAN_POSITION);
                    double gainPosition=averageInfo-infoInPosition;
                    //System.out.println("gain in position: "+gainPosition);
                    setGain(gainPosition, PACMAN_POSITION);
                case BLINKY_EDIBLE:
                    double infoInBlinkyEdible=getBooleanTagInfo(BLINKY_EDIBLE);
                    double gainBlinkyEdible=averageInfo-infoInBlinkyEdible;
                    //System.out.println("gain in blinkyedible: "+gainBlinkyEdible);
                    setGain(gainBlinkyEdible, BLINKY_EDIBLE);
                case INKY_EDIBLE:
                    double infoInInkyEdible=getBooleanTagInfo(INKY_EDIBLE);
                    double gainInkyEdible=averageInfo-infoInInkyEdible;
                    setGain(gainInkyEdible, INKY_EDIBLE);
                case PINKY_EDIBLE:
                    double infoInPinkyEdible=getBooleanTagInfo(Attribute.PINKY_EDIBLE);
                    double gainPinkyEdible=averageInfo-infoInPinkyEdible;
                    setGain(gainPinkyEdible, Attribute.PINKY_EDIBLE);
                case SUE_EDIBLE:
                    double infoInSueEdible=getBooleanTagInfo(Attribute.SUE_EDIBLE);
                    double gainSueEdible=averageInfo-infoInSueEdible;
                    setGain(gainSueEdible, Attribute.SUE_EDIBLE);
                case BLINKY_DISTANCE:
                    double infoInBlinkyDistance=getDiscreteTagInfo(Attribute.BLINKY_DISTANCE);
                    double gainBlinkyDist=averageInfo-infoInBlinkyDistance;
                    setGain(gainBlinkyDist, Attribute.BLINKY_DISTANCE);
                case INKY_DISTANCE:
                    double infoInInkyDistance=getDiscreteTagInfo(Attribute.INKY_DISTANCE);
                    double gainInkyDist=averageInfo-infoInInkyDistance;
                    setGain(gainInkyDist, Attribute.INKY_DISTANCE);
                case PINKY_DISTANCE:
                    double infoInPinkyDistance=getDiscreteTagInfo(Attribute.PINKY_DISTANCE);
                    double gainPinkyDist=averageInfo-infoInPinkyDistance;
                    setGain(gainPinkyDist, Attribute.PINKY_DISTANCE);
                case SUE_DISTANCE:
                    double infoInSueDistance=getDiscreteTagInfo(Attribute.SUE_DISTANCE);
                    double gainSueDist=averageInfo-infoInSueDistance;
                    setGain(gainSueDist, Attribute.SUE_DISTANCE);
            }
        }

        Collections.sort(list);
        System.out.println("selected attribute: "+String.valueOf(list.get(0).getAttribute()));
        return list.remove(0).getAttribute();
    }

    private void setGain(double gain, Attribute attribute) {
        ArrayList<AttributeObject> list=attributeList;
        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getAttribute()==attribute) {
                list.get(i).setGain(gain);
                break;
            }
        }
    }

    private double getBooleanTagInfo(Attribute attribute) {
        double nbrOfTrue=0;
        double nbrOfFalse=0;

        switch (attribute) {
            case BLINKY_EDIBLE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if(dataSet.get(i).getBlinkyEdible())
                        nbrOfTrue++;
                    nbrOfFalse++;
                }
                break;
            case INKY_EDIBLE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if(dataSet.get(i).getInkyEdible())
                        nbrOfTrue++;
                    nbrOfFalse++;
                }
                break;
            case PINKY_EDIBLE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if(dataSet.get(i).getPinkyEdible())
                        nbrOfTrue++;
                    nbrOfFalse++;
                }
                break;
            case SUE_EDIBLE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if(dataSet.get(i).getSueEdible())
                        nbrOfTrue++;
                    nbrOfFalse++;
                }
                break;
        }
        double infoInTag=nbrOfTrue/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfTrue))+
                nbrOfFalse/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfFalse));
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
                for(int i=0; i<nbrOfTuples; i++) {
                    if(dataSet.get(i).getPosition()==DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    if(dataSet.get(i).getPosition()==DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    if(dataSet.get(i).getPosition()==DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    if(dataSet.get(i).getPosition()==DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    if(dataSet.get(i).getPosition()==DataTuple.DiscreteTag.VERY_HIGH)
                        nbrOfVeryHigh++;
                }
                break;
            case BLINKY_DISTANCE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    if (dataSet.get(i).getBlinkyDistance() == DataTuple.DiscreteTag.VERY_HIGH)
                        nbrOfVeryHigh++;
                }
                break;
            case INKY_DISTANCE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    if (dataSet.get(i).getInkyDistance() == DataTuple.DiscreteTag.VERY_HIGH)
                        nbrOfVeryHigh++;
                }
                break;
            case PINKY_DISTANCE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    if (dataSet.get(i).getPinkyDistance() == DataTuple.DiscreteTag.VERY_HIGH)
                        nbrOfVeryHigh++;
                }
                break;
            case SUE_DISTANCE:
                for(int i=0; i<nbrOfTuples; i++) {
                    if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.VERY_LOW)
                        nbrOfVeryLow++;
                    if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.LOW)
                        nbrOfLow++;
                    if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.MEDIUM)
                        nbrOfMedium++;
                    if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.HIGH)
                        nbrOfHigh++;
                    if (dataSet.get(i).getSueDistance() == DataTuple.DiscreteTag.VERY_HIGH)
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
        double tagUp=(nbrOfUp/nbrOfTags)*minusLog2(nbrOfUp/nbrOfTags);
        double tagDown=(nbrOfDown/nbrOfTags)*minusLog2(nbrOfDown/nbrOfTags);
        double tagRight=(nbrOfRight/nbrOfTags)*minusLog2(nbrOfRight/nbrOfTags);
        double tagLeft=(nbrOfLeft/nbrOfTags)*minusLog2(nbrOfLeft/nbrOfTags);
        return -tagUp-tagDown-tagRight-tagLeft;
    }

    private double minusLog2(double value) {
        return (Math.log(value)/Math.log(2));
    }

    private double getAverageInfo(double nbrOfTuples, double nbrOfLeft, double nbrOfRight, double nbrOfUp, double nbrOfDown) {
        double infoInLeft=(nbrOfLeft/nbrOfTuples)*minusLog2(nbrOfLeft/nbrOfTuples);
        double infoInRight=(nbrOfRight/nbrOfTuples)*minusLog2(nbrOfRight/nbrOfTuples);
        double infoInUp=-(nbrOfUp/nbrOfTuples)*minusLog2(nbrOfUp/nbrOfTuples);
        double infoInDown=(nbrOfDown/nbrOfTuples)*minusLog2(nbrOfDown/nbrOfTuples);
        return -infoInLeft-infoInRight-infoInUp-infoInDown;
    }
}