/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import sun.applet.Main;

/**
 *
 * @author Le Anh Duy
 */
public class SpriteSheetManager implements Serializable{

    private static Map<String, SpriteSheet> imageCache;
    private static SpriteSheetManager spriteSheetManager;
    private static BufferedImageLoader loader;

    public static SpriteSheetManager getInstance() {
        if (spriteSheetManager == null) {
            spriteSheetManager = new SpriteSheetManager();
            imageCache = new HashMap<String, SpriteSheet>();
            loader = new BufferedImageLoader();
        }
        return spriteSheetManager;
    }

    private SpriteSheet loadSpriteSheet(String fileName) {
        SpriteSheet currentSpriteSheet = null;
        try {
            currentSpriteSheet = imageCache.get(fileName);
            if (currentSpriteSheet == null) {
                currentSpriteSheet = new SpriteSheet(loader.loadImage("/images/" + fileName));
                imageCache.put(fileName, currentSpriteSheet);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentSpriteSheet;
    }

    public ArrayList<BufferedImage> getBuildingSprites(String type, String fileName) {
        SpriteSheet ss = loadSpriteSheet(fileName);
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        if (type.equals("cage")) {
            sprites.add(ss.grabSprite(32 * 4 * 4, 32 * 5, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 3, 32 * 5, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 2, 32 * 5, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 1, 32 * 5, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 0, 32 * 5, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 4, 0, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 3, 0, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 2, 0, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 1, 0, 32 * 4, 32 * 5));
            sprites.add(ss.grabSprite(32 * 4 * 0, 0, 32 * 4, 32 * 5));
        } else {
            sprites.add(ss.grabSprite(0, 0, ss.getSpriteSheet().getWidth(), ss.getSpriteSheet().getHeight()));
        }
        return sprites;
    }

    public ArrayList<BufferedImage> getBurningSprites(String fileName) {
        SpriteSheet ss = loadSpriteSheet(fileName);
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        sprites.add(ss.grabSprite(0, 0, 32, 32));
        sprites.add(ss.grabSprite(32, 0, 32, 32));
        return sprites;
    }

    public ArrayList<BufferedImage> getDownSprites(String type, String fileName) {
        SpriteSheet ss = loadSpriteSheet(fileName);
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        if (type.equals("person")) {
            sprites.add(ss.grabSprite(32 * 2, 32, 32, 32));
            sprites.add(ss.grabSprite(32 * 2, 32 * 2, 32, 32));
            sprites.add(ss.grabSprite(32 * 2, 32 * 3, 32, 32));
        }
        if (type.equals("animal")) {
            sprites.add(ss.grabSprite(64 * 0, 64 * 2, 64, 64));
            sprites.add(ss.grabSprite(64 * 0, 64 * 3, 64, 64));
        }
        if (type.equals("fire")) {
            sprites.add(ImageUtil.getRotateImage(ss.grabSprite(0, 0, 64, 32), 90));
            sprites.add(ImageUtil.getRotateImage(ss.grabSprite(64, 0, 64, 32), 90));
        }
        return sprites;
    }

    public ArrayList<BufferedImage> getUpSprites(String type, String fileName) {
        SpriteSheet ss = loadSpriteSheet(fileName);
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        if (type.equals("person")) {
            sprites.add(ss.grabSprite(0, 0, 32, 32));
            sprites.add(ss.grabSprite(32 * 2, 0, 32, 32));
            sprites.add(ss.grabSprite(32 * 1, 32 * 3, 32, 32));
        }
        if (type.equals("animal")) {
            sprites.add(ss.grabSprite(0, 0, 64, 64));
            sprites.add(ss.grabSprite(0, 64, 64, 64));
        }
        if (type.equals("fire")) {
            sprites.add(ImageUtil.getRotateImage(ss.grabSprite(0, 0, 64, 32), 270));
            sprites.add(ImageUtil.getRotateImage(ss.grabSprite(64, 0, 64, 32), 270));
        }
        return sprites;
    }

    public ArrayList<BufferedImage> getLeftSprites(String type, String fileName) {
        SpriteSheet ss = loadSpriteSheet(fileName);
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        if (type.equals("person")) {
            sprites.add(ss.grabSprite(0, 32 * 1, 32, 32));
            sprites.add(ss.grabSprite(0, 32 * 2, 32, 32));
            sprites.add(ss.grabSprite(0, 32 * 3, 32, 32));
        }
        if (type.equals("animal")) {
            sprites.add(ss.grabSprite(64 * 1, 64 * 0, 64, 64));
            sprites.add(ss.grabSprite(64 * 1, 64 * 1, 64, 64));
        }
        if (type.equals("fire")) {
            sprites.add(ImageUtil.getRotateImage(ss.grabSprite(0, 0, 64, 32), 180));
            sprites.add(ImageUtil.getRotateImage(ss.grabSprite(64, 0, 64, 32), 180));
        }
        return sprites;
    }

    public ArrayList<BufferedImage> getRightSprites(String type, String fileName) {
        SpriteSheet ss = loadSpriteSheet(fileName);
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        if (type.equals("person")) {
            sprites.add(ss.grabSprite(32 * 1, 32 * 0, 32, 32));
            sprites.add(ss.grabSprite(32 * 1, 32 * 1, 32, 32));
            sprites.add(ss.grabSprite(32 * 1, 32 * 2, 32, 32));
        }
        if (type.equals("animal")) {
            sprites.add(ss.grabSprite(64 * 1, 64 * 2, 64, 64));
            sprites.add(ss.grabSprite(64 * 1, 64 * 3, 64, 64));
        }
        if (type.equals("fire")) {
            sprites.add(ImageUtil.getRotateImage(ss.grabSprite(0, 0, 64, 32), 0));
            sprites.add(ImageUtil.getRotateImage(ss.grabSprite(64, 0, 64, 32), 0));
        }
        return sprites;
    }

    public BufferedImage getEmoticonImage(String fileName, int mood) {
        SpriteSheet ss = loadSpriteSheet(fileName);
        int size = GameConstants.MOOD_SIZE;
        BufferedImage emoticon = null;
        switch (mood) {
            case GameConstants.HAPPY:
                emoticon = ss.grabSprite(size * 0, size * 3, size, size);
                break;
            case GameConstants.N_HAPPY:
                emoticon = ss.grabSprite(size, size * 4, size, size);
                break;
            case GameConstants.HUNGRY:
                emoticon = ss.grabSprite(size, size * 2, size, size);
                break;
            case GameConstants.N_HUNGRY:
                emoticon = ss.grabSprite(size * 2, size, size, size);
                break;
            case GameConstants.BLADDER:
                emoticon = ss.grabSprite(size * 3, size, size, size);
                break;
            case GameConstants.N_BLADDER:
                emoticon = ss.grabSprite(size * 3, size * 0, size, size);
                break;
        }
        return emoticon;
    }
}
