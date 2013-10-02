/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.GameMap;

/**
 *
 * @author Le Anh Duy
 */
public class GameMapView implements Serializable{

    private GameMap map;
    private Rectangle[][] blocks;
    private Image[][] blockImg;
//    private final int MAP_WIDTH = TILE_SIZE * ROW_NUM;
//    private final int MAP_HEIGHT = TILE_SIZE * COL_NUM;
   
    // Image tiles
    private Image BLOCK_GRASS, BLOCK_LAND, BLOCK_DOOR_1, BLOCK_DOOR_2, BLOCK_DOOR_3,
            BLOCK_DOOR_4, BLOCK_DOOR_5, BLOCK_TREE_1, BLOCK_TREE_2,
            BLOCK_TREE_3, BLOCK_WALL, BLOCK_ROAD;
    //Map variables
    private int x, y;

    public GameMapView(GameMap map) {
        this.map = map;
        BLOCK_GRASS = new ImageIcon(getClass().getResource("/images/grass.png")).getImage();
        BLOCK_LAND = new ImageIcon(getClass().getResource("/images/land.png")).getImage();
        BLOCK_DOOR_1 = new ImageIcon(getClass().getResource("/images/door1.png")).getImage();
        BLOCK_DOOR_2 = new ImageIcon(getClass().getResource("/images/door2.png")).getImage();
        BLOCK_DOOR_3 = new ImageIcon(getClass().getResource("/images/door3.png")).getImage();
        BLOCK_DOOR_4 = new ImageIcon(getClass().getResource("/images/door4.png")).getImage();
        BLOCK_DOOR_5 = new ImageIcon(getClass().getResource("/images/door5.png")).getImage();
        BLOCK_TREE_1 = new ImageIcon(getClass().getResource("/images/tree1.png")).getImage();
        BLOCK_TREE_2 = new ImageIcon(getClass().getResource("/images/tree2.png")).getImage();
        BLOCK_TREE_3 = new ImageIcon(getClass().getResource("/images/tree3.png")).getImage();
        BLOCK_WALL = new ImageIcon(getClass().getResource("/images/wall.png")).getImage();
        BLOCK_ROAD = new ImageIcon(getClass().getResource("/images/road.png")).getImage();
        blocks = new Rectangle[GameMap.getInstance().getHeight()][GameMap.getInstance().getWidth()];
        blockImg = new Image[GameMap.getInstance().getHeight()][GameMap.getInstance().getWidth()];
        try {
            load();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameMapView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GameMap getMap() {
        return map;
    }

    public Rectangle[][] getBlocks() {
        return blocks;
    }

    private void load() throws FileNotFoundException {
        char[][] blockData = map.getBlockData();

        for (int a = 0; a < blockData.length; a++) {
            x = 0;
            for (int b = 0; b < blockData[0].length; b++) {
                char c = blockData[a][b];
                switch (c) {
                    case GameMap.GRASS:
                        blockImg[a][b] = BLOCK_GRASS;
                        break;
                    case GameMap.WALL:
                        blockImg[a][b] = BLOCK_WALL;
                        break;
                    case GameMap.LAND:
                        blockImg[a][b] = BLOCK_LAND;
                        break;
                    case GameMap.DOOR1:
                  blockImg[a][b]= BLOCK_DOOR_1;
                        break;
                    case GameMap.DOOR2:
                   blockImg[a][b] = BLOCK_DOOR_2;
                        break;
                    case GameMap.DOOR3:
                     blockImg[a][b] = BLOCK_DOOR_3;
                        break;
                    case GameMap.DOOR4:
                     blockImg[a][b]= BLOCK_DOOR_4;
                        break;
                    case GameMap.DOOR5:
                  blockImg[a][b] = BLOCK_DOOR_5;
                        break;
                    case GameMap.TREE1:
                      blockImg[a][b] = BLOCK_TREE_1;
                        break;
                    case GameMap.TREE2:
                     blockImg[a][b] = BLOCK_TREE_2;
                        break;
                    case GameMap.TREE3:
                      blockImg[a][b] = BLOCK_TREE_3;
                        break;
                    case GameMap.ROAD:
                 blockImg[a][b] = BLOCK_ROAD;
                        break;

                }
                blocks[a][b] = new Rectangle(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
                x += GameConstants.TILE_SIZE;
            }
            y += GameConstants.TILE_SIZE;
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < GameMap.getInstance().getHeight(); i++) {
            for (int j = 0; j < GameMap.getInstance().getWidth(); j++) {
                g.drawImage(blockImg[i][j], blocks[i][j].x, blocks[i][j].y, null);
            }
        }
    }

    public void moveMap() {
        for (int i = 0; i < GameMap.getInstance().getHeight(); i++) {
            for (int j = 0; j < GameMap.getInstance().getWidth(); j++) {
                blocks[i][j].x += GameNavigator.XDirection;
                blocks[i][j].y += GameNavigator.YDirection;
            }
        }
    }
}
