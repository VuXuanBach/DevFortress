package org.app.map.zooitem;

/**
 *
 * @author dell
 */
public class ZooGrass extends ZooTile{
    
    public ZooGrass(int x, int y){
        super(x, y);
        setBuildState(ZooBuilding.CLEAR);
        setMoveState(ZooBuilding.BLOCK);
        setSprite("sprites/grass1.jpg");
    }
    
}
