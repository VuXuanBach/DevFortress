package rmit.se2.pkg2012a.skoorti.view.util;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SpriteSheet implements Serializable{
    
    public BufferedImage spriteSheet;
    
    public SpriteSheet(BufferedImage ss){
        this.spriteSheet = ss;
    }
    
    public BufferedImage grabSprite(int x, int y, int width, int height){
        BufferedImage sprite = spriteSheet.getSubimage(x, y, width, height);
        return sprite;
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }
    
}