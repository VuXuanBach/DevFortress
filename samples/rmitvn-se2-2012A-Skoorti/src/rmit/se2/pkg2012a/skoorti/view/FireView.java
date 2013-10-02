/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view;

import java.awt.image.BufferedImage;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.animal.Fire;

/**
 *
 * @author Le Anh Duy
 */
public class FireView implements View {

    List<BufferedImage> moveDownFrames;
    List<BufferedImage> moveRightFrames;
    List<BufferedImage> moveUpFrames;
    List<BufferedImage> moveLeftFrames;
    public BufferedImage sprite;
    private volatile boolean running = false;
    private long previousTime, frameDelay;
    private int frameAtPause, currentFrame, currentDirection;
    private Coordinate c;
    private Coordinate centerPoint;
    private Fire fire;

    public FireView(List<BufferedImage> moveDownFrames,
            List<BufferedImage> moveRightFrames,
            List<BufferedImage> moveUpFrames,
            List<BufferedImage> moveLeftFrames, Fire fire) {
        this.moveDownFrames = moveDownFrames;
        this.moveRightFrames = moveRightFrames;
        this.moveUpFrames = moveUpFrames;
        this.moveLeftFrames = moveLeftFrames;
        this.fire = fire;
        c = new Coordinate();
        centerPoint = new Coordinate();
    }

    public void setFrameDelay(long frameDelay) {
        this.frameDelay = frameDelay;
    }

    public void update(long time, int direction) {
        if (running) {
            if (time - previousTime >= frameDelay) {
                changeDirection(direction);
                previousTime = time;
            }
        }
    }

    public Coordinate getC() {
        return c;
    }

    public Coordinate getCenterPoint() {
        int x = c.getX();
        int y = c.getY();
        switch (currentDirection) {
            case GameConstants.UP:
                x += 16;
                y -= 32;
                break;
            case GameConstants.DOWN:
                x += 16;
                y -= 32;
                break;
            case GameConstants.LEFT:
                x += 32;
                y += 16;
                break;
            case GameConstants.RIGHT:
                x += 32;
                y += 16;
                break;
        }
        centerPoint.setX(x);
        centerPoint.setY(y);
        return centerPoint;
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

    public void changeDirection(int direction) {
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

    public void changeDirection(int x, int y) {
        switch (currentDirection) {
            case GameConstants.UP:
                y -= 48;
                break;
            case GameConstants.DOWN:
                y += 64;
                break;
            case GameConstants.LEFT:
                x -= 64;
                y += 32;
                break;
            case GameConstants.RIGHT:
                x += 64;
                y += 32;
                break;
        }
        c.setX(x);
        c.setY(y);
    }

    @Override
    public void updateViewCoordinate() {
    }

    @Override
    public void moveWithMap() {
        c.setX(c.getX() + GameNavigator.XDirection);
        c.setY(c.getY() + GameNavigator.YDirection);
    }
}