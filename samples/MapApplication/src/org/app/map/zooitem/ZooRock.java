package org.app.map.zooitem;

import java.awt.Graphics;
import org.app.map.ZooBuildItem;

/**
 *
 * @author dell
 */
public class ZooRock extends ZooItem{
    
    public ZooRock(int x, int y){
        super(x, y);
        setBuildState(ZooBuildItem.BLOCK);
        setMoveState(ZooBuildItem.BLOCK);
        setSprite("sprites/rock1.png");
    }

    @Override
    public void paint(Graphics g) {
        getSprite().draw(g, getX()* ZooTile.TILE_SIZE, getY()* ZooTile.TILE_SIZE);
    }
    
}
