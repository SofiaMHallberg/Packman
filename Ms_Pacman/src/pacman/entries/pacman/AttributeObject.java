package pacman.entries.pacman;

public class AttributeObject implements Comparable<AttributeObject>{
    private double gain;
    private Attribute name;

    public AttributeObject(Attribute name, double gain) {
        this.name=name;
        this.gain=gain;
    }

    public double getGain() {
        return gain;
    }

    public Attribute getAttribute() {
        return name;
    }

    @Override
    public int compareTo(AttributeObject o) {

        return Double.compare(gain, o.getGain());
    }
}
