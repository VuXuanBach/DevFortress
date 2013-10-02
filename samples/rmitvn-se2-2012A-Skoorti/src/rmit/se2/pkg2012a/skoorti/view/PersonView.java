package rmit.se2.pkg2012a.skoorti.view;

import java.awt.image.BufferedImage;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.person.Person;

/**
 *
 * @author Le Anh Duy
 */
public class PersonView implements View {

    List<BufferedImage> moveDownFrames;
    List<BufferedImage> moveRightFrames;
    List<BufferedImage> moveUpFrames;
    List<BufferedImage> moveLeftFrames;
    public BufferedImage sprite;
    private volatile boolean running = false;
    private boolean selected;
    private long previousTime, frameDelay;
    private int frameAtPause, currentFrame, currentDirection;
    private Person person;
    private Coordinate c;
    private Coordinate centerPoint;
    private BurnView burnView;

    public PersonView(List<BufferedImage> moveDownFrames,
            List<BufferedImage> moveRightFrames,
            List<BufferedImage> moveUpFrames,
            List<BufferedImage> moveLeftFrames, Person person) {
        this.moveDownFrames = moveDownFrames;
        this.moveRightFrames = moveRightFrames;
        this.moveUpFrames = moveUpFrames;
        this.moveLeftFrames = moveLeftFrames;
        this.person = person;
        selected = false;
        this.c = new Coordinate();
        centerPoint = new Coordinate();
    }

    public Person getModel() {
        return person;
    }

    public BurnView getBurnView() {
        return burnView;
    }

    public void setBurnView(BurnView burnView) {
        this.burnView = burnView;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setFrameDelay(long frameDelay) {
        this.frameDelay = frameDelay;
    }

    private int dyingTime = 0;
    
    public void update(long time) {
        if (running) {
            if (time - previousTime >= frameDelay) {
                if (!person.isBurn()) {
                    move();
                    previousTime = time;
                } else {
                    burnView.update(time);
                    dyingTime++;
                    if(dyingTime > 600){
                        person.setDead(true);
                    }
                }
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

    public Coordinate getCenterPoint() {
        centerPoint.setX(c.getX() + 16);
        centerPoint.setY(c.getY() + 16);
        return centerPoint;
    }

    public void moveWithMap() {
        c.setX(c.getX() + GameNavigator.XDirection);
        c.setY(c.getY() + GameNavigator.YDirection);
        burnView.moveWithMap();
    }

    public void updateViewCoordinate() {
        c.setX(person.getCurrentCoordinate().getX() - GameNavigator.getCurrentX());
        c.setY(person.getCurrentCoordinate().getY() - GameNavigator.getCurrentY());
        burnView.updatePosition(c.getX(), c.getY());
    }

    private void move() {
        // handle movement AI from the model and get the direction
        int direction = person.move();
        // same direction
        if (direction == currentDirection) {
            // if the person stop, keep the frame
            if (person.getMb().getSpeed() != 0) {
                currentFrame = ++currentFrame % 3;
            }
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
}