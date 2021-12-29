package pacman.entries.pacman;

import pacman.game.Constants;

public class Node {
    private Attribute attribute;
    private Constants.MOVE move;

    public Node() {}

    public Node(Attribute attribute) {
        this.attribute=attribute;
    }

    public Node(Constants.MOVE move) {
        this.move=move;
    }

    public void setMove(Constants.MOVE move) {
        this.move=move;
    }

}
