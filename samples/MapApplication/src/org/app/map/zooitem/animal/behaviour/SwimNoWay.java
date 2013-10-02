package org.app.map.zooitem.animal.behaviour;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Acay13
 */
public class SwimNoWay implements Swimable {

    public SwimNoWay() {
    }

    @Override
    public int swim() {
        System.out.println("I can't swim");
        return 0;
    }
    
}
