package pacman.entries.pacman;

/**
 * A class for storing attributes and their gain.
 *
 * @author Sofia Hallberg, Oscar Kareld
 * 04/01-2022
 */
public class AttributeObject implements Comparable<AttributeObject>{
    private double gain;
    private Attribute name;

    public AttributeObject(Attribute name, double gain) {
        this.name=name;
        this.gain=gain;
    }
    public AttributeObject(Attribute name) {
        this.name=name;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain=gain;
    }

    public Attribute getAttribute() {
        return name;
    }

    @Override
    public int compareTo(AttributeObject o) {

        return Double.compare(gain, o.getGain());
    }
}
