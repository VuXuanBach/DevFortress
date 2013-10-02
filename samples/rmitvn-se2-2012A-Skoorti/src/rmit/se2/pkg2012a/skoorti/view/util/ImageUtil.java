/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author zozo
 */
public class ImageUtil {

    public static BufferedImage getImageFromFile(String fileName, int width, int height) throws IOException {
        fileName = "/images/" + fileName;
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage image = loader.loadImage(fileName);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        if (width > 0 && height > 0) {
            img.createGraphics().drawImage(image, 0, 0, width, height, null);
        }
        return img;
    }

    public static BufferedImage getRotateImage(BufferedImage bufferedImage, double degrees) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(degrees * Math.PI / 180, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        bufferedImage = op.filter(bufferedImage, null);
        return bufferedImage;
    }
}
