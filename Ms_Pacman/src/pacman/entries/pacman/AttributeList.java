package pacman.entries.pacman;

import java.util.LinkedList;

public class AttributeList implements Cloneable {
    LinkedList<AttributeObject> list;

    public AttributeList() {
        list=new LinkedList<>();
        list.add(new AttributeObject(Attribute.PACMAN_POSITION));
        list.add(new AttributeObject(Attribute.BLINKY_EDIBLE));
        list.add(new AttributeObject(Attribute.INKY_EDIBLE));
        list.add(new AttributeObject(Attribute.PINKY_EDIBLE));
        list.add(new AttributeObject(Attribute.SUE_EDIBLE));
        list.add(new AttributeObject(Attribute.BLINKY_DISTANCE));
        list.add(new AttributeObject(Attribute.INKY_DISTANCE));
        list.add(new AttributeObject(Attribute.PINKY_DISTANCE));
        list.add(new AttributeObject(Attribute.SUE_DISTANCE));
    }

    public LinkedList<AttributeObject> getList() {
        return list;
    }

    public void removeAttribute(Attribute attribute) {
        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getAttribute()==attribute) {
                list.remove(i);
                break;
            }
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getSize() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
