package rmit.se2.pkg2012a.skoorti.view.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import javax.imageio.ImageIO;

public class BufferedImageLoader implements Serializable{
    
    public BufferedImage loadImage(String pathRelativeToThis) throws IOException {
        URL url = this.getClass().getResource(pathRelativeToThis);
        BufferedImage img = ImageIO.read(url);
        return img;
    }
    
}