/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.map.zooitem.animal.behaviour;

/**
 *
 * @author Acay13
 */
public class FlyWithWings implements Flyable {

    public FlyWithWings() {
    }

    @Override
    public int fly() {
        System.out.println("I'm flying with wings");
        return 5;
    }
    
}
