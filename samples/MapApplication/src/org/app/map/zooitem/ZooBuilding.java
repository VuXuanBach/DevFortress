package org.app.map.zooitem;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class ZooBuilding extends ZooItem{

    //size of building in term of tile
    
    
    //list of sprites that represents the building
    private ArrayList<ZooTile> tiles;

    //initialize a building from its top-left tile location
    public ZooBuilding(int x, int y){
        //tiles = new ZooTile[BUILDING_SIZE];
        //tiles[0] = new ZooTile(x, y);
        //tiles[1] = new ZooTile(x+1, y);
        //tiles[2] = new ZooTile(x, y+1);
        //tiles[3] = new ZooTile(x+1, y+1);
        super(x, y);
        setSprite("sprites/house.png");
        
    }

    public ZooBuilding(int x, int y, int size){
        //tiles = new ZooTile[BUILDING_SIZE];
        //tiles[0] = new ZooTile(x, y);
        //tiles[1] = new ZooTile(x+1, y);
        //tiles[2] = new ZooTile(x, y+1);
        //tiles[3] = new ZooTile(x+1, y+1);
        super(x, y);
        
        setSprite("sprites/house.png");
        
    }
    
    @Override
    public void paint(Graphics g) {
        getSprite().draw(g, getX()*ZooTile.TILE_SIZE, getY()*ZooTile.TILE_SIZE);
    }
}
