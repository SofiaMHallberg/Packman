package pacman.entries.pacman;

/**
 * A class for storing attributes and corresponding gain.
 *
 * @author Sofia Hallberg, Oscar Kareld
 * 04/01-2022
 */
public class AttributeObject implements Comparable<AttributeObject> {
    private double gain;
    private Attribute type;

    public AttributeObject(Attribute type) {
        this.type=type;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain=gain;
        //System.out.println("setGain "+gain+" to "+String.valueOf(type));
    }

    public Attribute getAttribute() {
        return type;
    }

    @Override
    public int compareTo(AttributeObject o) {

        return Double.compare(gain, o.getGain());
    }
}
