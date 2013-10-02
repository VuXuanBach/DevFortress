/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.component;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import view.animation.GameCanvas;

/**
 *
 * @author BACH
 */
public class Sprite {

    public BufferedImage image = null;   // image of the sprite
    // initial coordinates and velocity of the sprite
    public int x = 0, y = 300, count, xv = 0, yv = 3, width = 64, height = 64;
    private int animationFrame = 0;   // incremented for every frame.
    private int gesture = 0;          // animation gesture (0-3)
    public int relativeSpeed = 1;    // update position at every 2 frames (moving half slower)
    public int gestureWait = 10;      // update animation gesture at every 8 frames
    private final int[][] UP = {{1, 3}, {0, 0}, {2, 0}};
    private final int[][] DOWN = {{2, 2}, {2, 1}, {2, 3}};
    private final int[][] LEFT = {{0, 1}, {0, 2}, {0, 3}};
    private final int[][] RIGHT = {{1, 1}, {1, 0}, {1, 2}};
    private final int STEPMOVE = 3;
    private boolean collision, isMoving;
    private int collStepCount;
    // pre-defined source regions
    private int[][][] frameRect = // [direction][gesture][x,y,w,h]
            {
        {{0, 0, width, height}, {width, 0, width, height}, {width * 2, 0, width, height}}, // left
        {{0, height, width, height}, {width, height, width, height}, {width * 2, height, width, height}}, // right
        {{0, height * 2, width, height}, {width, height * 2, width, height}, {width * 2, height * 2, width, height}}, // down
        {{0, height * 3, width, height}, {width, height * 3, width, height}, {width * 2, height * 3, width, height}} // up
    };

    public Sprite(BufferedImage image, int width, int height) {
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void setStepMove(int xv, int yv) {
        this.xv = xv;
        this.yv = yv;
    }

//    public BufferedImage makeTransparent(BufferedImage tmpImage) {
//        int h = tmpImage.getHeight(null);
//        int w = tmpImage.getWidth(null);
//        
//        BufferedImage resultImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//
//        // assume the upperleft corner of the original image is a transparent pixel
//        int transparentColor = tmpImage.getRGB(0, 0);
//        for (int y = 0; y < h; y++) {
//            for (int x = 0; x < w; x++) {
//                int color = tmpImage.getRGB(x, y);
//                if (color == transparentColor) {
//                    color = color & 0x00FFFFFF; // clear the alpha flag
//                }
//                resultImage.setRGB(x, y, color);
//            }
//        }
//        
//        return resultImage;
//    }
    // activeRegion is defined by corners coordinates (x1,y1) and (x2,y2)
    public int[] getActiveRegion() {
        // upperLeft corner(x,y),  lowerRight corner(x,y)
        return new int[]{0, 0, 1024, 768};  // lower area of the screen.
    }

    public int rotation() {

        if (checkCollision(0, -STEPMOVE)) {
            return 2;
        }
        if (checkCollision(0, STEPMOVE)) {
            return 3;
        }
        if (checkCollision(-STEPMOVE, 0)) {
            return 0;
        }
        if (checkCollision(STEPMOVE, 0)) {
            return 1;
        }
        return 3;
    }

    public ArrayList<int[]> moveable() {
        ArrayList<int[]> goodWay = new ArrayList<int[]>();

        if (!checkCollision(0, -STEPMOVE)) {
            int[] move = {0, -STEPMOVE};
            goodWay.add(move);
        }
        if (!checkCollision(0, STEPMOVE)) {
            int[] move = {0, STEPMOVE};
            goodWay.add(move);
        }
        if (!checkCollision(-STEPMOVE, 0)) {
            int[] move = {-STEPMOVE, 0};
            goodWay.add(move);
        }
        if (!checkCollision(STEPMOVE, 0)) {
            int[] move = {STEPMOVE, 0};
            goodWay.add(move);
        }

        return goodWay;
    }

    public void move(int desX, int desY) {
        if (Math.abs(x - desX) < 3 && Math.abs(y - desY) < 3) {
            x = desX;
            y = desY;
            System.out.println("reach destination");
            isMoving = false;
        } else {
            isMoving = true;
            ArrayList<int[]> directions = moveable();

            int[] shortest = directions.get(0);
            for (int i = 1; i < directions.size(); i++) {
                if (Math.abs(x + directions.get(i)[0] - desX) < Math.abs(x + shortest[0] - desX)
                        || Math.abs(y + directions.get(i)[1] - desY) < Math.abs(y + shortest[1] - desY)) {
                    shortest = directions.get(i);
                }
            }

            setStepMove(shortest[0], shortest[1]);
            move();
        }
    }

    public void move() {
        animationFrame++;

        x += xv;
        y += yv;

        int rightBound = getActiveRegion()[2];
        int leftBound = getActiveRegion()[0];
        int upperBound = getActiveRegion()[1];
        int lowerBound = getActiveRegion()[3];

        if (x > rightBound - width || x < leftBound) // hit border
        {
            x -= xv;      // undo movement
            xv = -xv;     // change direction of velocity
        }

        if (y > lowerBound - height || y < upperBound) // don't move too high or too low
        {
            y -= yv;      // undo movement
            yv = -yv;     // change direction of velocity
        }
        isCollision();
    }

    public int[] getImageBound(int direction, int gesture) {
        return frameRect[direction][gesture];
    }

    public boolean isCollision() {

        Rectangle dev = new Rectangle(x + xv, y + yv, width, height);

        for (Rectangle r : GameCanvas.obstacleList) {
            if (r.intersects(dev)) {
                collision = true;
                System.out.println("collide! --> change direction");
                return true;
            }
        }
        return false;
    }

    public boolean checkCollision(int xv, int yv) {

        Rectangle dev = new Rectangle(x + xv, y + yv, width, height);

        for (Rectangle r : GameCanvas.obstacleList) {
            if (r.intersects(dev)) {
                return true;
            }
        }
        return false;
    }

    public void draw(java.awt.Graphics2D gr) {
        int[][] frames = null;
        int[] srcBounds = null;
        // draw sprite at new position

        int direction = 0;
        if (xv > 0) {
            direction = 1;
        } else if (xv < 0) {
            direction = 0;
        }
        if (yv > 0) {
            direction = 3;
        } else if (yv < 0) {
            direction = 2;
        }

        if (isMoving) {
            frames = getDirectionFrames(direction);
            srcBounds = getImageBound(frames[count][1], frames[count][0]);

            if (count >= 2) {
                count = 0;
            } else {
                count++;
            }
        } else {
            direction = rotation();
            System.out.println("rotate face to " + direction);
            frames = getDirectionFrames(direction);
            srcBounds = getImageBound(frames[1][1], frames[1][0]);
        }
        int w = srcBounds[2], h = srcBounds[3];
        int dx2 = x + w;
        int dy2 = y + h;
        int sx1 = srcBounds[0], sy1 = srcBounds[1], sx2 = sx1 + w, sy2 = sy1 + h;
        gr.drawImage(image, x, y, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }

    private int[][] getDirectionFrames(int direction) {
        switch (direction) {
            case 0:
                return LEFT;
            case 1:
                return RIGHT;
            case 2:
                return UP;
            case 3:
                return DOWN;
        }
        return UP;
    }
}
