package org.app.map.zooitem.animal.behaviour;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Acay13
 */
public class SwimWithFins implements Swimable {

    public SwimWithFins() {
    }

    @Override
    public int swim() {
        System.out.println("I'm swimming with my fins");
        return 4;
    }
    
}
