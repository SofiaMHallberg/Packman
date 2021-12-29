package pacman.entries.pacman;

import pacman.game.Constants;

import java.util.Collections;
import java.util.LinkedList;

public class DecisionTreeCreator {
    private Dataset dataset;
    private LinkedList<String[]> trainingDataSet;
    private LinkedList<String[]> testDataSet;
    LinkedList<AttributeObject> attributeList;
    private double averageInfo;
    private double nbrOfTuples;
    private double nbrOfUp=0;
    private double nbrOfDown=0;
    private double nbrOfRight=0;
    private double nbrOfLeft=0;

    public DecisionTreeCreator() {
        dataset=new Dataset();
        trainingDataSet=dataset.getTrainingDataSet();
        testDataSet=dataset.getTestDataSet();
        buildTree(trainingDataSet, attributeList);
    }

    public Node buildTree(LinkedList<String[]> dataSet, LinkedList<AttributeObject> attributeList) {
        Node node=new Node();
        Constants.MOVE move=multipleClasses(dataSet);
        if(move!=null) {
            node.setMove(move);
            return node;
        }

        if(attributeList.isEmpty()) {
            node.setMove(majorityClass(dataSet));
            return node;
        }

        Attribute thisAttribute=attributeSelection(dataSet, attributeList);

        return null;
    }

    private Constants.MOVE majorityClass(LinkedList<String[]> dataSet) {
        int nbrOfUp=0;
        int nbrOfDown=0;
        int nbrOfRight=0;
        int nbrOfLeft=0;

        for(int i=0; i<dataSet.size(); i++) {
            switch (dataSet.get(i)[0]) {
                case "LEFT":
                    nbrOfLeft++;
                    break;
                case "RIGHT":
                    nbrOfRight++;
                    break;
                case "UP":
                    nbrOfUp++;
                    break;
                case "DOWN":
                    nbrOfDown++;
                    break;
            }
        }
        if(nbrOfUp>=nbrOfDown && nbrOfUp>=nbrOfRight && nbrOfUp>=nbrOfLeft)
            return Constants.MOVE.UP;
        if(nbrOfDown>=nbrOfUp && nbrOfDown>=nbrOfRight && nbrOfDown>=nbrOfLeft)
            return Constants.MOVE.DOWN;
        if(nbrOfRight>=nbrOfDown && nbrOfRight>=nbrOfUp && nbrOfRight>=nbrOfLeft)
            return Constants.MOVE.RIGHT;
        if(nbrOfLeft>=nbrOfDown && nbrOfLeft>=nbrOfRight && nbrOfLeft>=nbrOfUp)
            return Constants.MOVE.UP;
    }

    private Constants.MOVE multipleClasses(LinkedList<String[]> dataSet) {
        String myClass=dataSet.getFirst()[0];
        for(int i=1; i<dataSet.size(); i++) {
            if(!dataSet.get(i)[0].equals(myClass))
                return null;
        }
        return Constants.MOVE.valueOf(myClass);
    }

     public Attribute attributeSelection(LinkedList<String[]> dataSet, LinkedList<AttributeObject> attributeList) {
        nbrOfTuples=dataSet.size();
        nbrOfUp=0;
        nbrOfDown=0;
        nbrOfRight=0;
        nbrOfLeft=0;

        for(int i=0; i<nbrOfTuples; i++) {
            switch (dataSet.get(i)[0]) {
                case "LEFT":
                    nbrOfLeft++;
                    break;
                case "RIGHT":
                    nbrOfRight++;
                    break;
                case "UP":
                    nbrOfUp++;
                    break;
                case "DOWN":
                    nbrOfDown++;
                    break;
            }
        }

        averageInfo=getAverageInfo(nbrOfTuples, nbrOfLeft, nbrOfRight, nbrOfUp, nbrOfDown);

        double infoInPosition=getDiscreteTagInfo(Attribute.PACMAN_POSITION);
        double infoInBlinkyDistance=getDiscreteTagInfo(Attribute.BLINKY_DISTANCE);
        double infoInInkyDistance=getDiscreteTagInfo(Attribute.INKY_DISTANCE);
        double infoInPinkyDistance=getDiscreteTagInfo(Attribute.PINKY_DISTANCE);
        double infoInSueDistance=getDiscreteTagInfo(Attribute.SUE_DISTANCE);
        double infoInBlinkyEdible=getBooleanTagInfo(Attribute.BLINKY_EDIBLE);
        double infoInInkyEdible=getBooleanTagInfo(Attribute.INKY_EDIBLE);
        double infoInPinkyEdible=getBooleanTagInfo(Attribute.PINKY_EDIBLE);
        double infoInSueEdible=getBooleanTagInfo(Attribute.SUE_EDIBLE);

        double gainPosition=averageInfo-infoInPosition;
        AttributeObject positionObject=new AttributeObject(Attribute.PACMAN_POSITION, gainPosition);
        attributeList.add(positionObject);
        double gainBlinkyDist=averageInfo-infoInBlinkyDistance;
        AttributeObject blinkyDistance=new AttributeObject(Attribute.BLINKY_DISTANCE, gainBlinkyDist);
        attributeList.add(blinkyDistance);
        double gainInkyDist=averageInfo-infoInInkyDistance;
        AttributeObject inkyDistance=new AttributeObject(Attribute.INKY_DISTANCE, gainInkyDist);
        attributeList.add(inkyDistance);
        double gainPinkyDist=averageInfo-infoInPinkyDistance;
        AttributeObject pinkyDistance=new AttributeObject(Attribute.PINKY_DISTANCE, gainPinkyDist);
        attributeList.add(pinkyDistance);
        double gainSueDist=averageInfo-infoInSueDistance;
        AttributeObject sueDistance=new AttributeObject(Attribute.SUE_DISTANCE, gainSueDist);
        attributeList.add(sueDistance);
        double gainBlinkyEdible=averageInfo-infoInBlinkyEdible;
        AttributeObject blinkyEdible=new AttributeObject(Attribute.BLINKY_EDIBLE, gainBlinkyEdible);
        attributeList.add(blinkyEdible);
        double gainInkyEdible=averageInfo-infoInInkyEdible;
        AttributeObject inkyEdible=new AttributeObject(Attribute.INKY_EDIBLE, gainInkyEdible);
        attributeList.add(inkyEdible);
        double gainPinkyEdible=averageInfo-infoInPinkyEdible;
        AttributeObject pinkyEdible=new AttributeObject(Attribute.PINKY_EDIBLE, gainPinkyEdible);
        attributeList.add(pinkyEdible);
        double gainSueEdible=averageInfo-infoInSueEdible;
        AttributeObject sueEdible=new AttributeObject(Attribute.SUE_EDIBLE, gainSueEdible);
        attributeList.add(sueEdible);

        Collections.sort(attributeList);
        return attributeList.removeFirst().getAttribute();
    }

