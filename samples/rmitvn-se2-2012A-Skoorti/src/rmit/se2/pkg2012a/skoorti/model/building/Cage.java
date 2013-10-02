/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.building;

import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.GameMap;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;

/**
 *
 * @author Le Anh Duy
 */
public class Cage extends Building {

    private boolean opened;
    private Animal animal;

    public Cage(String name, Coordinate currentCoordinate, int width, int height) {
        super(name, currentCoordinate, width, height, "cage.png");
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getDirection() {
        int x = getCurrentCoordinate().getX() / GameConstants.TILE_SIZE;
        int y = getCurrentCoordinate().getY() / GameConstants.TILE_SIZE;
        int width = getWidth() / GameConstants.TILE_SIZE;
        int height = getHeight() / GameConstants.TILE_SIZE;
        int direction = 10;
        if (GameMap.getInstance().getBlockStatus(y - 1, x) == 'r') {
            direction = GameConstants.UP;
        }
        if (GameMap.getInstance().getBlockStatus(y + height, x) == 'r') {
            direction = GameConstants.DOWN;
        }
        if (GameMap.getInstance().getBlockStatus(y, x - 1) == 'r') {
            direction = GameConstants.LEFT;
        }
        if (GameMap.getInstance().getBlockStatus(y, x + width) == 'r') {
            direction = GameConstants.RIGHT;
        }
        return direction;
    }

    @Override
    public float getCost() {
        return 100;
    }

    @Override
    public void setCost(float cost) {
        super.setCost(cost);
    }

    @Override
    public String log() {
        return "You have built new cage for " + this.getName();
    }

    @Override
    public void updateBuilding() {
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    
    
    
}
