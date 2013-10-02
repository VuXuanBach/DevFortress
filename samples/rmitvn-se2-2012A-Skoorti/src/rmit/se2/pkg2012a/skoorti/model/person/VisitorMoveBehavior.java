package rmit.se2.pkg2012a.skoorti.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.GameMap;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.DescendingArrayComparator;
import rmit.se2.pkg2012a.skoorti.model.util.Movable;

public class VisitorMoveBehavior implements Movable {

    private Coordinate c;
    private int speed;
    private int direction;
    private long lastTimeStop;
    private static int[][] visitorMap;
    private int[][] moveMap;
    private List<Integer> movements;
    public static final int NON_ENTERABLE = -1, OCCUPIED = 0, AVAILABLE = 1;
    private int[] currentDest;
    private boolean arrived;

    public VisitorMoveBehavior() {
        this.c = new Coordinate(32, 32);
        this.speed = 8; // speed is from 1 - 10
        direction = GameConstants.DOWN;
        lastTimeStop = 0;
        this.currentDest = new int[]{32, 32, 0};
    }

    public VisitorMoveBehavior(Coordinate c, int speed, int direction) {
        this();
        this.c = c;
        this.speed = speed;
        this.direction = direction;
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

    private int atRow() {
        return (int) Math.round(c.getY() / 32.0);
    }

    private int atCol() {
        return (int) Math.round(c.getX() / 32.0);
    }

    public boolean isArrived() {
        return arrived;
    }

    @Override
    public Movable move(int[] dest) {
        if (c.getX() % 32 != 0 || c.getY() % 32 != 0) {//go to next tile
        } else if (dest == null) {
            arrived = true;
            speed = 0;
        } else if (speed == 0 || this.movements == null) {//visitor is standing at the cage
            arrived = false;
            if (System.currentTimeMillis() - lastTimeStop < 3000) {
                return this;
            }
            currentDest = dest;
            search(currentDest);

            if (movements != null && !movements.isEmpty()) {
                speed = 8;
                direction = this.movements.remove(movements.size() - 1);
            }
        } else if (this.movements.isEmpty()) {//when visitor arrives at destination
            lastTimeStop = System.currentTimeMillis();
            speed = 0;
            direction = currentDest[2];
            arrived = true;
        } else {//on the way to destination
            direction = movements.remove(movements.size() - 1);
        }

        c.setX(c.getX() + speed * GameConstants.MOVECOLLECTIONS[direction][0]);
        c.setY(c.getY() + speed * GameConstants.MOVECOLLECTIONS[direction][1]);
        return this;
    }

    private void initIntArray(int[][] a, int initVal) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = initVal;
            }
        }
    }

    /**
     * initiate the array of moves to a specific destination
     *
     * @param goal list of all destinations
     */
    private void search(int[] goal) {
        int[][] closed = new int[GameMap.getInstance().getHeight()][GameMap.getInstance().getWidth()];
        initIntArray(closed, 0);
        closed[atRow()][atCol()] = 1;
        int[][] action = new int[GameMap.getInstance().getHeight()][GameMap.getInstance().getWidth()];
        initIntArray(action, -1);
        int x = atRow();
        int y = atCol();
        int g = 0;
        List open = new ArrayList();
        int[] h = {g, x, y};
        open.add(h);
        int[][] delta = GameConstants.MOVEDELTA;
        boolean found = false; // flag that is set when search is complete
        boolean resign = false; // flag set if we can't find expand
        while (!found && !resign) {
            if (open.isEmpty()) {
                resign = true;
            } else {
                Collections.sort(open, new DescendingArrayComparator());
                int[] next = (int[]) open.remove(open.size() - 1);
                x = next[1];
                y = next[2];
                g = next[0];
                if (x == goal[0] && y == goal[1]) {
                    found = true;
                } else {
                    for (int i = 0; i < delta.length; i++) {
                        int x2 = x + delta[i][0];
                        int y2 = y + delta[i][1];
                        if (x2 >= 0 && x2 < GameMap.getInstance().getHeight() && y2 >= 0 && y2 < GameMap.getInstance().getWidth()) {
                            if (closed[x2][y2] == 0 && GameMap.getInstance().getEnterableMap()[x2][y2] == 0) {
                                int g2 = g + 1;
                                int[] k = {g2, x2, y2};
                                open.add(k);
                                closed[x2][y2] = 1;
                                action[x2][y2] = i;
                            }
                        }
                    }
                }
            }
        }

        x = goal[0];
        y = goal[1];
        movements = new ArrayList<Integer>();
        while (x != atRow() || y != atCol()) {
            int x2 = x - delta[action[x][y]][0];
            int y2 = y - delta[action[x][y]][1];
            movements.add(action[x][y]);
            x = x2;
            y = y2;
        }
    }

    public int senseRoad() {
        if (c.getX() % 32 != 0 || c.getY() % 32 != 0) {
            return this.direction;
        }
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

    public int senseCage() {
        if (c.getX() % 32 != 0 || c.getY() % 32 != 0) {
            return this.direction;
        }
        int tileX = (int) Math.round(c.getX() / 32.0);
        int tileY = (int) Math.round(c.getY() / 32.0);
        int[] d = {direction, (direction + 1) % 4, (direction + 3) % 4};
        char[] neighborTiles = new char[3];
        ArrayList<Integer> neighborList = getAvailableNeighbors(neighborTiles);
        for (int i = 0; i < neighborTiles.length; i++) {
            char neighbor = GameMap.getInstance().getBlockStatus(tileY + GameConstants.MOVECOLLECTIONS[d[i]][1],
                    tileX + GameConstants.MOVECOLLECTIONS[d[i]][0]);
            if (visitorMap[tileY][tileX] == AVAILABLE && neighbor == GameMap.CAGE) {
                neighborList.add(i);
            }
        }
        if (neighborList.isEmpty()) {
            return direction;
        }

        lastTimeStop = System.currentTimeMillis();
        speed = 0;
        visitorMap[tileY][tileX] = OCCUPIED;
        Random r = new Random();
        int aa = neighborList.get(r.nextInt(neighborList.size()));
        return d[aa];
    }

    public static void initVMap() {
        char[][] blockData = GameMap.getInstance().getBlockData();
        visitorMap = new int[blockData.length][blockData[0].length];
        for (int i = 0; i < blockData.length; i++) {
            for (int j = 0; j < blockData[i].length; j++) {
                if (blockData[i][j] == GameMap.ROAD) {
                    visitorMap[i][j] = 1;
                } else {
                    visitorMap[i][j] = -1;
                }
            }
        }
    }

    public void initMMap() {
        char[][] blockData = GameMap.getInstance().getBlockData();
        moveMap = new int[blockData.length][blockData[0].length];
        for (int i = 0; i < blockData.length; i++) {
            for (int j = 0; j < blockData[i].length; j++) {
                if (blockData[i][j] == GameMap.ROAD) {
                    moveMap[i][j] = 0;
                } else {
                    moveMap[i][j] = -1;
                }
            }
        }
    }

    @Override
    public Movable stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getStopTime() {
        return System.currentTimeMillis() - lastTimeStop;
    }
}
