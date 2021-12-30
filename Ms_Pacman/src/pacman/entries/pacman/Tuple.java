package pacman.entries.pacman;
import dataRecording.DataTuple;
import pacman.game.Constants;

public class Tuple {
    private Constants.MOVE moveClass;
    private DataTuple.DiscreteTag position;
    private boolean blinkyEdible;
    private boolean inkyEdible;
    private boolean pinkyEdible;
    private boolean sueEdible;
    private DataTuple.DiscreteTag blinkyDistance;
    private DataTuple.DiscreteTag inkyDistance;
    private DataTuple.DiscreteTag pinkyDistance;
    private DataTuple.DiscreteTag sueDistance;

    public Tuple(String[] values)
    {
        moveClass=Constants.MOVE.valueOf(values[0]);
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

    public Constants.MOVE getMoveClass() {
        return moveClass;
    }

    public DataTuple.DiscreteTag getPosition() {
        return position;
    }

    public Boolean getBlinkyEdible() {
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
