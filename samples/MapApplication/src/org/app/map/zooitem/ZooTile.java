package org.app.map.zooitem;

import java.awt.Graphics;
import org.app.map.ZooBuildItem;

/**
 * a tile is a square of pixels in the map
 * @author dell
 */
public class ZooTile extends ZooBuildItem{
    
    //tile size in terms of pixels in map
    public static final int TILE_SIZE = 20;
    
    
    
    public ZooTile(int px, int py, int bState, int mState){
        super(px, py, bState, mState);
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);
    }
    
    //set state to default
    public ZooTile(int px, int py){
        super(px, py);  
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);
    }

    @Override
    public void paint(Graphics g) {
        getSprite().draw(g, getX()* TILE_SIZE, getY()* TILE_SIZE);
    }
    
}
