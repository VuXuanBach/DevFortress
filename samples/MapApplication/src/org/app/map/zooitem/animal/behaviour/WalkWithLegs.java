package org.app.map.zooitem.animal.behaviour;

/**
 *
 * @author Acay13
 */
public class WalkWithLegs implements Walkable {

    public WalkWithLegs() {
    }

    @Override
    public int walk() {
        System.out.println("I'm walking with my legs");
        return 3;
    }
    
}
