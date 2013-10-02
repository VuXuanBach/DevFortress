/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.game.cache;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author DELL XPS
 */
public class SpriteCache {

    private static HashMap<String, BufferedImage> map;

    public SpriteCache() {
        map = new HashMap<>();
    }
    
    public static BufferedImage loadImage(String res) {
        try {
            return ImageIO.read(SpriteCache.class.getResource(res));
        } catch (IOException ex) {
            return null;
        }
    }
    
    public static BufferedImage add(String res) {
        BufferedImage image = map.get(res);
        map.put(res, image);
        return image;
    }
    
    public static BufferedImage get(String res) {
        BufferedImage image = map.get(res);
        
        if(image == null) {
            add(res);
            image = map.get(res);
        }
        
        return image;
    }
}
