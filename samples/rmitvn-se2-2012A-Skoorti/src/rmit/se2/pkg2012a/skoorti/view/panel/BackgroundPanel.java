/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import rmit.se2.pkg2012a.skoorti.view.util.ImageUtil;

/**
 *
 * @author duynguyen
 */
public class BackgroundPanel extends JPanel {

    private String fileName;
    int width = 737;
    int height = 180;

    public BackgroundPanel(LayoutManager layoutManager, String fileName) {
        this.fileName = fileName;
        setLayout(layoutManager);
    }

    public BackgroundPanel(LayoutManager layout, String fileName, int width, int height) {
        this(layout, fileName);
        this.width = width;
        this.height = height;
    }

//    @Override
//    public void paintComponents(Graphics g) {
//        super.paintComponents(g);
//        try {
//            g.drawImage(new BufferedImageLoader().loadImage("/images/logos/splashLogo.png"), 737, 200, null);
//        } catch (IOException ex) {
//            Logger.getLogger(BackgroundPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BufferedImage background = null;
        try {
            background = ImageUtil.getImageFromFile("logos/" + fileName, width, height);
        } catch (IOException ex) {
            Logger.getLogger(BackgroundPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.drawImage(background, 0, 0, this);
    }
}
