/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.component;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import view.util.ViewUtilities;

/**
 *
 * @author BACH
 */
public class Background {

    private final char FLOOR = 'f';
    private char[][] tiled;
    private Rectangle blocks[][];
    private Image imgBlocks[][];
    private Image imgTile;
    private int x, y;
    private int count;
    
    public Background() {
        init();
        initTile();
        load();
    }

    private void init() {
        tiled = new char[ViewUtilities.NUM_BLOCK_WIDTH][ViewUtilities.NUM_BLOCK_HEIGHT];
        blocks = new Rectangle[ViewUtilities.NUM_BLOCK_WIDTH][ViewUtilities.NUM_BLOCK_HEIGHT];
        imgBlocks = new Image[ViewUtilities.NUM_BLOCK_WIDTH][ViewUtilities.NUM_BLOCK_HEIGHT];
        imgTile = ViewUtilities.makeIcon("/tiles/floor.png").getImage();

    }

    private void initTile() {
        for (int i = 0; i < ViewUtilities.NUM_BLOCK_HEIGHT; i++) {
            for (int j = 0; j < ViewUtilities.NUM_BLOCK_WIDTH; j++) {
                tiled[j][i] = FLOOR;
            }
        }
    }

    private void load() {
        for (int i = 0; i < ViewUtilities.NUM_BLOCK_HEIGHT; i++) {
            for (int j = 0; j < ViewUtilities.NUM_BLOCK_WIDTH; j++) {
                imgBlocks[j][i] = check(tiled[j][i]);
                blocks[j][i] = new Rectangle(y, x, ViewUtilities.BLOCK_SIZE, ViewUtilities.BLOCK_SIZE);
                y += ViewUtilities.BLOCK_SIZE;
            }
            x += ViewUtilities.BLOCK_SIZE;
            y = 0;
        }
    }

    private Image check(char c) {
        switch (c) {
            case FLOOR:
                return imgTile;
        }
        return imgTile;
    }

    public void draw(Graphics g) {
        for (int i = 0; i < ViewUtilities.NUM_BLOCK_HEIGHT; i++) {
            for (int j = 0; j < ViewUtilities.NUM_BLOCK_WIDTH; j++) {
                g.drawImage(imgBlocks[j][i], blocks[j][i].x, blocks[j][i].y, null);
            }
        }
    }
}
