/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view;

import java.awt.image.BufferedImage;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;

/**
 *
 * @author Le Anh Duy
 */
public class AnimalView implements View {

    List<BufferedImage> moveDownFrames;
    List<BufferedImage> moveRightFrames;
    List<BufferedImage> moveUpFrames;
    List<BufferedImage> moveLeftFrames;
    public BufferedImage sprite;
    private volatile boolean running = false;
    private long previousTime, frameDelay;
    private int frameAtPause, currentFrame, currentDirection;
    private Animal animal;
    private FireView fireView; 
    private Coordinate c;

    public AnimalView(List<BufferedImage> moveDownFrames,
            List<BufferedImage> moveRightFrames,
            List<BufferedImage> moveUpFrames,
            List<BufferedImage> moveLeftFrames, Animal animal) {
        this.moveDownFrames = moveDownFrames;
        this.moveRightFrames = moveRightFrames;
        this.moveUpFrames = moveUpFrames;
        this.moveLeftFrames = moveLeftFrames;
        this.animal = animal;
        c = new Coordinate(animal.getCurrentCoordinate().getX(), animal.getCurrentCoordinate().getY());
    }

    public Animal getModel() {
        return animal;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public FireView getFireView() {
        return fireView;
    }
    
    public void setFireView(FireView fireView) {
        this.fireView = fireView;
    }

    public void setFrameDelay(long frameDelay) {
        this.frameDelay = frameDelay;
    }

    public void update(long time) {
        if (running) {
            if (time - previousTime >= frameDelay) {
                move();
                fireView.update(time, currentDirection);
                fireView.changeDirection(c.getX(), c.getY());
                previousTime = time;
            }
        }
    }

    public void start() {
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
        fireView.moveWithMap();
    }

    public void updateViewCoordinate() {
        if (animal.isFined()) {
            c.setX(animal.getCurrentCoordinate().getX() - GameNavigator.getCurrentX() + 32);
            c.setY(animal.getCurrentCoordinate().getY() - GameNavigator.getCurrentY() + 64);
        } else {
            if (animal.getMovable().getDirection() == GameConstants.LEFT
                    || animal.getMovable().getDirection() == GameConstants.RIGHT) {
                c.setX(animal.getCurrentCoordinate().getX() - GameNavigator.getCurrentX());
                c.setY(animal.getCurrentCoordinate().getY() - GameNavigator.getCurrentY() - 32);
            } else {
                c.setX(animal.getCurrentCoordinate().getX() - GameNavigator.getCurrentX() - 16);
                c.setY(animal.getCurrentCoordinate().getY() - GameNavigator.getCurrentY());
            }
        }
    }

    private void move() {
        // handle movement AI from the model and get the direction
        int direction = GameConstants.DOWN;
        if (!animal.isFined()) {
            direction = animal.move();
        }

        // same direction
        if (direction == currentDirection) {
            currentFrame = ++currentFrame % 2;
            switch (currentDirection) {
                case GameConstants.DOWN:
                    sprite = moveDownFrames.get(currentFrame);
                    break;
                case GameConstants.RIGHT:
                    sprite = moveRightFrames.get(currentFrame);
                    break;
                case GameConstants.UP:
                    sprite = moveUpFrames.get(currentFrame);
                    break;
                case GameConstants.LEFT:
                    sprite = moveLeftFrames.get(currentFrame);
                    break;
            }
        } else {// different direction
            currentFrame = 1;
            switch (direction) {
                case GameConstants.DOWN:
                    currentDirection = GameConstants.DOWN;
                    sprite = moveDownFrames.get(currentFrame);
                    break;
                case GameConstants.RIGHT:
                    currentDirection = GameConstants.RIGHT;
                    sprite = moveRightFrames.get(currentFrame);
                    break;
                case GameConstants.UP:
                    currentDirection = GameConstants.UP;
                    sprite = moveUpFrames.get(currentFrame);
                    break;
                case GameConstants.LEFT:
                    currentDirection = GameConstants.LEFT;
                    sprite = moveLeftFrames.get(currentFrame);
                    break;
            }
        }
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimalView other = (AnimalView) obj;
        if (this.animal != other.animal && (this.animal == null || !this.animal.equals(other.animal))) {
            return false;
        }
        if (this.c != other.c && (this.c == null || !this.c.equals(other.c))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.animal != null ? this.animal.hashCode() : 0);
        hash = 67 * hash + (this.c != null ? this.c.hashCode() : 0);
        return hash;
    }
}