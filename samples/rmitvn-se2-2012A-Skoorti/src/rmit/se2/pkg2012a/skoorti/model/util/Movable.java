/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.util;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author duynguyen
 */
public interface Movable extends Serializable {
    public Movable move(int[] destination);
    public Movable stop();
    public Coordinate getCoordinate();
    public int getDirection();
    public int getSpeed();
    public void setSpeed(int speed);
    public long getStopTime();
    //public void sense();
    //public void moveRight();
    //public void moveLeft();
}
