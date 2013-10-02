/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.game.gamecanvas;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import view.game.cache.SpriteCache;

/**
 *
 * @author DELL XPS
 */
public class GameCanvas extends Canvas implements Runnable {

    private int width, height;
    private Thread t;
    private BufferStrategy Buffer;

    public GameCanvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addNotify() {
        super.addNotify();
        while (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

    @Override
    public void run() {
        createBufferStrategy(2);
        Buffer = getBufferStrategy();

        while (true) {
            long startTime = System.currentTimeMillis();
            update();
            render();
            long waitTime = System.currentTimeMillis() - startTime;
            if (waitTime < 10) {
                waitTime = 10;
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void update() {
    }

    private void render() {
        Graphics g = Buffer.getDrawGraphics();
        Image i = SpriteCache.loadImage("littleroottown.png");
        g.drawImage(i, width, width, this);
        
        if(!Buffer.contentsLost()) {
            Buffer.show();
        }
    }
}
