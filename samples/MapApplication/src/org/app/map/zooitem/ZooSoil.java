package org.app.map.zooitem;

/**
 *
 * @author dell
 */
public class ZooSoil extends ZooTile{
    
    public ZooSoil(int x, int y){
        super(x, y);
        setBuildState(ZooBuilding.BLOCK);
        setMoveState(ZooBuilding.CLEAR);
        setSprite("sprites/sand.jpg");
    }
    
}
