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
public class BurnView implements View {

    private List<BufferedImage> frames;
    public BufferedImage sprite;
    private volatile boolean running = false;
    private long previousTime, frameDelay;
    private int frameAtPause, currentFrame;
    private Coordinate c;

    public BurnView(List<BufferedImage> frames) {
        this.frames = frames;
        c = new Coordinate();
    }

    public void setFrameDelay(long frameDelay) {
        this.frameDelay = frameDelay;
    }

    public void update(long time) {
        if (running) {
            if (time - previousTime >= frameDelay) {
                sprite = frames.get(++currentFrame % 2);
                previousTime = time;
            }
        }
    }

    public Coordinate getC() {
        return c;
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

    public void updatePosition(int x, int y){
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
