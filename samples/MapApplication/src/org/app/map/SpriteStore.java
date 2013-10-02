package org.app.map;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpriteStore {

    private static SpriteStore single = new SpriteStore();
    private HashMap sprites = new HashMap();

    public static SpriteStore get() {
        return single;
    }

    public Sprite getSprite(String ref) {

        // if we've already got the sprite in the cache
        // then just return the existing version
        if (sprites.get(ref) != null) {
            return (Sprite) sprites.get(ref);
        }
        
        BufferedImage sourceImage = null;
        
        
        try {
            
            URL url = getClass().getClassLoader().getResource(ref);
            if(url == null)
                error("Cannot find image " + ref);
            
            sourceImage = ImageIO.read(url);
        } catch (IOException ex) {
            error("Cannot load image" + ref);
        }
        
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image acc_image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
        acc_image.getGraphics().drawImage(sourceImage, 0, 0, null);
        
        Sprite sprite = new Sprite(acc_image);
        sprites.put(ref, sprite);
        
        return sprite;
        
    }

    private void error(String string) {
        System.out.println(string);
        System.exit(0);
    }
}
