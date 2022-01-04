package pacman.entries.pacman;

public class GameAttribute {
    private Attribute attribute;
    private String value;


    public GameAttribute(Attribute attribute) {
        this.attribute=attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
