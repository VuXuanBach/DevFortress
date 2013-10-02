package org.app.map;

import org.app.map.zooitem.animal.Animal;
import org.app.visitor.Visitor;

/**
 *
 * @author dell
 */
public interface Observer {
    public void update(ZooBuildItem item);
    public void update(Animal a,boolean status);
    public void update(Visitor visitor);
}
