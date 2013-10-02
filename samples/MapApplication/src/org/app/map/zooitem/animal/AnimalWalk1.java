/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.map.zooitem.animal;

import org.app.map.zooitem.ZooTile;
import org.app.map.zooitem.animal.behaviour.*;

/**
 *
 * @author Luu Manh 13
 */
public class AnimalWalk1 extends Animal {

    public AnimalWalk1(int x, int y, int level) {
        super(x, y);
        setFlyable(new FlyNoWay());
        setSwimable(new SwimNoWay());
        setWalkable(new WalkWithLegs());
        description = "I'm walkabe";
        heathly = 250;
        switch (level) {
            case 1:
                setSprite("sprites/AnimalFoot01_1.png");
                setRef("sprites/AnimalFoot01_1");
                setLevel(level);
                setCost(1000);
                break;
            case 2:
                setSprite("sprites/AnimalFoot01_2.png");
                setRef("sprites/AnimalFoot01_2");
                setLevel(level);
                setCost(1500);
                break;
            case 3:
                setSprite("sprites/AnimalFoot01_3.png");
                setRef("sprites/AnimalFoot01_3");
                setLevel(level);
                setCost(2000);
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
                System.out.println("Upgrade from 1");
                level++;
                setCost(newCost);
                setSprite("sprites/AnimalFoot01_2.png");
                setRef("sprites/AnimalFoot01_2");
                break;
            case 2:
                level++;
                setCost(newCost);
                setSprite("sprites/AnimalFoot01_3.png");
                setRef("sprites/AnimalFoot01_3");
                break;
            default:
                break;
        }
    }

    @Override
    public void feed() {
        heathly = 250;
    }
}
