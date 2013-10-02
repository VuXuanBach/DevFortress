/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view;

import java.awt.image.BufferedImage;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.building.Cage;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.view.util.SpriteSheetManager;

/**
 *
 * @author Le Anh Duy
 */
public class BuildingView implements View {

    List<BufferedImage> frames;
    public BufferedImage sprite;
    private volatile boolean running = false;
    private long previousTime, frameDelay;
    private int frameAtPause, currentFrame;
    private Building building;
    private Coordinate c;

    public BuildingView(List<BufferedImage> frames, Building building) {
        this.frames = frames;
        this.building = building;
        c = new Coordinate(building.getCurrentCoordinate().getX(), building.getCurrentCoordinate().getY());
    }

    public Building getModel() {
        return building;
    }

    public void setFrameDelay(long frameDelay) {
        this.frameDelay = frameDelay;
    }

    public void draw(long time) {
        if (running) {
            if (time - previousTime >= frameDelay) {
                if (building instanceof Cage) {
                    // if the cage is not opened
                    if (!((Cage) building).isOpened()) {
                        if (currentFrame != frames.size() - 1) {
                            sprite = frames.get(currentFrame++);
                        } else {
                            sprite = frames.get(currentFrame--);
                        }
                    } else {
                        if (currentFrame != 0) {
                            sprite = frames.get(currentFrame--);
                        }
                    }
                    previousTime = time;
                } else {
                    sprite = frames.get(currentFrame);
                }
            }
        }
    }

    public void build() {
        running = true;
        previousTime = 0;
        frameAtPause = 0;
        currentFrame = 0;
    }

    public void stop() {
        running = false;
        previousTime = 0;
        frameAtPause = 0;
        currentFrame = 0;
    }

    public void pause() {
        frameAtPause = currentFrame;
        running = false;
    }

    public void resume() {
        currentFrame = frameAtPause;
        running = true;
    }

    public Coordinate getC() {
        return c;
    }

    public void setC(Coordinate c) {
        this.c = c;
    }

    public void moveWithMap() {
        c.setX(c.getX() + GameNavigator.XDirection);
        c.setY(c.getY() + GameNavigator.YDirection);
    }

    public void updateViewCoordinate() {
        c.setX(building.getCurrentCoordinate().getX() - GameNavigator.getCurrentX());
        c.setY(building.getCurrentCoordinate().getY() - GameNavigator.getCurrentY());
    }

    public Building getBuilding() {
        return building;
    }
    
    public void updateBuilding(){
        building.updateBuilding();
        frames = SpriteSheetManager.getInstance().getBuildingSprites(building.getName(), building.getImageName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BuildingView other = (BuildingView) obj;
        if (this.building != other.building && (this.building == null || !this.building.equals(other.building))) {
            return false;
        }
        if (this.c != other.c && (this.c == null || !this.c.equals(other.c))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (this.building != null ? this.building.hashCode() : 0);
        hash = 11 * hash + (this.c != null ? this.c.hashCode() : 0);
        return hash;
    }
    
    
}
