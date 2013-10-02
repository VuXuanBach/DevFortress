/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.map.zooitem.animal;

import org.app.map.zooitem.animal.behaviour.FlyNoWay;
import org.app.map.zooitem.animal.behaviour.SwimNoWay;
import org.app.map.zooitem.animal.behaviour.WalkWithLegs;

/**
 *
 * @author Luu Manh 13
 */
public class AnimalSwim1 extends Animal {

    public AnimalSwim1(int x, int y, int level) {
        super(x, y);
        setFlyable(new FlyNoWay());
        setSwimable(new SwimNoWay());
        setWalkable(new WalkWithLegs());
        description = "I'm walkabe";
        heathly = 150;
        switch (level) {
            case 1:
                setCost(1500);
                setSprite("sprites/AnimalSwim01_1.png");
                setRef("sprites/AnimalSwim01_1");
                setLevel(level);
                break;
            case 2:
                setCost(2000);
                setSprite("sprites/AnimalSwim01_2.png");
                setRef("sprites/AnimalSwim01_2");
                setLevel(level);
                break;
            case 3:
                setCost(2500);
                setSprite("sprites/AnimalSwim01_3.png");
                setRef("sprites/AnimalSwim01_3");
                setLevel(level);
                break;
            default:
                break;
        }
    }

    

    @Override
    public void upgrade() {
        int newCost;
        newCost = getCost()+500;
        switch (level) {
            case 1:
                level++;
                setCost(newCost);
                setSprite("sprites/AnimalSwim01_2.png");
                setRef("sprites/AnimalSwim01_2");
                break;
            case 2:
                level++;
                setCost(newCost);
                setSprite("sprites/AnimalSwim01_3.png");
                setRef("sprites/AnimalSwim01_3");
                break;
            default:
                break;
        }
    }

    @Override
    public void feed() {
        heathly = 150;
    }
}
