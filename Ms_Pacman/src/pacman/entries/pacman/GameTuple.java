package pacman.entries.pacman;

import dataRecording.DataTuple;
import pacman.game.Constants;

public class GameTuple {
    private DataTuple.DiscreteTag position;
    private boolean blinkyEdible;
    private boolean inkyEdible;
    private boolean pinkyEdible;
    private boolean sueEdible;
    private DataTuple.DiscreteTag blinkyDistance;
    private DataTuple.DiscreteTag inkyDistance;
    private DataTuple.DiscreteTag pinkyDistance;
    private DataTuple.DiscreteTag sueDistance;

    public GameTuple(String[] values)
    {
        position=DataTuple.DiscreteTag.valueOf(values[1]);
        blinkyEdible=Boolean.parseBoolean(values[2]);
        inkyEdible=Boolean.parseBoolean(values[3]);
        pinkyEdible=Boolean.parseBoolean(values[4]);
        sueEdible=Boolean.parseBoolean(values[5]);
        blinkyDistance=DataTuple.DiscreteTag.valueOf(values[6]);;
        inkyDistance=DataTuple.DiscreteTag.valueOf(values[7]);
        pinkyDistance=DataTuple.DiscreteTag.valueOf(values[8]);
        sueDistance=DataTuple.DiscreteTag.valueOf(values[9]);
    }

    public DataTuple.DiscreteTag getPosition() {
        return position;
    }

    public boolean getBooleanValue(Attribute attribute) {
        switch (attribute) {
            case BLINKY_EDIBLE:
                return blinkyEdible;
            case INKY_EDIBLE:
                return inkyEdible;
            case PINKY_EDIBLE:
                return pinkyEdible;
            case SUE_EDIBLE:
                return sueEdible;
        }
        return true; //OBS! KONTROLLERA SÅ DETTA INTE SKER
    }

    public DataTuple.DiscreteTag getDiscreteValue(Attribute attribute) {
        switch (attribute) {
            case PACMAN_POSITION:
                return position;
            case BLINKY_DISTANCE:
                return blinkyDistance;
            case INKY_DISTANCE:
                return inkyDistance;
            case PINKY_DISTANCE:
                return pinkyDistance;
            case SUE_DISTANCE:
                return sueDistance;
            default:
                return null;
        }
    }

    public boolean getBlinkyEdible() {
        return blinkyEdible;
    }

    public Boolean getInkyEdible() {
        return inkyEdible;
    }

    public Boolean getPinkyEdible() {
        return pinkyEdible;
    }

    public Boolean getSueEdible() {
        return sueEdible;
    }

    public DataTuple.DiscreteTag getBlinkyDistance() {
        return blinkyDistance;
    }

    public DataTuple.DiscreteTag getInkyDistance() {
        return inkyDistance;
    }

    public DataTuple.DiscreteTag getPinkyDistance() {
        return pinkyDistance;
    }

    public DataTuple.DiscreteTag getSueDistance() {
        return sueDistance;
    }
}
