package org.app.map;

import java.awt.Graphics;

/**
 *
 * @author dell
 */
public abstract class ZooBuildItem {

    //state of the tile(block or clear)
    public static final int BLOCK = 0;
    public static final int CLEAR = 1;
    private int x;
    private int y;
    private int cost;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    private int buildState;
    private int moveState;
    private Sprite sprite;
    private int width;
    private int height;
    String ref;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ZooBuildItem(int px, int py) {

        x = px;
        y = py;
        buildState = BLOCK;
        moveState = BLOCK;
        setCost(50);

    }

    public ZooBuildItem(int px, int py, int bState, int mState) {

        x = px;
        y = py;
        buildState = bState;
        moveState = mState;
        setCost(50);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(String ref) {
        sprite = SpriteStore.get().getSprite(ref);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBuildState() {
        return buildState;
    }

    public void setBuildState(int buildState) {
        this.buildState = buildState;
    }

    public int getMoveState() {
        return moveState;
    }

    public void setMoveState(int moveState) {
        this.moveState = moveState;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public abstract void paint(Graphics g);
}
