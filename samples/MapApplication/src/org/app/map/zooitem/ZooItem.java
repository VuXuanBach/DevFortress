package org.app.map.zooitem;

import org.app.map.ZooBuildItem;

/**
 *
 * @author dell
 */
public abstract class ZooItem extends ZooBuildItem{

    public ZooItem(int x, int y){
        super(x, y);
        setWidth(40);
        setHeight(40);
    }
    
    
}
