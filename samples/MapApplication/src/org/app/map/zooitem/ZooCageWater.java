/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.map.zooitem;

import java.awt.Graphics;
import java.util.ArrayList;
import org.app.map.ZooBuildItem;
import org.app.map.zooitem.animal.Animal;

/**
 *
 * @author Luu Manh 13
 */
public class ZooCageWater extends ZooItem {

    double price;
    ArrayList<Animal> animals;

    public ZooCageWater(int x, int y) {
        super(x, y);
        setSprite("sprites/ZooCageWater.png");
        price = 3000;
        animals = new ArrayList<>();        
        super.setHeight(200);
        super.setWidth(240);
        setCost(3000);
    }

    public boolean addAnimal(Animal a) {
        return false;
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
