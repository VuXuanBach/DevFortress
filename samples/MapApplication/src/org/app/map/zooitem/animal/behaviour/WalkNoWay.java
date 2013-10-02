package org.app.map.zooitem.animal.behaviour;

/**
 *
 * @author Acay13
 */
public class WalkNoWay implements Walkable {

    public WalkNoWay() {
    }

    @Override
    public int walk() {
        System.out.println("I can't walk");
        return 0;
    }
    
}
