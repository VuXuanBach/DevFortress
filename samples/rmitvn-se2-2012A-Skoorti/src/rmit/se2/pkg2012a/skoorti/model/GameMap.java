package rmit.se2.pkg2012a.skoorti.model;

import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmit.se2.pkg2012a.skoorti.GameConstants;

public class GameMap implements Serializable{
  private static GameMap _instance;
    // block data
    private char[][] blockData;
    private static String mapFileName;
    private int width;
    private int height;
//    public static final int ROW_NUM = 59;
//    public static final int COL_NUM = 60;
//    public static final int ROW_NUM = 14;
//    public static final int COL_NUM = 16;
    public static final char GRASS = '-', WALL = 'w', LAND = ' ', ROAD = 'r',
            TREE1 = 't', TREE2 = 'T', TREE3 = 'c', CAGE = 'g', RESTAURANT = 'a',
            MALL = 'm', SHOP = 's',
            DOOR = '1', DOOR1 = '1', DOOR2 = '2', DOOR3 = '3', DOOR4 = '4', DOOR5 = '5';

    private GameMap() throws FileNotFoundException {
        this.mapFileName = "src/maps/map1.txt";
        initBlockData();
    }
    private GameMap(String mapFileName) throws FileNotFoundException {
        this.mapFileName = mapFileName;
        initBlockData();
    }
    
    public static GameMap getInstance() {
      if (_instance == null) {
        try {
          _instance = new GameMap();
        } catch (FileNotFoundException ex) {
          Logger.getLogger(GameMap.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      return _instance;
    }
    
    public static GameMap getInstance(String mapFileName) {
      if (_instance == null) {
        try {
          _instance = new GameMap(mapFileName);
        } catch (FileNotFoundException ex) {
          Logger.getLogger(GameMap.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      return _instance;
    }

    public void initBlockData() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(mapFileName));
        ArrayList<String> lines = new ArrayList<String>();
        while (scan.hasNextLine()) {
            lines.add(scan.nextLine());
        }
        scan.close();
        height = lines.size();
        width = lines.get(0).length();
        blockData = new char[height][width];
        for (int a = 0; a < height; a++) {
            for (int b = 0; b < width; b++) {
                blockData[a][b] = lines.get(a).charAt(b);
            }
        }
    }
    
    public char[][] getBlockData() {
        return blockData;
    }

    public char getBlockStatus(int x, int y) {
        //System.out.println("x = " + x + " y = " + y);
//        if (x<0 || y<0 || x>=COL_NUM || y>=ROW_NUM)
//            throw new ZooGameException();
        return blockData[x][y];
    }
    
          public int parseRow(Coordinate c) {
    return (int) Math.round(c.getY() / 32.0);
  }
  
  public int parseCol(Coordinate c) {
    return (int) Math.round(c.getX() / 32.0);
  }
  
  public int[] getRandomGate() {
    List<int[]> cages = new ArrayList<int[]>();
    for (int i = 0; i < blockData.length; i++) {
      for (int j = 0; j < blockData[i].length; j++) {
        if (blockData[i][j] == DOOR) {
          cages.add(new int[]{i, j});
        }
      }
    }
    Random rd = new Random();
    return cages.get(rd.nextInt(cages.size()));
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
    
    /**
     * 
     * @param x col number
     * @param y row number
     * @param width number of tiles occupied by the width
     * @param height number of tiles occupied by the height
     */
    public void setBuildingStatus(int x, int y, int width, int height){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                blockData[y + j][x + i] = 'b';
            }
        }
    }

    public void removeBuildingStatus(int x, int y, int width, int height){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                blockData[y + j][x + i] = LAND;
            }
        }
    }
    
    public int[][] getEnterableMap() {
      int[][] map = new int[blockData.length][blockData[0].length];
    for (int i = 0; i < blockData.length; i++) {
      for (int j = 0; j < blockData[i].length; j++) {
        if(blockData[i][j] == ROAD || blockData[i][j] == DOOR)
          map[i][j] = 0;
        else
          map[i][j] = 1;
      }
    }
    return map;
  }

    public List<Coordinate> getGateNeighbors() {
      List<Coordinate> gateNeighbors = new ArrayList<Coordinate>();
      int[][] motions = GameConstants.MOVECOLLECTIONS;
      for(int i=0; i<height; i++)
        for(int j=0; j<width; j++) {
          if(blockData[i][j] == DOOR) {
            for(int a = 0; a<motions.length; a++) {
              int i2 = i+motions[a][0];
              int j2 = j+motions[a][1];
              if (i2>=0 && i2<height && j2>=0 && j2<width && blockData[i2][j2]==ROAD)
                gateNeighbors.add(new Coordinate(j2*GameConstants.TILE_SIZE, i2*GameConstants.TILE_SIZE));
            }
          }
        }
      return gateNeighbors;
    }
    
    public List<Coordinate> getGates() {
      List<Coordinate> gates = new ArrayList<Coordinate>();
      for(int i=0; i<height; i++)
        for(int j=0; j<width; j++) {
          if(blockData[i][j] == DOOR) {
            gates.add(new Coordinate(j*GameConstants.TILE_SIZE, i*GameConstants.TILE_SIZE));
          }
        }
      return gates;
    }
}
