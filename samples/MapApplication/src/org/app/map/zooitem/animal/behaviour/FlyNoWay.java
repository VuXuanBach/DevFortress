package org.app.map.zooitem.animal.behaviour;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Acay13
 */
public class FlyNoWay implements Flyable {

    public FlyNoWay() {
    }

    @Override
    public int fly() {
        System.out.println("I can't fly");
        return 0;
    }
    
}
