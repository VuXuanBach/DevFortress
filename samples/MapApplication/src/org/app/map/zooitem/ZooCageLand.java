/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.map.zooitem;

import java.awt.Graphics;
import java.util.ArrayList;
import org.app.map.ZooBuildItem;
import org.app.map.zooitem.animal.Animal;
import org.app.map.zooitem.animal.behaviour.WalkWithLegs;

/**
 *
 * @author Luu Manh 13
 */
public class ZooCageLand extends ZooItem {

    double price;
    ArrayList<Animal> animals;

    public ZooCageLand(int x, int y) {
        super(x, y);
        setSprite("sprites/ZooCageLand.png");
        price = 2000;
        animals = new ArrayList<>();        
        super.setHeight(200);
        super.setWidth(240);
        setCost(2000);
    }

    public boolean addAnimal(Animal a) {
        if(a==null||animals.contains(a)){
            System.out.println("This animal is aready added.");
            return false;
        }
        if(!(a.getWalkable() instanceof WalkWithLegs)){
            return false;
        }else{
            this.animals.add(a);
        return false;
        }
    }

    public boolean removeAnimal(Animal a) {
        animals.remove(a);
        return animals.contains(a);
    }

    public boolean removeAllAnimal() {
        return animals.removeAll(animals);
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void paint(Graphics g) {
        getSprite().draw(g, getX() * ZooTile.TILE_SIZE, getY() * ZooTile.TILE_SIZE);
    }
}
