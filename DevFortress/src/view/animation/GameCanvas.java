/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import view.component.Background;
import view.util.ViewUtilities;
import view.component.Sprite;

/**
 *
 * @author BACH
 */
public class GameCanvas extends JPanel implements Runnable {

    private Background background;
    private Image image;
    private Graphics grp;
    private ArrayList<Sprite> spriteList = new ArrayList<>();
    public static ArrayList<Rectangle> obstacleList = new ArrayList<>();
    private Graphics2D g2d;

    public GameCanvas() {
        background = new Background();
        try {
            initSpriteList("female25.png");
            obstacleList.add(new Rectangle(147, 287, 32, 96));
            obstacleList.add(new Rectangle(150, 38, 32, 96));
            obstacleList.add(new Rectangle(265, 37, 32, 96));
            obstacleList.add(new Rectangle(260, 326, 32, 96));
            obstacleList.add(new Rectangle(350, 115, 32, 96));
//            obstacleList.add(new Rectangle(414, 206, 96, 32));
            obstacleList.add(new Rectangle(520, 255, 96, 32));
            obstacleList.add(new Rectangle(677, 395, 96, 32));
            obstacleList.add(new Rectangle(700, 88, 96, 32));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Rectangle> getObstacleList() {
        return obstacleList;
    }

    public void rendering() {
        if (image == null) { // Create the buffer
            image = createImage(ViewUtilities.GAME_WIDTH, ViewUtilities.GAME_HEIGHT);
            if (image == null) {
                return;
            } else {
                grp = image.getGraphics();
            }
        }
        //Clear the screen
        grp.setColor(Color.WHITE);
        grp.fillRect(0, 0, ViewUtilities.GAME_WIDTH, ViewUtilities.GAME_HEIGHT);
        paint(grp);

        //Draw Game elements

    }

    public void gameDisplay() {
        //graphic object of the panel
        Graphics pg;

        pg = this.getGraphics();
        if (pg != null && image != null) {
            pg.drawImage(image, 0, 0, null);
        }
        Toolkit.getDefaultToolkit().sync();
        pg.dispose();
    }

    public void initSpriteList(String imgName) throws IOException {
        BufferedImage imageL = ViewUtilities.makeBufferedImage("characters/" + imgName);

        Sprite sprite = new Sprite(imageL, 64, 64);
        spriteList.add(sprite);
    }

    private void animateFrame() {
        for (int i = 0; i < spriteList.size(); i++) {
            Sprite sprite = spriteList.get(i);
            sprite.move(700 + 48, 88 + 33);
            sprite.draw(g2d);
        }
    }

    @Override
    public void paint(Graphics g) {
        setPreferredSize(new Dimension(1366, 768));
        revalidate();
        background.draw(g);
        for (Rectangle r : obstacleList) {
            g.fillRect(r.x, r.y, r.width, r.height);
        }

        g2d = (java.awt.Graphics2D) g;

        try {
            animateFrame();
            Thread.sleep(40);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            rendering();
            gameDisplay();
        }
    }
}
