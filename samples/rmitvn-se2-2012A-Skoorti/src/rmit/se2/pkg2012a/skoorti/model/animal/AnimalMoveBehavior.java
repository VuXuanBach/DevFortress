package rmit.se2.pkg2012a.skoorti.model.animal;

import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Movable;

public class AnimalMoveBehavior implements Movable {
    private Coordinate c;
  private int speed;
  private int direction;

    public AnimalMoveBehavior() {
        c = new Coordinate();
        speed = GameConstants.STD_SPEED;
        direction = 0;
    }

    public AnimalMoveBehavior(Coordinate c, int speed, int direction) {
        this.c = c;
        this.speed = speed;
        this.direction = direction;
    }

    @Override
    public Movable move(int[] destination) {
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
        this.speed = speed;
    }

    @Override
    public Movable stop() {
        return this;
    }

    @Override
    public long getStopTime() {
        return System.currentTimeMillis();
    }
    
}
