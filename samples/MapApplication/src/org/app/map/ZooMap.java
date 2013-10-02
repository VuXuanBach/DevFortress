package org.app.map;

import com.sun.j3d.utils.timer.J3DTimer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.app.map.zooitem.*;
import org.app.map.zooitem.animal.Animal;
import org.app.visitor.Visitor;
import org.app.visitor.VisitorFactory;
import org.map.controller.MouseCanvasListener;
import org.map.exception.BlockedException;
import org.map.exception.OutOfBoundException;

/**
 *
 * @author Tien Phat
 */
public class ZooMap extends JPanel implements Runnable {
    //width of the map based on number of tile

    public static final int MAP_WIDTH = 50;
    //height of the map based on number of tile
    public static final int MAP_HEIGHT = 30;
    public static final int FPS = 80;
    public static final int NO_OF_DELAY = 5;
    //list of all tiles in the map
    ArrayList<ZooTile> tiles = new ArrayList<>();
    //list of build items in the map
    ArrayList<ZooItem> items = new ArrayList<>();
    ArrayList<Visitor> visitors = new ArrayList<>();
    ArrayList<Observer> obs = new ArrayList<>();
    long period;
    private volatile boolean running = false;
    Thread animation;
    Graphics g;
    Image buffer;
    Visitor visitor;
    private int selectedItem;
    
    //added by Tam
    static int count;
    VisitorFactory factory;

    /**
     * constructor
     */
    public ZooMap() {
        
        //added by Tam
        count = 0;
        factory = new VisitorFactory();

        setBackground(Color.white);
        this.setPreferredSize(new Dimension(MAP_WIDTH * ZooTile.TILE_SIZE, MAP_HEIGHT * ZooTile.TILE_SIZE));
        this.addMouseListener(new MouseCanvasListener(this));
        setFocusable(true);
        requestFocus();

        //initialize the map with all the tiles
        //note that width represent column while height represent row 
        initTiles();
        //draw a border with rock images
        initRockBorder();
        initPath();
        
        startGame();

    }

    /**
     * calculate the period for each frame based on the required FPS
     *
     * @param fps
     */
    private void calPeriod(int fps) {
        //calculate to ms and convert to ns
        period = (1000L / fps) * 1000000L;
    }

