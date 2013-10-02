/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.person;

import java.util.ArrayList;
import java.util.Random;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.GameMap;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Movable;

/**
 *
 * @author duynguyen
 */
public class NormalMoveBehavior implements Movable {
  private int speed;
  private int direction;
  private long stopTime;
  private Coordinate c;

  public NormalMoveBehavior() {
        this.c = new Coordinate(32,32);
        this.speed = GameConstants.STD_SPEED; // speed is from 1 - 10
        direction = GameConstants.RIGHT;
        stopTime = System.currentTimeMillis();
    }

    public NormalMoveBehavior(Coordinate c, int speed) {
      this();
      this.c = c;
      this.speed = speed;
    }
  @Override
    public Movable move(int[] destination) {
        if (speed == 0 && System.currentTimeMillis() - stopTime < 2000) {
            return this;
        }
        speed = GameConstants.STD_SPEED;
        //sense ahead
        direction = senseRoad();
        
        c.setX(c.getX() + speed * GameConstants.MOVECOLLECTIONS[direction][0]);
        c.setY(c.getY() + speed * GameConstants.MOVECOLLECTIONS[direction][1]);
        return this;
    }
  
    @Override
    public Movable stop() {
        speed = 0;
        stopTime = System.currentTimeMillis();
        return this;
    }

    public long getStopTime() {
        return stopTime;
    }

    public int senseRoad() {
        if (c.getX() % 32 != 0 || c.getY() % 32 != 0)
                return this.direction;
        int tileX = (int) Math.round(c.getX() / 32.0);
        int tileY = (int) Math.round(c.getY() / 32.0);
        //get tiles around
        int[] d = {direction, (direction + 1) % 4, (direction + 3) % 4};
        char[] neighborTiles = new char[3];
        for (int i = 0; i < neighborTiles.length; i++) {
            neighborTiles[i] = GameMap.getInstance().getBlockStatus(tileY + GameConstants.MOVECOLLECTIONS[d[i]][1],
                    tileX + GameConstants.MOVECOLLECTIONS[d[i]][0]);
        }
        ArrayList<Integer> neighborList = getAvailableNeighbors(neighborTiles);
        if (neighborList.isEmpty()) {
            return (this.direction + 2) % 4;
        } else if (neighborList.size() == 1 && d[neighborList.get(0)] == direction) {
            return direction;
        } else {
            Random r = new Random();
            return d[neighborList.get(r.nextInt(neighborList.size()))];
        }
    }

    private ArrayList getAvailableNeighbors(char[] neighbors) {
        ArrayList<Integer> neighborList = new ArrayList<Integer>();
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] == GameMap.ROAD) {
                neighborList.add(i);
            }
        }
        return neighborList;
    }

  @Override
  public Coordinate getCoordinate() {
    return this.c;
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
  
}
