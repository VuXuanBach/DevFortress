/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.GameMap;

/**
 *
 * @author Le Anh Duy
 */
public class GameNavigator implements Serializable {

    public static final int PAN_UP = 0, PAN_DOWN = 1, PAN_LEFT = 2, PAN_RIGHT = 3, speed = 32;
    private GameMapView map;
    public static int XDirection, YDirection;
    private static int currentX, currentY;
    private List<View> views = new ArrayList<View>();

    public static int getCurrentX() {
        return currentX;
    }

    public static int getCurrentY() {
        return currentY;
    }

    public GameNavigator(GameMapView map) {
        this.map = map;
    }

    public void navigate(int nav) {
        Rectangle[][] blocks = map.getBlocks();
        switch (nav) {
            case PAN_UP:
                if (blocks[0][0].y != 0) {
                    YDirection = speed;
                    currentY -= speed;
                }
                break;
            case PAN_DOWN:
                if (blocks[GameMap.getInstance().getHeight() - 1][0].y + GameConstants.TILE_SIZE >= GameConstants.GAME_HEIGHT) {
                    YDirection = -speed;
                    currentY += speed;
                }
                break;
            case PAN_LEFT:
                if (blocks[0][0].x != 0) {
                    XDirection = speed;
                    currentX -= speed;
                }
                break;
            case PAN_RIGHT:
                if (blocks[0][GameMap.getInstance().getWidth() - 1].x + GameConstants.TILE_SIZE >= GameConstants.GAME_WIDTH) {
                    XDirection = -speed;
                    currentX += speed;
                }
                break;
            default:
                System.out.println("default case entered... Doing nothing.");
                break;
        }
        move();
    }

    private void move() {
        map.moveMap();
        for (View view : views) {
            view.moveWithMap();
        }
        XDirection = 0;
        YDirection = 0;
    }

    public void addView(View view) {
        views.add(view);
    }

    public static boolean isAvailableBlock(int x, int y, int numRows, int numCols) {
        if (x < 0 || y < 0
                || x - getCurrentX() + GameConstants.TILE_SIZE * numRows > GameConstants.GAME_WIDTH
                || y - getCurrentY() + GameConstants.TILE_SIZE * numCols > GameConstants.GAME_HEIGHT) {
            return false;
        }
        int tileX = x / 32;
        int tileY = y / 32;
//        if (x < numCols - 1 || y < numRows - 1) {
//            return false;
//        }
        for (int i = 0; i <= numCols - 1; i++) {
            for (int j = 0; j <= numRows - 1; j++) {
                //System.out.println(GameMap.getBlockStatus(tileX-i, tileY-j) + " x="+(tileX-i)+" y="+(tileY-j));
                if (GameMap.getInstance().getBlockStatus(tileY + i, tileX + j) != GameMap.GRASS && GameMap.getInstance().getBlockStatus(tileY + i, tileX + j) != GameMap.LAND) {
                    return false;
                }
            }
        }
        return true;
    }
}