    private double getBooleanTagInfo(Attribute attribute) {
        int column;
        switch (attribute) {
            case BLINKY_EDIBLE:
                column = 2;
                break;
            case INKY_EDIBLE:
                column = 3;
                break;
            case PINKY_EDIBLE:
                column = 4;
                break;
            case SUE_EDIBLE:
                column = 5;
                break;
        }

        double nbrOfTrue=0;
        double nbrOfFalse=0;

        for(int i=0; i<nbrOfTuples; i++) {
            switch (trainingDataSet.get(i)[0]) {
                case "TRUE":
                    nbrOfTrue++;
                    break;
                case "FALSE":
                    nbrOfFalse++;
                    break;
            }
        }

        double infoInTag=nbrOfTrue/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfTrue))+
                nbrOfFalse/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfFalse));

        return infoInTag;
    }

    private double getDiscreteTagInfo(Attribute attribute) {
        int column;

        switch (attribute) {
            case PACMAN_POSITION:
                column=1;
                break;
            case BLINKY_DISTANCE:
                column=6;
                break;
            case INKY_DISTANCE:
                column=7;
                break;
            case PINKY_DISTANCE:
                column=8;
                break;
            case SUE_DISTANCE:
                column=9;
                break;
        }

        double nbrOfVeryLow=0;
        double nbrOfLow=0;
        double nbrOfMedium=0;
        double nbrOfHigh=0;
        double nbrOfVeryHigh=0;

        for(int i=0; i<nbrOfTuples; i++) {
            switch (trainingDataSet.get(i)[0]) {
                case "VERY_LOW":
                    nbrOfVeryLow++;
                    break;
                case "LOW":
                    nbrOfLow++;
                    break;
                case "MEDIUM":
                    nbrOfMedium++;
                    break;
                case "HIGH":
                    nbrOfHigh++;
                    break;
                case "VERY_HIGH":
                    nbrOfVeryHigh++;
                    break;
            }
        }

        double infoInTag=nbrOfVeryLow/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfVeryLow))+
                         nbrOfLow/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfLow))+
                         nbrOfMedium/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfMedium))+
                         nbrOfHigh/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfHigh))+
                         nbrOfVeryHigh/nbrOfTuples*(infoInDiscreteTagInfo(nbrOfVeryHigh));


        return infoInTag;
    }

    private double infoInDiscreteTagInfo(double nbrOfTags) {
        double tagUp=(nbrOfTags/nbrOfUp)*minusLog2(nbrOfTags/nbrOfUp);
        double tagDown=(nbrOfTags/nbrOfDown)*minusLog2(nbrOfTags/nbrOfDown);
        double tagRight=(nbrOfTags/nbrOfRight)*minusLog2(nbrOfTags/nbrOfRight);
        double tagLeft=(nbrOfTags/nbrOfLeft)*minusLog2(nbrOfTags/nbrOfLeft);
        return tagUp+tagDown+tagRight+tagLeft;
    }

    private double minusLog2(double value) {
        return (Math.log(value)/Math.log(2));
    }

    private double getAverageInfo(double nbrOfTuples, double nbrOfLeft, double nbrOfRight, double nbrOfUp, double nbrOfDown) {
        double infoInLeft=(nbrOfLeft/nbrOfTuples)*minusLog2(nbrOfLeft/nbrOfTuples);
        double infoInRight=(nbrOfRight/nbrOfTuples)*minusLog2(nbrOfRight/nbrOfTuples);
        double infoInUp=(nbrOfUp/nbrOfTuples)*minusLog2(nbrOfUp/nbrOfTuples);
        double infoInDown=(nbrOfDown/nbrOfTuples)*minusLog2(nbrOfDown/nbrOfTuples);

        return infoInLeft+infoInRight+infoInUp+infoInDown;
    }
}
