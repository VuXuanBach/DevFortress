
package rmit.se2.pkg2012a.skoorti.model.person;

import java.util.List;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Movable;

public class ZooKeeperMoveBehavior implements Movable {
    private List<Integer> movements;
    private int speed;
  private int direction;
  private Coordinate c;

    public ZooKeeperMoveBehavior(List<Integer> movements, int speed, int direction, Coordinate c) {
        this.movements = movements;
        this.speed = speed;
        this.direction = direction;
        this.c = c;
    }

    @Override
    public Movable move(int[] destination) {
//        
//        c.setX(c.getX() + speed * GameConstants.MOVECOLLECTIONS[direction][0]);
//        c.setY(c.getY() + speed * GameConstants.MOVECOLLECTIONS[direction][1]);
        return this;
    }

    @Override
    public Coordinate getCoordinate() {
        return c;
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Movable stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getStopTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
