/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.map.zooitem.animal;

import java.awt.Graphics;
import org.app.map.zooitem.ZooItem;
import org.app.map.zooitem.ZooTile;
import org.app.map.zooitem.animal.behaviour.Flyable;
import org.app.map.zooitem.animal.behaviour.Swimable;
import org.app.map.zooitem.animal.behaviour.Walkable;

/**
 *
 * @author Luu Manh 13
 */
public abstract class Animal extends ZooItem implements Runnable {

    Flyable flyable;
    Swimable swimable;
    Walkable walkable;
    int level;
    String description = "Unknown Animal";
    int heathly;

    public Animal(int x, int y) {
        super(x, y);
    }

    public String getDescription() {
        return description;
    }

    public double upgradeCost() {
        return 500;
    }

    public abstract void upgrade();

    public Flyable getFlyable() {
        return flyable;
    }

    public void setFlyable(Flyable flyable) {
        this.flyable = flyable;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Swimable getSwimable() {
        return swimable;
    }

    public void setSwimable(Swimable swimable) {
        this.swimable = swimable;
    }

    public Walkable getWalkable() {
        return walkable;
    }

    public void setWalkable(Walkable walkable) {
        this.walkable = walkable;
    }

    @Override
    public void run() {
        while (heathly > 0) {
            long startingTime = System.currentTimeMillis();
            this.live(startingTime);
        }
    }

    public void live(long startingTime) {
        long timePassed = System.currentTimeMillis() - startingTime;
        while (timePassed < 1000) {
            timePassed = System.currentTimeMillis() - startingTime;
        }
        System.out.println("One second");
        System.out.print(heathly + "_" + this.getDescription() + "_");
        heathly -= 1;
        if (heathly > 60) {
            heathly -= walkable.walk();
            heathly -= swimable.swim();
            heathly -= flyable.fly();
        } else {
            if (heathly >= 10 && heathly < 60) {
                System.out.println("I'm hungry");
                super.setSprite(getRef() + "Hungry.png");
            } else if (heathly > 0 && heathly < 101) {
                System.out.println("I'm starving");
                super.setSprite(getRef() + "Dead.png");
            } else if (heathly <= 0) {
                System.out.println("I'm died");
                super.setSprite("sprites/Dead.png");
            }
        }
    }

    public int getHeathly() {
        return heathly;
    }

    public void setHeathly(int heathly) {
        this.heathly = heathly;
    }
    public abstract void feed();
    @Override
    public void paint(Graphics g) {
        getSprite().draw(g, getX() * ZooTile.TILE_SIZE, getY() * ZooTile.TILE_SIZE);
    }
}