    /**
     * initializes tiles for the map
     */
    private void initTiles() {
        for (int i = 0; i < MAP_WIDTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                tiles.add(new ZooGrass(i, j));
            }
        }
    }

    /**
     * initialize a rock border around the map
     */
    private void initRockBorder() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            if (i == 0 || i == MAP_HEIGHT - 1) {
                for (int j = 0; j < MAP_WIDTH; j++) {
                    items.add(new ZooRock(j, i));
                }
            } else {

                items.add(new ZooRock(0, i));
                items.add(new ZooRock(MAP_WIDTH - 1, i));
            }
        }
    }

    /**
     * initialize the path for the visitor to move
     */
    private void initPath() {
        for (int i = 1; i < MAP_WIDTH - 1; i++) {
            try {
                addPath(i, 1);
                addPath(i, 2);
                addPath(i, 3);
                addPath(i, MAP_HEIGHT - 2);
                addPath(i, MAP_HEIGHT - 3);
                addPath(i, MAP_HEIGHT - 4);
            } catch (OutOfBoundException ex) {
                System.out.println("Error adding near bound of map");
            } catch (BlockedException ex) {
            }
        }

        for (int i = 1; i < MAP_HEIGHT - 1; i++) {
            try {
                addPath(MAP_WIDTH / 2 - 1, i);
                addPath(MAP_WIDTH / 2, i);
                addPath(MAP_WIDTH / 2 + 1, i);
                //checkCrossRoad(MAP_WIDTH / 2 + 2, i);
            } catch (OutOfBoundException ex) {
                Logger.getLogger(ZooMap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BlockedException ex) {
                System.out.println("Error adding in blocked area");
            }
        }
    }

    public void addPath(int x, int y) throws OutOfBoundException, BlockedException {

        ZooTile tile = getTileAt(x, y);
        //check if there is a duplicate tile which is an instance of ZooSoil
        //otherwise if it's a ZooGrass then we cannot add a CrossroadTile
        if (tile != null && tile instanceof ZooSoil) {
            addTile(new CrossroadTile(x, y));
        } else {
            addTile(new ZooSoil(x, y));
        }

    }

    /**
     * start the animation thread
     */
    private void startGame() {
        if (animation == null && !running) {
            animation = new Thread(this);
        }
        animation.start();
    }
    
    public void stopGame(){
        if(animation!= null && running){
            running = false;
        }
    }

    /**
     * the game loop for constant update and render the screen
     */
    @Override
    public void run() {

        long before, after, diff, sleep;
        long oversleep = 0;
        long generate = 0;
        int no_delay = 0;
        running = true;
        //calculate the period and store in class variable
        calPeriod(FPS);

        before = J3DTimer.getValue();
        while (running) {
            gameUpdate();
            gameRender();
            gameDisplay();

            after = J3DTimer.getValue();

            diff = (after - before);
            generate += diff / 1000000;

            if (generate >= 4000) {
                addNewVisitor();
                spendMoney();
                generate = 0;
            }
            sleep = (period - diff) - oversleep;

            if (sleep > 0) {
                try {
                    //convert sleep from ns to ms
                    Thread.sleep(sleep / 1000000L);
                } catch (InterruptedException ex) {
                    oversleep = (J3DTimer.getValue() - after) - sleep;
                    System.out.println(oversleep);
                }
                //frame took longer to execute than the period calculated
            } else {
                oversleep = 0;
                //give other thread a chance to run
                if (++no_delay >= NO_OF_DELAY) {
                    Thread.yield();
                    no_delay = 0;
                }
            }

            before = J3DTimer.getValue();

        }

    }

    /**
     * add an item to the map without remove any duplicate tile below
     *
     * @param item
     * @throws OutOfBoundException
     * @throws BlockedException
     */
    public void addItem(ZooItem item) throws OutOfBoundException, BlockedException {
        if (item != null && (!checkCollision(item) || item instanceof Animal)) {
            items.add(item);
            notifyObservers(item);
        }
    }

    /**
     *
     *
     * @param item
     * @return
     */
    public boolean checkCollision(ZooItem item) {
        int x = item.getX();
        int y = item.getY();
        int width = item.getWidth() / ZooTile.TILE_SIZE;
        int height = item.getHeight() / ZooTile.TILE_SIZE;
        Rectangle newRec = new Rectangle(x, y, width, height);
        //check if the new item collides with any existing items
        for (int i = 0; i < items.size(); i++) {
            int itemX = items.get(i).getX();
            int itemY = items.get(i).getY();
            int itemWidth = items.get(i).getWidth() / ZooTile.TILE_SIZE;
            int itemHeight = items.get(i).getHeight() / ZooTile.TILE_SIZE;
            Rectangle itemRec = new Rectangle(itemX, itemY, itemWidth, itemHeight);
            if (newRec.intersects(itemRec)) {
                return true;
            }
        }
        //check if the new item collides with the path
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) instanceof ZooSoil) {
                int tileX = tiles.get(i).getX();
                int tileY = tiles.get(i).getY();
                int tileWidth = tiles.get(i).getWidth() / ZooTile.TILE_SIZE;
                int tileHeight = tiles.get(i).getHeight() / ZooTile.TILE_SIZE;
                Rectangle tileRec = new Rectangle(tileX, tileY, tileWidth, tileHeight);
                if (newRec.intersects(tileRec)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * add a tile to the map and remove duplicate one at the same location
     *
     * @param tile
     * @throws OutOfBoundException
     * @throws BlockedException
     */
    public void addTile(ZooTile newTile) throws OutOfBoundException, BlockedException {
        int x, y;
        x = newTile.getX();
        y = newTile.getY();
        Collection<ZooBuildItem> col = new ArrayList<>();

        //delete duplicate tile at the same posi
        ZooTile tile = getTileAt(x, y);
        if (tile != null) {
            col.add(tile);
        } else {
            throw new OutOfBoundException();
        }

        tiles.removeAll(col);
        tiles.add(newTile);
    }

    //this method actually remove building not builditem need to be fixed!!
    public void removeItem(ZooItem item) {
        int x, y;
        x = item.getX();
        y = item.getY();

        ZooItem bItem = getItemAt(x, y);
        if (bItem != null) {
            items.remove(bItem);
        }
    }

    /**
     * get a tile at a specified column and row location(NOT pixel location)
     *
     * @param x: the column of the tile
     * @param y: the row of the tile
     * @return
     */
    public ZooTile getTileAt(int x, int y) {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) instanceof ZooTile
                    && tiles.get(i).getX() == x && tiles.get(i).getY() == y) {
                return (ZooTile) tiles.get(i);
            }
        }

        return null;

    }

    /**
     * get a building at a specified column and row location(NOT pixel location)
     *
     * @param x
     * @param y
     * @return
     */
    public ZooItem getItemAt(int x, int y) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getX() == x && items.get(i).getY() == y) {
                if(items.get(i) instanceof Animal)
                    this.notifyObservers((Animal)items.get(i), true);
                else
                    this.notifyObservers(null,false);
                return items.get(i);
            }
        }
        return null;

    }

    /**
     * print error message
     *
     * @param string
     */
    private void error(String string) {
        System.out.println(string);
        System.exit(0);
    }

    private void gameUpdate() {
        for (int i = 0; i < visitors.size(); i++) {
            visitors.get(i).move();
        }
    }

    //render the graphics to a buffer image
    private void gameRender() {
        if (buffer == null) {
            buffer = createImage(MAP_WIDTH * ZooTile.TILE_SIZE, MAP_WIDTH * ZooTile.TILE_SIZE);
            g = buffer.getGraphics();
            if (g == null) {
                System.out.println("Cannot get graphics");
                return;
            }
        }
        //clear background
        g.setColor(Color.white);
        g.fillRect(0, 0, MAP_WIDTH * ZooTile.TILE_SIZE, MAP_WIDTH * ZooTile.TILE_SIZE);


        //render the tiles
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).paint(g);
        }
        //render all items on the map
        for (int i = 0; i < items.size(); i++) {
            items.get(i).paint(g);
        }
        //render the visitors
        for (int i = 0; i < visitors.size(); i++) {
            visitors.get(i).draw(g);
        }
    }

    //render buffer to screen
    private void gameDisplay() {
        //graphic object of the panel
        Graphics pg;

        pg = this.getGraphics();
        if (pg != null && buffer != null) {
            pg.drawImage(buffer, 0, 0, null);
        }
        Toolkit.getDefaultToolkit().sync();
        pg.dispose();
    }
    /*
     * Added Acay13
     */

    public ZooItem getItemWaterCageAt(int x, int y) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof ZooCageWater) {
                if (x < (items.get(i).getX() + 2) || y < (items.get(i).getY() + 2) || x > (items.get(i).getX() + 8) || y > (items.get(i).getY() + 6)) {
                    continue;
                } else {
                    //System.out.println("Found " + items.get(i).getX() + " " + items.get(i).getY());
                    return items.get(i);
                }
            }
        }
        return null;
    }
    /*
     * Added Acay13
     */

    public ZooItem getItemLandCageAt(int x, int y) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof ZooCageLand) {
                if (x < (items.get(i).getX() + 2) || y < (items.get(i).getY() + 2) || x > (items.get(i).getX() + 8) || y > (items.get(i).getY() + 6)) {
                    continue;
                } else {
                    //System.out.println("Found " + items.get(i).getX() + " " + items.get(i).getY());
                    return items.get(i);
                }
            }
        }
        return null;
    }
    /*
     * Added Acay13
     */

    public ZooItem getItemAirCageAt(int x, int y) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof ZooCageAir) {
                if (x < (items.get(i).getX() + 2) || y < (items.get(i).getY() + 2) || x > (items.get(i).getX() + 8) || y > (items.get(i).getY() + 6)) {
                    continue;
                } else {
                    //System.out.println("Found " + items.get(i).getX() + " " + items.get(i).getY());
                    return items.get(i);
                }
            }
        }
        return null;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
    
    public void addObserver(Observer ob){
        obs.add(ob);
    }

    private void notifyObservers(ZooBuildItem item) {
        for(int i=0; i<obs.size(); i++){
            obs.get(i).update(item);
        }        
    }
    private void notifyObservers(Visitor visitor){
        for(int i=0; i<obs.size();i++){
            obs.get(i).update(visitor);
        }
    }
    public  void notifyObservers(Animal a,boolean status) {
        for(int i=0; i<obs.size(); i++){
            obs.get(i).update(a,status);
        }        
    }
    
    //added by Tam
    public void addNewVisitor(){
        count++;
        visitor = factory.createVisitor(count, this);
        //t = new Thread(visitor); // create thread using a Runnable object
        //t.start(); 
        visitors.add(visitor);
        notifyObservers(visitor);
    }
    
    private void spendMoney(){
        for (int i = 0; i < visitors.size(); i++){
            if (visitors.get(i).spendMoney()==false){
                System.out.println("Remove Visitor " + visitors.get(i).getNumber());
                visitors.remove(visitors.get(i));
            }
        }
    }
    
    public int getTotalVisitorsSize(){
        return visitors.size();
    }
    
    public ArrayList getTotalVisitors(){
        return visitors;
    }
}
