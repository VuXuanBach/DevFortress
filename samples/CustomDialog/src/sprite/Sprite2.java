/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import java.awt.image.BufferedImage;

class Sprite2 {

    public BufferedImage image = null;   // image of the sprite
    // initial coordinates and velocity of the sprite
    public int x = 50, y = 250;
    private int xv = 2;
    private int yv = 0;
    private int w = 32, h = 32;
    private int animationFrame = 0;   // incremented for every frame.
    private int gesture = 0;          // animation gesture (0-3)
    public int relativeSpeed = 2;    // update position at every 2 frames (moving half slower)
    public int gestureWait = 10;      // update animation gesture at every 8 frames
    java.util.Random random = new java.util.Random();
    // pre-defined source regions
    private int[][][] frameRect = // [direction][gesture][x,y,w,h]
            {
        {{0, 0, w, h}, {32, 0, w, h}, {64, 0, w, h}}, // left
        {{0, 32, w, h}, {32, 32, w, h}, {64, 32, w, h}}, // right
        {{0, 64, w, h}, {32, 64, w, h}, {64, 64, w, h}}, // down
        {{0, 96, w, h}, {32, 96, w, h}, {64, 96, w, h}} // up
    };

    public Sprite2(BufferedImage img) {
        this.image = makeTransparent(img);
    }

    public BufferedImage makeTransparent(BufferedImage tmpImage) {
        int h = tmpImage.getHeight(null);
        int w = tmpImage.getWidth(null);

        BufferedImage resultImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // assume the upperleft corner of the original image is a transparent pixel
        int transparentColor = tmpImage.getRGB(0, 0);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int color = tmpImage.getRGB(x, y);
                if (color == transparentColor) {
                    color = color & 0x00FFFFFF; // clear the alpha flag
                }
                resultImage.setRGB(x, y, color);
            }
        }

        return resultImage;
    }

    // activeRegion is defined by corners coordinates (x1,y1) and (x2,y2)
    public int[] getActiveRegion() {
        // upperLeft corner(x,y),  lowerRight corner(x,y)
        return new int[]{0, 250, 750, 440};  // lower area of the screen.
    }

    public void move() {
        animationFrame++;
        if (animationFrame % relativeSpeed == 0) // control when to update the position
        {
//            int bored = random.nextInt(200);
//            if (bored == 1) // the sprite is bored for 0.5% of the time
//            {
//                // if it feels bored, it changes direction
//                int[] xvTable = {-2, 2, 0, 0};
//                int[] yvTable = {0, 0, 2, -2};
//                int dir = random.nextInt(4);
//                xv = xvTable[dir];
//                yv = yvTable[dir];
//            }

            // move sprite to new position
//            x += xv;
//            y += yv;
            x = 0; y += 2;
            

            // get Sprite dimension
            int w = frameRect[0][0][2];  // 32
            int h = frameRect[0][0][3];  // 32

            int rightBound = getActiveRegion()[2];
            int leftBound = getActiveRegion()[0];
            int upperBound = getActiveRegion()[1];
            int lowerBound = getActiveRegion()[3];

            if (x > rightBound - w || x < leftBound) // hit border
            {
                x -= xv;      // undo movement
                xv = -xv;     // change direction of velocity
            }

            if (y > lowerBound - h || y < upperBound) // don't move too high or too low
            {
                y -= yv;      // undo movement
                yv = -yv;     // change direction of velocity
            }

        }

        if (animationFrame % gestureWait == 0) // control when to update gesture
        {
            gesture = 0;
        }

    }

    public int[] getImageBound(int direction, int gesture) {
        return frameRect[direction][gesture];
    }

    public void draw(java.awt.Graphics2D gr) {
        // draw sprite at new position

        int direction = 0;
        if (xv > 0) {
            direction = 1;
        } else if (xv < 0) {
            direction = 0;
        }
        if (yv > 0) {
            direction = 2;
        } else if (yv < 0) {
            direction = 2;
        }
        //int[] srcBounds = getImageBound(direction, gesture);
        int[] srcBounds = getImageBound(0, 0);
        int w = srcBounds[2], h = srcBounds[3];
        int dx2 = x + w;
        int dy2 = y + h;
        int sx1 = srcBounds[0], sy1 = srcBounds[1], sx2 = sx1 + w, sy2 = sy1 + h;
        gr.drawImage(image, x, y, dx2, dy2, sx1, sy1, sx2, sy2, null);

    }
}
